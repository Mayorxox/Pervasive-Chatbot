package com.tartu.sensorbot.logger;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Logger {
  private static final String LOG_FILE_NAME = "user_logs.txt";

  public static void log(Context context, String message) {
    try {
      FileOutputStream fos = context.openFileOutput(LOG_FILE_NAME, Context.MODE_APPEND);
      OutputStreamWriter osw = new OutputStreamWriter(fos);
      osw.write(message + "\n");
      osw.flush();
      osw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String readLogs(Context context) {
    try {
      FileInputStream fis = context.openFileInput(LOG_FILE_NAME);
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line).append("\n");
      }
      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
