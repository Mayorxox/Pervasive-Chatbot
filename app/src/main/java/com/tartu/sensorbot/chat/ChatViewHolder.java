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
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.message.MessageStep;
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
    messageTextView.setText(message.getText());
  }

  public void bindComplexBotMessage(Message message) {
    if (messageContainer == null) {
      return;
    }
    messageContainer.removeAllViews();

    List<MessageStep> steps = message.getSteps();

    LayoutInflater layoutInflater = LayoutInflater.from(messageContainer.getContext());
    steps.forEach(step -> {
      View stepView = layoutInflater.inflate(R.layout.step_item, messageContainer, false);

      ((TextView) stepView.findViewById(R.id.stepText)).setText(step.getInstruction());
      ((TextView) stepView.findViewById(R.id.time)).setText(step.getTime());

      messageContainer.addView(stepView);
    });

    if (Objects.nonNull(message.getText()) && Objects.equals(condition,
        ChatbotCondition.reference)) {
      TextView textView = new TextView(messageContainer.getContext());
      textView.setText(message.getText());
      textView.setTextSize(14);
      textView.setTextColor(Color.BLACK);
      messageContainer.addView(textView);
    }
    if (message.getSteps().size() > 0 && Objects.equals(condition, ChatbotCondition.pervasive)) {
      View complexBotButtonView = layoutInflater.inflate(R.layout.complex_bot_pc_button,
          messageContainer, false);
      messageContainer.addView(complexBotButtonView);
    }
  }

  public void bindBotMessage(Message message) {
    this.setTextViewWidth();
    int backgroundColor = messageContainer.getContext().getResources()
        .getColor(R.color.bot_message_background);
    messageTextView.setBackgroundColor(backgroundColor);
    cardView.setCardBackgroundColor(backgroundColor);
    messageContainer.setGravity(Gravity.START);
    messageTextView.setText(message.getText());
  }

  private void setTextViewWidth() {
    int maxWidth = (int) (0.8 * itemView.getContext().getResources()
        .getDisplayMetrics().widthPixels);
    messageTextView.setMaxWidth(maxWidth);
  }
}