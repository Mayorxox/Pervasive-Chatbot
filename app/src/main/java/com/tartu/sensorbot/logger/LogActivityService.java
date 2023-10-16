package com.tartu.sensorbot.logger;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.AccessibilityEvent;

public class LogActivityService extends AccessibilityService {

  @Override
  public void onAccessibilityEvent(AccessibilityEvent event) {
    Logger.log(this, event);
  }

  @Override
  public void onInterrupt() {
    Logger.log(this, "Accessibility service interrupted.");
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Logger.log(this, "Accessibility service created.");

    // Register for system broadcasts (e.g., connectivity changes)
    IntentFilter filter = new IntentFilter();
    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    registerReceiver(broadcastReceiver, filter);
    Logger.log(this, "Registered for system broadcasts.");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Logger.log(this, "Accessibility service destroyed.");
    unregisterReceiver(broadcastReceiver);
    Logger.log(this, "Unregistered from system broadcasts.");
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
}
