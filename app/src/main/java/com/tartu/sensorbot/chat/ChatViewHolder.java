package com.tartu.sensorbot.chat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.tartu.sensorbot.R;
import com.tartu.sensorbot.logger.Logger;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.message.MessageStep;
import com.tartu.sensorbot.util.DialogUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ChatViewHolder extends RecyclerView.ViewHolder {

  private final static String INFO_TEXT =
      "Computational offloading is a process where certain tasks "
          + "or computations are performed on more powerful remote servers instead of on your device. "
          + "This helps your device save battery life and perform tasks more efficiently. It's like "
          + "asking someone else to do a heavy task for you!";

  private final TextView messageTextView;
  private final CardView cardView;
  private final LinearLayout messageContainer;
  private final String condition;
  private final Context context;

  public ChatViewHolder(@NonNull View itemView, int viewType, String condition) {
    super(itemView);
    this.context = itemView.getContext();
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
      ChatStepView stepView = new ChatStepView(step, condition, context);
      messageContainer.addView(stepView.create(false));

      if (!step.getAdditionalSteps().isEmpty() && isReferenceCondition()) {
        step.getAdditionalSteps().forEach(additionalStep -> {
          ChatStepView additionalStepView = new ChatStepView(additionalStep, condition, context);
          messageContainer.addView(additionalStepView.create(true));
        });

        View doneButtonLayout = layoutInflater.inflate(R.layout.done_button, messageContainer,
            false);
        Button doneButton = doneButtonLayout.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(v -> DialogUtil.showLoadingDialog(context, 500, () ->
            Logger.log(context,
                String.format("%s: User clicked confirm button", LocalDateTime.now().toString()))));

        messageContainer.addView(doneButtonLayout);
      }
    });

    if (Objects.nonNull(message.getText()) && isReferenceCondition()) {
      TextView textView = getReferenceContextText(message);
      messageContainer.addView(textView);
    }
    View complexBotButtonView = getComplexBotButtonView(layoutInflater);
    messageContainer.addView(complexBotButtonView);
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

  @NonNull
  private TextView getReferenceContextText(Message message) {
    TextView textView = new TextView(messageContainer.getContext());
    textView.setText(message.getText());
    textView.setTextSize(14);
    textView.setTextColor(Color.BLACK);
    return textView;
  }

  private boolean isReferenceCondition() {
    return Objects.equals(condition,
        ChatbotCondition.reference);
  }

  @NonNull
  private View getComplexBotButtonView(LayoutInflater layoutInflater) {
    View complexBotButtonView = layoutInflater.inflate(R.layout.confirm_offloading_info,
        messageContainer, false);

    View container = complexBotButtonView.findViewById(R.id.computational_offloading_container);
    container.setOnClickListener(v -> DialogUtil.display(context, INFO_TEXT));
    return complexBotButtonView;
  }

  private void setTextViewWidth() {
    int maxWidth = (int) (0.8 * itemView.getContext().getResources()
        .getDisplayMetrics().widthPixels);
    messageTextView.setMaxWidth(maxWidth);
  }
}