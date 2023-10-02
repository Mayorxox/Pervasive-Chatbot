package com.tartu.sensorbot;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.tartu.sensorbot.activityHandlers.NextButtonHandler;
import com.tartu.sensorbot.activityHandlers.TermsAndConditionsHandler;
import com.tartu.sensorbot.activityHandlers.UserManualHandler;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeActivityHandlers();
  }

  private void initializeActivityHandlers() {
    View rootView = findViewById(android.R.id.content);

    new TermsAndConditionsHandler(rootView);
    new UserManualHandler(rootView, this);
    new NextButtonHandler(rootView, this);
  }
}
