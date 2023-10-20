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
import com.tartu.sensorbot.logger.Logger;
import com.tartu.sensorbot.util.BatteryDrainerUtil;
import java.time.LocalDateTime;

public class NextButtonHandler {

  private final Button nextButton;
  private final RadioGroup radioGroup;
  private final CheckBox termsCheckbox;
  private final RadioButton pervasiveRadioButton;
  private final Context context;

  public NextButtonHandler(View rootView) {
    this.nextButton = rootView.findViewById(R.id.nextButton);
    this.radioGroup = rootView.findViewById(R.id.radioGroup);
    this.termsCheckbox = rootView.findViewById(R.id.termsCheckbox);
    this.pervasiveRadioButton = rootView.findViewById(R.id.pervasiveRadioButton);
    this.context = rootView.getContext();

    updateNextButton(false, false);

    initialize();
  }

  private void initialize() {
    nextButton.setOnClickListener(v -> startChatActivity());

    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      updateNextButton(termsCheckbox.isChecked(), checkedId != -1);
    });

    termsCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
      Logger.log(context, String.format("%s: User is %s terms and conditions", LocalDateTime.now().toString(), isChecked ? "Accepted" : "Declined"));
      updateNextButton(isChecked, radioGroup.getCheckedRadioButtonId() != -1);
    });
  }

  private void startChatActivity() {
    Intent intent = new Intent(context, ChatActivity.class);
    String chatbotCondition = getChatbotCondition();
    Logger.log(context, String.format("%s: User choose conditions - %s", LocalDateTime.now().toString(), chatbotCondition));
    intent.putExtra("condition", chatbotCondition);
    BatteryDrainerUtil.start();
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
