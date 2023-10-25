package com.tartu.sensorbot.chat;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import com.tartu.sensorbot.R;
import com.tartu.sensorbot.message.MessageStep;
import java.util.Objects;

public class ChatStepView {

  private final MessageStep step;
  private final String condition;
  private final Context context;
  private CheckManager checkManager;

  public ChatStepView(MessageStep step, String condition, Context context) {
    this.step = step;
    this.condition = condition;
    this.context = context;
  }

  public ChatStepView(MessageStep step, String condition, Context context, CheckManager checkManager) {
    this(step, condition, context);
    this.checkManager = checkManager;
  }

  public View create(boolean isAdditionalStep) {
    ConstraintLayout stepLayout = createStepLayout();

    if (isAdditionalStep) {
      stepLayout.setPadding(84, 4, 4, 4);
    }
    TextView stepText = createDefaultTextView(step.getInstruction(), 0.55f);
    stepLayout.addView(stepText);

    if (isPervasive() || !ChatAction.MIGRATE_COMPUTATION.equals(step.getChatAction())) {
      View clickableElement = createClickableElement();

      if (Objects.nonNull(checkManager) && clickableElement instanceof CheckBox) {
        checkManager.addCheckBox((CheckBox) clickableElement);
      }

      LayoutParams clickableElementParams = createLayoutParams(stepText.getId(),
          isAdditionalStep ? 0.2f : 0.3f);
      stepLayout.addView(clickableElement, clickableElementParams);

      if (step.getTimeInMinutes() > 0) {
        LinearLayout timeLayout = getTimeLayout();
        LayoutParams timeLayoutParams = createLayoutParams(clickableElement.getId(), 0.15f);
        stepLayout.addView(timeLayout, timeLayoutParams);
      }
    }
    return stepLayout;
  }

  @NonNull
  private LinearLayout getTimeLayout() {
    // Create a LinearLayout to hold the time icon and text
    LinearLayout timeLayout = new LinearLayout(context);
    timeLayout.setOrientation(LinearLayout.VERTICAL);
    timeLayout.setGravity(Gravity.CENTER_HORIZONTAL);
    timeLayout.setId(View.generateViewId());

    ImageView timeIcon = new ImageView(context);
    timeIcon.setImageResource(R.drawable.ic_time);
    timeIcon.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ));

    TextView timeText = createDefaultTextView(step.getTime(), 0.15f);
    LinearLayout.LayoutParams timeTextParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    );
    timeText.setLayoutParams(timeTextParams);

    timeLayout.addView(timeIcon);
    timeLayout.addView(timeText);
    return timeLayout;
  }

  private ConstraintLayout createStepLayout() {
    ConstraintLayout stepLayout = new ConstraintLayout(context);
    LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT);
    stepLayout.setLayoutParams(layoutParams);
    stepLayout.setPadding(4, 4, 4, 4);
    return stepLayout;
  }

  @NonNull
  private TextView createDefaultTextView(String text, float widthPercent) {
    TextView textView = new TextView(context);
    textView.setId(View.generateViewId());
    textView.setText(text);

    LayoutParams layoutParams = createLayoutParams(0, widthPercent);
    textView.setLayoutParams(layoutParams);

    return textView;
  }

  private LayoutParams createLayoutParams(int nextToViewId, float widthPercent) {
    LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
    layoutParams.matchConstraintPercentWidth = widthPercent;
    if (nextToViewId != 0) {
      layoutParams.leftToRight = nextToViewId;
    }
    layoutParams.topToTop = LayoutParams.PARENT_ID;
    layoutParams.bottomToBottom = LayoutParams.PARENT_ID;
    return layoutParams;
  }

  private View createClickableElement() {
    step.setClickableElement(context, isPervasive());
    return step.getClickableElement();
  }

  private boolean isPervasive() {
    return ChatbotCondition.pervasive.equals(condition);
  }
}