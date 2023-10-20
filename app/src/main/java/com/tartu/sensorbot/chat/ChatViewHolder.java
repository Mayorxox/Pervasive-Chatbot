package com.tartu.sensorbot.chat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.tartu.sensorbot.R;
import com.tartu.sensorbot.logger.Logger;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.message.MessageStep;
import com.tartu.sensorbot.util.BatteryDrainerUtil;
import com.tartu.sensorbot.util.DialogDisplayUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ChatViewHolder extends RecyclerView.ViewHolder {

  private final static String INFO_TEXT = "Computational offloading is a process where certain tasks "
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
      View stepView = createStepView(step, condition);
      messageContainer.addView(stepView);
    });

    if (Objects.nonNull(message.getText()) && isReferenceCondition()) {
      TextView textView = getReferenceContextText(message);
      View infoButton = layoutInflater.inflate(R.layout.info_image_button, messageContainer, false);
      infoButton.setOnClickListener(v -> DialogDisplayUtil.display(context, INFO_TEXT));

      View doneButtonLayout = layoutInflater.inflate(R.layout.done_button, messageContainer, false);
      Button doneButton = doneButtonLayout.findViewById(R.id.doneButton);
      doneButton.setOnClickListener(v -> {
        Logger.log(context, String.format("%s: User clicked confirm button", LocalDateTime.now().toString()));
        doneButton.setEnabled(false);
        DialogDisplayUtil.showLoadingDialog(context, 500);
      });

      messageContainer.addView(infoButton);
      messageContainer.addView(textView);
      messageContainer.addView(doneButtonLayout);
    }
    if (message.getSteps().size() > 0 && !isReferenceCondition()) {
      View complexBotButtonView = getComplexBotButtonView(layoutInflater);
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
    View complexBotButtonView = layoutInflater.inflate(R.layout.complex_bot_pc_button,
        messageContainer, false);

    Button confirmButton = complexBotButtonView.findViewById(R.id.confirmButton);
    confirmButton.setOnClickListener(v -> {
      Logger.log(context, String.format("%s: User clicked confirm button", LocalDateTime.now().toString()));
      confirmButton.setEnabled(false);
      DialogDisplayUtil.showLoadingDialog(context, 3000);
    });

    ImageView infoButton = complexBotButtonView.findViewById(R.id.infoButton);
    infoButton.setOnClickListener(v -> DialogDisplayUtil.display(context, INFO_TEXT));
    return complexBotButtonView;
  }

  private void setTextViewWidth() {
    int maxWidth = (int) (0.8 * itemView.getContext().getResources()
        .getDisplayMetrics().widthPixels);
    messageTextView.setMaxWidth(maxWidth);
  }

  private View createStepView(MessageStep step, String condition) {
    Context context = messageContainer.getContext();

    // Create a ConstraintLayout as the root layout
    ConstraintLayout stepLayout = new ConstraintLayout(context);
    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    );
    stepLayout.setLayoutParams(layoutParams);
    stepLayout.setPadding(4, 4, 4, 4);

    // Create TextView for step instruction
    TextView stepText = new TextView(context);
    stepText.setId(View.generateViewId());
    stepText.setText(step.getInstruction());
    ConstraintLayout.LayoutParams stepTextParams = new ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    stepTextParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
    stepTextParams.rightToLeft = View.generateViewId();  // Placeholder ID for ToggleButton
    stepTextParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
    stepTextParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
    stepTextParams.matchConstraintPercentWidth = 0.65f;
    stepLayout.addView(stepText, stepTextParams);

    // Create ToggleButton or other clickable elements based on condition
    View clickableElement;
    switch (condition) {
      case ChatbotCondition.pervasive -> {
        Button button = new Button(context);
        button.setText("Activate");
        clickableElement = button;
      }
      case ChatbotCondition.reference -> {
        clickableElement = new CheckBox(context);
      }
      default -> {
        ToggleButton toggleButton = new ToggleButton(context);
        toggleButton.setTextOn("Activate");
        toggleButton.setTextOff("Off");
        clickableElement = toggleButton;
      }
    }
    clickableElement.setId(View.generateViewId());
    clickableElement.setOnClickListener(v -> {
      switch (step.getChatAction()) {
        case CLOSE_APPS:
          // Code to close all apps
          break;
        case ACTIVATE_SAVING_MODE:
          // Code to activate energy saving mode
          break;
        case MIGRATE_COMPUTATION:
          if (ChatbotCondition.pervasive.equals(condition)) {
            BatteryDrainerUtil.stop();
          }
          break;
        case NONE:
        default:
          System.out.println("No action required for this step");
          break;
      }
    });
    ConstraintLayout.LayoutParams clickableElementParams = new ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    clickableElementParams.leftToRight = stepText.getId();
    clickableElementParams.rightToLeft = View.generateViewId();  // Placeholder ID for TextView timeText
    clickableElementParams.topToTop = stepText.getId();
    clickableElementParams.matchConstraintPercentWidth = 0.2f;
    stepLayout.addView(clickableElement, clickableElementParams);

    // Create TextView for step time
    TextView timeText = new TextView(context);
    timeText.setId(clickableElementParams.rightToLeft);  // Use the placeholder ID
    timeText.setText(step.getTime());
    ConstraintLayout.LayoutParams timeTextParams = new ConstraintLayout.LayoutParams(0, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    timeTextParams.leftToRight = clickableElement.getId();
    timeTextParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
    timeTextParams.topToTop = clickableElement.getId();
    timeTextParams.matchConstraintPercentWidth = 0.15f;
    stepLayout.addView(timeText, timeTextParams);

    return stepLayout;
  }
}