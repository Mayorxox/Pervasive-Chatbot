package com.tartu.sensorbot.logger;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.AccessibilityEvent;
import java.time.LocalDateTime;

public class LogActivityService extends AccessibilityService {

  @Override
  public void onAccessibilityEvent(AccessibilityEvent event) {
    String logMessage = "Accessibility Event: " +
        "\n\tTimestamp: " + LocalDateTime.now().toString() +
        "\n\tPackage: " + event.getPackageName() +
        "\n\tType: " + AccessibilityEvent.eventTypeToString(event.getEventType()) +
        "\n\tContent Description: " + event.getContentDescription() +
        "\n\tSource Class: " + (event.getClassName() != null ? event.getClassName() : "N/A") +
        "\n\tTime: " + event.getEventTime() +
        "\n\tRecord Count: " + event.getRecordCount();

    // If you want to log text data from the event
    StringBuilder eventText = new StringBuilder("\n\tText: ");
    for (CharSequence s : event.getText()) {
      eventText.append(s).append(" ");
    }
    logMessage += eventText.toString();

    // If you want to log additional info for certain event types
    if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED && event.getSource() != null) {
      logMessage += "\n\tView Resource ID: " + event.getSource().getViewIdResourceName();
    }

    Logger.log(this, logMessage);
  }

  @Override
  public void onInterrupt() {
    // Handle interrupts if needed
  }

  @Override
  public void onCreate() {
    super.onCreate();

    // Register for system broadcasts (e.g., connectivity changes)
    IntentFilter filter = new IntentFilter();
    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    registerReceiver(broadcastReceiver, filter);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
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
}
