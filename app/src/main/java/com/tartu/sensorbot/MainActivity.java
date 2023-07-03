package com.tartu.sensorbot;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tartu.sensorbot.bot.ChatBot;
import com.tartu.sensorbot.decoration.VerticalSpaceItemDecoration;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.message.MessageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final List<Message> messages = new ArrayList<>();
    private final Random random = new Random();
    private final Handler handler = new Handler();
    private MessageAdapter messageAdapter;
    private ImageView infoButton;
    private RecyclerView recyclerView;
    private ChatBot chatBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputField = findViewById(R.id.input_field);
        ImageButton sendButton = findViewById(R.id.send_button);
        infoButton = findViewById(R.id.info_icon);
        ImageView historyButton = findViewById(R.id.history_icon);
        recyclerView = findViewById(R.id.chat_recycler_view);
        chatBot = new ChatBot();
        messageAdapter = new MessageAdapter(messages, chatBot, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(messageAdapter);
        int verticalSpaceHeight = getResources().getDimensionPixelSize(R.dimen.vertical_space_height);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(verticalSpaceHeight));

        updateUI();

        sendButton.setOnClickListener(v -> {
            String userMessage = inputField.getText().toString();
            if (!userMessage.isEmpty()) {
                messages.add(0, new Message(userMessage, true, false));
                inputField.setText("");
                sendBotMessage(userMessage);
                updateUI();
            }
        });

        infoButton.setOnClickListener(v -> {
            if (!messages.isEmpty()) {
                showClearConversationModal();
            } else {
                showInfoModal();
            }
        });
        historyButton.setOnClickListener(v -> openHistoryPage());
    }

    private void updateUI() {
        TextView placeholderText = findViewById(R.id.placeholder_text);

        if (messages.isEmpty()) {
            placeholderText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            infoButton.setImageResource(R.drawable.ic_info);
        } else {
            placeholderText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            infoButton.setImageResource(R.drawable.ic_bin);
        }
    }

    private void showInfoModal() {
        new AlertDialog.Builder(this)
                .setTitle("Information")
                .setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void showClearConversationModal() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Clear chat")
                .setMessage("Are you sure you want to clear the chat?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    messages.clear();
                    messageAdapter.notifyDataSetChanged();
                    updateUI();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void sendBotMessage(String userMessage) {
        String botMessageText = chatBot.getResponse(userMessage);
        final StringBuilder typedMessage = new StringBuilder();
        final Message botMessage = new Message("", false, false);

        messages.add(0, botMessage);

//  if (botMessageText.contains("suggestions")) {
//      for (String suggestion : chatBot.getSuggestions()) {
//          messages.add(0, new Message(suggestion, false, true));
//      }
//  }

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (typedMessage.length() < botMessageText.length()) {
                    typedMessage.append(botMessageText.charAt(typedMessage.length()));
                    botMessage.setText(typedMessage.toString());
                    messageAdapter.notifyDataSetChanged();
                    int delay = 30 + random.nextInt(150); // random delay between 50 and 250 milliseconds
                    handler.postDelayed(this, delay);
                }
            }
        });
    }

    private void openHistoryPage() {
//        Intent intent = new Intent(this, HistoryActivity.class);
//        startActivity(intent);
    }
}
