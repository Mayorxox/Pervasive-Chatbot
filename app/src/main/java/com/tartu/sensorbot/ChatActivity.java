package com.tartu.sensorbot;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tartu.sensorbot.chat.BotResponseGenerator;
import com.tartu.sensorbot.chat.Message;
import com.tartu.sensorbot.chat.MessageAdapter;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText inputEditText;
    private MessageAdapter messageAdapter;
    private BotResponseGenerator responseGenerator;
    private String condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputEditText = findViewById(R.id.inputEditText);
        this.condition = getIntent().getStringExtra("condition");
        responseGenerator = new BotResponseGenerator(condition);

        setUpChatRecyclerView();
        handleSendButtonClickListener();
    }

    private void setUpChatRecyclerView() {
        RecyclerView chatRecyclerView = findViewById(R.id.chatRecyclerView);

        messageAdapter = new MessageAdapter(condition);
        messageAdapter.setScrollToBottomCallback(() -> chatRecyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1));
        chatRecyclerView.setAdapter(messageAdapter);
        chatRecyclerView.setLayoutManager(getLinearLayoutManager());

        // send initial bot message
        messageAdapter.addMessage("Hello, how can I help you today?", true);
    }

    private void handleSendButtonClickListener() {
        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> onSendButtonClick());
    }

    private LinearLayoutManager getLinearLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        return layoutManager;
    }

    private void onSendButtonClick() {
        String userMessage = inputEditText.getText().toString().trim();
        if (!userMessage.isEmpty()) {
            // Add user's message
            messageAdapter.addMessage(userMessage, false);
            inputEditText.setText("");
            simulateBotResponse(userMessage);
        }
    }

    private void simulateBotResponse(String userQuery) {
        new Handler().postDelayed(() -> {
            List<Message> responses = responseGenerator.generateResponse(userQuery);
            messageAdapter.addBotMessages(responses);
        }, 2000);
    }
}
