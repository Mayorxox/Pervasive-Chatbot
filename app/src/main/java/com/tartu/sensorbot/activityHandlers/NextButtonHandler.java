package com.tartu.sensorbot.activityHandlers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tartu.sensorbot.ChatActivity;
import com.tartu.sensorbot.R;
import com.tartu.sensorbot.chat.ChatbotCondition;

public class NextButtonHandler {

  private final Button nextButton;
  private final RadioGroup radioGroup;
  private final CheckBox termsCheckbox;
  private final RadioButton pervasiveRadioButton;
  private final Context context;

  public NextButtonHandler(View rootView, Context context) {
    this.context = context;
    this.nextButton = rootView.findViewById(R.id.nextButton);
    this.radioGroup = rootView.findViewById(R.id.radioGroup);
    this.termsCheckbox = rootView.findViewById(R.id.termsCheckbox);
    this.pervasiveRadioButton = rootView.findViewById(R.id.pervasiveRadioButton);

    updateNextButton(false, false);

    initialize();
  }

  private void initialize() {
    nextButton.setOnClickListener(v -> startChatActivity());

    radioGroup.setOnCheckedChangeListener((group, checkedId) ->
        updateNextButton(termsCheckbox.isChecked(), checkedId != -1));

    termsCheckbox.setOnCheckedChangeListener((buttonView, isChecked) ->
        updateNextButton(isChecked, radioGroup.getCheckedRadioButtonId() != -1));
  }

  private void startChatActivity() {
    Intent intent = new Intent(context, ChatActivity.class);
    intent.putExtra("condition", getChatbotCondition());
    context.startActivity(intent);
  }

  private void updateNextButton(boolean isTermsAccepted, boolean isConditionSelected) {
    boolean buttonEnabled = isTermsAccepted && isConditionSelected;
    int backgroundColor = buttonEnabled ? R.color.blue_700 : R.color.darker_gray;

    nextButton.setEnabled(buttonEnabled);
    nextButton.setBackgroundColor(context.getResources().getColor(backgroundColor));
  }

  private String getChatbotCondition() {
    if (pervasiveRadioButton.isChecked()) {
      return ChatbotCondition.pervasive;
    }
    return ChatbotCondition.reference;
  }

}
