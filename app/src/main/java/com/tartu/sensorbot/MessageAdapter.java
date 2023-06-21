package com.tartu.sensorbot;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tartu.sensorbot.message.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private final List<Message> messages;
    private final ChatBot chatBot;
    private static int screenWidth;

    public MessageAdapter(List<Message> messages, ChatBot chatBot, Context context) {
        this.messages = messages;
        this.chatBot = chatBot;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_user, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_bot, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        return message.isUser() ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageTextView.setText(message.getText());

        if (message.isSuggestion()) {
            holder.messageTextView.setOnClickListener(v -> {
                // Send the suggestion as a user message
                String userMessage = message.getText();
                messages.add(0, new Message(userMessage, true, false));
                notifyDataSetChanged();

                // Get a response from the bot
                String botMessageText = chatBot.getResponse(userMessage);
                messages.add(0, new Message(botMessageText, false, false));
                notifyDataSetChanged();
            });
        } else {
            holder.messageTextView.setOnClickListener(null);
        }
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text);
            messageTextView.setMaxWidth((int) (screenWidth * 0.75));
        }
    }
}
