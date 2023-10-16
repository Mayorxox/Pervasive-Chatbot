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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Optional;

public class LogActivity extends AppCompatActivity {

  private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      result -> {
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
          Uri uri = result.getData().getData();
          copyLogFileTo(uri);
        }
      }
  );
  private TextView logTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log);

    logTextView = findViewById(R.id.logTextView);
    ScrollView scrollView = findViewById(R.id.scrollView);

    String logs = Logger.readLogs(this);
    Optional.ofNullable(logTextView).ifPresent(a -> a.setText(logs));

    // Scroll to the bottom
    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

    // Export logs to a file when the button is clicked
    Button exportButton = findViewById(R.id.exportButton);
    exportButton.setOnClickListener(v -> exportLogs());

    // Clear logs when clicked
    Button clearLogsButton = findViewById(R.id.clearLogsButton);
    clearLogsButton.setOnClickListener(v -> showClearLogsConfirmationDialog());
  }

  private void exportLogs() {
    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("text/csv");
    intent.putExtra(Intent.EXTRA_TITLE, String.format("logs_%s.csv", LocalDateTime.now().toString()));

    resultLauncher.launch(intent);
  }

  private void showClearLogsConfirmationDialog() {
    new AlertDialog.Builder(this)
        .setTitle("Clear Logs")
        .setMessage("Are you sure you want to clear logs?")
        .setPositiveButton("Yes", (dialog, which) -> {
          Logger.clearLogs(this);
          String logs = Logger.readLogs(this);
          Optional.ofNullable(logTextView).ifPresent(a -> a.setText(logs));
          showDialog("Success", "Logs cleared successfully!");
        })
        .setNegativeButton("No", null)
        .show();
  }

  private void copyLogFileTo(Uri targetUri) {
    File logFile = new File(getFilesDir(), Logger.LOG_FILE_NAME);
    try (InputStream inputStream = new FileInputStream(logFile);
        OutputStream outputStream = getContentResolver().openOutputStream(targetUri)) {

      byte[] buffer = new byte[1024];
      int length;
      while ((length = inputStream.read(buffer)) > 0) {
        outputStream.write(buffer, 0, length);
      }
      showDialog("Success", "Logs exported successfully!");
    } catch (IOException e) {
      e.printStackTrace();
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
