package com.tartu.sensorbot.logger;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;
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

    boolean isAccessibilityServiceEnabled = getEnabledServices().stream().anyMatch(
            service -> isAccessibilityServiceEnabled(service, LogActivityService.class.getName()));
    if (!isAccessibilityServiceEnabled) {
      showEnableAccessibilityServiceAlert();
    }
  }

  private void startActivity(String actionAccessibilitySettings) {
    context.startActivity(new Intent(actionAccessibilitySettings));
  }

  private boolean hasUsageStatsPermission() {
    AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
    int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
    return mode == AppOpsManager.MODE_ALLOWED;
  }

  private List<AccessibilityServiceInfo> getEnabledServices() {
    AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    return am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
  }

  private boolean isAccessibilityServiceEnabled(AccessibilityServiceInfo enabledService, String serviceName) {
    ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
    return enabledServiceInfo.packageName.equals(context.getPackageName())
        && enabledServiceInfo.name.equals(serviceName);
  }

  private void showEnableAccessibilityServiceAlert() {
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
