package com.tartu.sensorbot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.tartu.sensorbot.logger.Logger;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class LogActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log);

    TextView logTextView = findViewById(R.id.logTextView);
    ScrollView scrollView = findViewById(
        R.id.scrollView); // Ensure you give an ID to your ScrollView in XML
    Button exportButton = findViewById(R.id.exportButton);

    String logs = Logger.readLogs(this);
    logTextView.setText(logs);

    // Scroll to the bottom
    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

    // Export logs to a file when the button is clicked
    exportButton.setOnClickListener(v -> exportLogs());
  }

  private void exportLogs() {
    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TITLE, "logs.txt");

    // Use the new ActivityResult API to handle the result
    resultLauncher.launch(intent);
  }

  // Define an ActivityResultLauncher to handle the result of the document creation
  private final ActivityResultLauncher<Intent> resultLauncher =
      registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
          Uri uri = result.getData().getData();
          if (uri != null) {
            writeLogsToFile(uri, Logger.readLogs(this));
          }
        }
      });

  private void writeLogsToFile(Uri uri, String logs) {
    try (OutputStream outputStream = getContentResolver().openOutputStream(uri);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
      writer.write(logs);
      showDialog("Success", "Logs exported successfully!");
    } catch (IOException e) {
      showDialog("Error", "Failed to export logs!");
    }
  }

  private void showDialog(String title, String message) {
    new AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, null)
        .show();
  }
}

