package com.tartu.sensorbot;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tartu.sensorbot.bot.BotMessageTemplates;
import com.tartu.sensorbot.bot.BotResponseGenerator;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.message.MessageAdapter;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText inputEditText;
    private MessageAdapter messageAdapter;
    private BotResponseGenerator responseGenerator;
    private String condition;
    private static final String CONDITION_INDENT_KEY = "condition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputEditText = findViewById(R.id.inputEditText);
        this.condition = getIntent().getStringExtra(CONDITION_INDENT_KEY);
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
        messageAdapter.addMessage(BotMessageTemplates.INITIAL_BOT_MESSAGE, true);
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
