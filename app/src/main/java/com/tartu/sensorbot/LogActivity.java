package com.tartu.sensorbot;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.tartu.sensorbot.logger.Logger;

public class LogActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log);

    TextView logTextView = findViewById(R.id.logTextView);
    String logs = Logger.readLogs(this);
    logTextView.setText(logs);
  }

}
