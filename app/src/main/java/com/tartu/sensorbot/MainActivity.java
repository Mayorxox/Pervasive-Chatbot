package com.tartu.sensorbot;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tartu.sensorbot.message.Message;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText inputField;
    private Button sendButton;
    private RecyclerView chatRecyclerView;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.input_field);
        sendButton = findViewById(R.id.send_button);
        chatRecyclerView = findViewById(R.id.chat_recycler_view);

        messageAdapter = new MessageAdapter(new ArrayList<>());
        chatRecyclerView.setAdapter(messageAdapter);

        inputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendButton.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        sendButton.setOnClickListener(v -> {
            String message = inputField.getText().toString();
            sendMessage(message);
            inputField.setText("");
        });

        ImageView infoIcon = findViewById(R.id.info_icon);
        infoIcon.setOnClickListener(v -> showInfoModal());

        ImageView historyIcon = findViewById(R.id.history_icon);
        historyIcon.setOnClickListener(v -> openHistoryPage());
    }

    private void sendMessage(String message) {
        // Add the message to the adapter and notify it of the change
        messageAdapter.addMessage(new Message(message, true));
        messageAdapter.notifyDataSetChanged();
    }

    private void showInfoModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information")
                .setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed accumsan congue enim et blandit.")
                .setPositiveButton("Close", (dialog, id) -> {
                    // User clicked the Close button
                    dialog.dismiss();
                });
        builder.create().show();
    }

    private void openHistoryPage() {
        // Open the history page
    }
}

