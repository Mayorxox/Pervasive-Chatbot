package com.tartu.sensorbot.chat;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tartu.sensorbot.R;

import java.util.List;
import java.util.Objects;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    private final TextView messageTextView;
    private final CardView cardView;
    private final LinearLayout messageContainer;
    private final String condition;

    public ChatViewHolder(@NonNull View itemView, int viewType, String condition) {
        super(itemView);
        this.condition = condition;
        this.messageTextView = itemView.findViewById(R.id.messageTextView);
        this.cardView = itemView.findViewById(R.id.cardView);

        if (viewType == Message.VIEW_TYPE_COMPLEX_BOT) {
            this.messageContainer = itemView.findViewById(R.id.stepsContainer);
        } else {
            this.messageContainer = itemView.findViewById(R.id.messageContainer);
        }
    }

    public void bindUserMessage(Message message) {
        this.setTextViewWidth();
        messageTextView.setBackgroundColor(Color.WHITE);
        cardView.setCardBackgroundColor(Color.WHITE);
        messageContainer.setGravity(Gravity.END);
        messageTextView.setText(message.text);
    }

    public void bindComplexBotMessage(Message message) {
        if (messageContainer == null) {
            return;
        }
        if (Objects.equals(condition, ChatbotCondition.reference)) {
            messageContainer.removeAllViews();
        }

        List<String> steps = message.getSteps();
        List<String> times = message.getTimes();
        int insertionPosition = Math.max(messageContainer.getChildCount() - 2, 0);

        for (int i = 0; i < steps.size(); i++) {
            View stepView = LayoutInflater.from(messageContainer.getContext())
                    .inflate(R.layout.step_item, messageContainer, false);

            ((TextView) stepView.findViewById(R.id.stepText)).setText(steps.get(i));
            ((TextView) stepView.findViewById(R.id.time)).setText(times.get(i));

            messageContainer.addView(stepView, insertionPosition);
            insertionPosition++;
        }
    }

    public void bindBotMessage(Message message) {
        this.setTextViewWidth();
        messageTextView.setBackgroundColor(Color.parseColor("#ADD8E6"));
        cardView.setCardBackgroundColor(Color.parseColor("#ADD8E6"));
        messageContainer.setGravity(Gravity.START);
        messageTextView.setText(message.text);
    }

    private void setTextViewWidth() {
        int maxWidth = (int) (0.8 * itemView.getContext().getResources().getDisplayMetrics().widthPixels);
        messageTextView.setMaxWidth(maxWidth);
    }
}