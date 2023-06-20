package com.tartu.sensorbot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tartu.sensorbot.message.Message;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Message> messages = new ArrayList<>();
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputField = findViewById(R.id.input_field);
        ImageButton sendButton = findViewById(R.id.send_button);
        ImageView infoButton = findViewById(R.id.info_icon);
        ImageView historyButton = findViewById(R.id.history_icon);
        RecyclerView recyclerView = findViewById(R.id.chat_recycler_view);

        messageAdapter = new MessageAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        updateUI();

        sendButton.setOnClickListener(v -> {
            String userMessage = inputField.getText().toString();
            if (!userMessage.isEmpty()) {
                messages.add(new Message(userMessage, false));
                messageAdapter.notifyDataSetChanged();
                inputField.setText("");
                updateUI();
            }
        });

        infoButton.setOnClickListener(v -> showInfoModal());
        historyButton.setOnClickListener(v -> openHistoryPage());
    }

    private void updateUI() {
        TextView placeholderText = findViewById(R.id.placeholder_text);
        RecyclerView recyclerView = findViewById(R.id.chat_recycler_view);

        if (messages.isEmpty()) {
            placeholderText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            placeholderText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showInfoModal() {
        new AlertDialog.Builder(this)
                .setTitle("Information")
                .setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void openHistoryPage() {
//        Intent intent = new Intent(this, HistoryActivity.class);
//        startActivity(intent);
    }
}
