package com.tartu.sensorbot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.tartu.sensorbot.activityHandlers.NextButtonHandler;
import com.tartu.sensorbot.activityHandlers.TermsAndConditionsHandler;
import com.tartu.sensorbot.activityHandlers.UserManualHandler;
import com.tartu.sensorbot.logger.LoggerPermissionUtil;

public class MainActivity extends AppCompatActivity {

  private LoggerPermissionUtil loggerPermissionUtil;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeActivityHandlers();
    loggerPermissionUtil = new LoggerPermissionUtil(this);
    loggerPermissionUtil.checkPermissionsAndSettings();
  }

  @Override
  protected void onResume() {
    super.onResume();
    loggerPermissionUtil.checkPermissionsAndSettings();
  }

  private void initializeActivityHandlers() {
    View rootView = findViewById(android.R.id.content);

    new TermsAndConditionsHandler(rootView);
    new UserManualHandler(rootView);
    new NextButtonHandler(rootView);

    logViewHandler();
  }

  private void logViewHandler() {
    TextView logText = findViewById(R.id.appLogsText);
    logText.setOnClickListener(v -> {
      Intent intent = new Intent(this, LogActivity.class);
      startActivity(intent);
    });
  }
}
