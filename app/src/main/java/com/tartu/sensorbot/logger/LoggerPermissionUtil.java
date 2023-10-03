package com.tartu.sensorbot.logger;

import android.app.AlertDialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import java.util.List;

public class LoggerPermissionUtil {

  private final Context context;

  public LoggerPermissionUtil(Context context) {
    this.context = context;
  }

  public void checkPermissionsAndSettings() {
    // Check for PACKAGE_USAGE_STATS permission
    if (!hasUsageStatsPermission()) {
      startActivity(Settings.ACTION_USAGE_ACCESS_SETTINGS);
    }

    // Check if the AccessibilityService is enabled and if not, guide the user to enable it
    if (!isAccessibilityServiceEnabled()) {
      new AlertDialog.Builder(context)
          .setTitle("Enable Accessibility Service")
          .setMessage("For the user study, please enable the UserActivityService in the accessibility settings.")
          .setPositiveButton("Go to Settings", (dialog, which) -> {
            // Direct the user to the accessibility settings
            startActivity(Settings.ACTION_ACCESSIBILITY_SETTINGS);
          })
          .setNegativeButton("Cancel", null)
          .show();
    }
  }

  private void startActivity(String actionAccessibilitySettings) {
    context.startActivity(new Intent(actionAccessibilitySettings));
  }

  private boolean isAccessibilityServiceEnabled() {
    String serviceName = context.getPackageName() + "/" + LogActivityService.class.getName();
    String enabledServices = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
    return enabledServices != null && enabledServices.contains(serviceName);
  }

  private boolean hasUsageStatsPermission() {
    final UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
    if (usageStatsManager == null) {
      return false;
    }
    final long currentTime = System.currentTimeMillis();
    final List<UsageStats> stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 1000, currentTime);
    return stats != null && !stats.isEmpty();
  }

}
