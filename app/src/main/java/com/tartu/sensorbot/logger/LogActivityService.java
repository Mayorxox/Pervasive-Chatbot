package com.tartu.sensorbot.logger;

import android.accessibilityservice.AccessibilityService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class LogActivityService extends AccessibilityService {

  private final Handler handler = new Handler(Looper.getMainLooper());
  private final Runnable logUsageStatsRunnable = new Runnable() {
    @Override
    public void run() {
      logAppUsageStats();
      handler.postDelayed(this, 120000);  // Log every 2 minutes
    }
  };

  @Override
  public void onAccessibilityEvent(AccessibilityEvent event) {
    String logMessage = "Accessibility Event: " +
        "\n\tPackage: " + event.getPackageName() +
        "\n\tType: " + AccessibilityEvent.eventTypeToString(event.getEventType()) +
        "\n\tContent: " + event.getContentDescription();
    Logger.log(this, logMessage);
  }

  @Override
  public void onInterrupt() {
    // Handle interrupts if needed
  }

  @Override
  public void onCreate() {
    super.onCreate();

    handler.post(logUsageStatsRunnable);
    // Register for system broadcasts (e.g., connectivity changes)
    IntentFilter filter = new IntentFilter();
    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    registerReceiver(broadcastReceiver, filter);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    handler.removeCallbacks(logUsageStatsRunnable);
    unregisterReceiver(broadcastReceiver);
  }

  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      // Handle system broadcasts
      if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
        Logger.log(getContext(), "Connectivity changed");
      }
    }
  };

  private Context getContext() {
    return this;
  }

  private void logAppUsageStats() {
    // Log app usage stats
    UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
    if (usageStatsManager != null) {
      long currentTime = System.currentTimeMillis();
      List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 1000 * 60 * 60 * 24, currentTime);
      for (UsageStats usageStats : usageStatsList) {
        String logMessage = "App Usage: " +
            "\n\tApp: " + usageStats.getPackageName() +
            "\n\tLast Time Used: " + usageStats.getLastTimeUsed();
        Logger.log(this, logMessage);
      }
    }
  }
}
