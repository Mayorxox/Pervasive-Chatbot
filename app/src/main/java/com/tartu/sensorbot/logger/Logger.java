package com.tartu.sensorbot.logger;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import com.tartu.sensorbot.util.StringUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.Locale;

public class Logger {

  public static final String LOG_FILE_NAME = "user_logs.csv";
  private static final String CSV_HEADER = "Timestamp,Message,Package,Type,Source Class,View Resource ID,Content Description,Time,Record Count\n";

  public static void log(Context context, String message) {
    String logEntry = String.format("%s,%s\n", LocalDateTime.now().toString(), StringUtil.escapeForCsv(message));

    writeLogEntry(context, logEntry);
  }

  public static void log(Context context, AccessibilityEvent event) {
    String logEntry = String.format(Locale.US,
        "%s,%s,%s,%s,%s,%s,%s,%s,%d\n",
        LocalDateTime.now().toString(),
        StringUtil.escapeForCsv(event.getText()),
        StringUtil.escapeForCsv(event.getPackageName()),
        StringUtil.escapeForCsv(AccessibilityEvent.eventTypeToString(event.getEventType())),
        StringUtil.escapeForCsv(event.getClassName() != null ? event.getClassName().toString() : "N/A"),
        StringUtil.escapeForCsv(getViewIdResourceId(event)),
        StringUtil.escapeForCsv(event.getContentDescription()),
        StringUtil.escapeForCsv(Long.toString(event.getEventTime())),
        event.getRecordCount()
    );

    writeLogEntry(context, logEntry);
  }

  private static String getViewIdResourceId(AccessibilityEvent event) {
    if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED && event.getSource() != null) {
      return event.getSource().getViewIdResourceName();
    }
    return "";
  }

  private static void writeLogEntry(Context context, String logEntry) {
    try (FileOutputStream fos = context.openFileOutput(LOG_FILE_NAME, Context.MODE_APPEND);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw)) {

      // Check if file is empty and write header if it is
      File logFile = new File(context.getFilesDir(), LOG_FILE_NAME);
      if (logFile.length() == 0) {
        bw.write(CSV_HEADER);
      }

      bw.write(logEntry);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String readLogs(Context context) {
    StringBuilder sb = new StringBuilder();
    try (FileInputStream fis = context.openFileInput(LOG_FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr)) {

      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // This regex splits by commas outside of quotes
        sb.append(getLogLine(values));
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  private static String getLogLine(String[] values) {
    if (values.length < 2) {
      return "";
    }

    String timestamp = values[0];
    String message = values[1];
    if (message != null) {
      return String.format("%s: %s\n", timestamp, message);
    }

    if (values.length >= 7 && values[6] != null) {
      String contentDescription = values[6];
      return String.format("%s: %s\n", timestamp, contentDescription);
    }
    return "";
  }

  public static void clearLogs(Context context) {
    File logFile = new File(context.getFilesDir(), LOG_FILE_NAME);
    if (logFile.exists()) {
      logFile.delete();
    }
  }
}
