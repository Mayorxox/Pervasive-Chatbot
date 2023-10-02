package com.tartu.sensorbot;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.tartu.sensorbot.chat.ChatbotCondition;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initializeNextButton();
    initializeUserManual();

    RadioGroup radioGroup = findViewById(R.id.radioGroup);
    TextView termsLink = findViewById(R.id.termsLink);
    CheckBox termsCheckbox = findViewById(R.id.termsCheckbox);

    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      updateNextButton(termsCheckbox.isChecked(), checkedId != -1);
    });

    termsCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
      updateNextButton(isChecked, radioGroup.getCheckedRadioButtonId() != -1);
    });

    // SpannableString for clickable "terms and conditions"
    // SpannableString for the full text
    SpannableString spannableString = new SpannableString(
        "By clicking this you are accepting our terms and conditions");
    ClickableSpan clickableSpan = new ClickableSpan() {
      @Override
      public void onClick(@NonNull View widget) {
        // show terms and conditions dialog
      }

      @Override
      public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false); // This will remove the underline from the link
        ds.setColor(Color.BLUE); // Set your desired color for the link part
      }
    };

    int startPos = "By clicking this you are accepting our ".length();
    int endPos = startPos + "terms and conditions".length();
    spannableString.setSpan(clickableSpan, startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    termsLink.setText(spannableString);
    termsLink.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private void initializeUserManual() {
    TextView userManualText = findViewById(R.id.userManualText);
    userManualText.setOnClickListener(v -> {
      InputStream is = getResources().openRawResource(R.raw.user_manual);
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      String line;
      try {
        while ((line = reader.readLine()) != null) {
          sb.append(line).append("\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      String userManualHtml = sb.toString();
      if (VERSION.SDK_INT >= VERSION_CODES.N) {
        showModalDialog(Html.fromHtml(userManualHtml, Html.FROM_HTML_MODE_COMPACT));
      }
    });
  }

  private void initializeNextButton() {
    Button nextButton = findViewById(R.id.nextButton);
    RadioButton pervasiveRadioButton = findViewById(R.id.pervasiveRadioButton);
    updateNextButton(false, false);
    nextButton.setOnClickListener(v -> startChatActivity(pervasiveRadioButton));
  }

  private void startChatActivity(RadioButton pervasiveRadioButton) {
    Intent intent = new Intent(this, ChatActivity.class);
    intent.putExtra("condition", getChatbotCondition(pervasiveRadioButton));
    startActivity(intent);
  }

  private String getChatbotCondition(RadioButton pervasiveRadioButton) {
    if (pervasiveRadioButton.isChecked()) {
      return ChatbotCondition.pervasive;
    }
    return ChatbotCondition.reference;
  }

  private void updateNextButton(boolean isTermsAccepted, boolean isConditionSelected) {
    Button nextButton = findViewById(R.id.nextButton);
    boolean buttonEnabled = isTermsAccepted && isConditionSelected;
    int backgroundColor = buttonEnabled ? R.color.blue_700 : R.color.darker_gray;

    nextButton.setEnabled(buttonEnabled);
    nextButton.setBackgroundColor(getResources().getColor(backgroundColor));
  }

  private void showModalDialog(Spanned content) {
    // Create custom dialog object
    final Dialog dialog = new Dialog(MainActivity.this);

    // Set dialog to not use the title bar
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.dialog_content);

    // Set dialog width and height as described
    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
    lp.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.67);
    dialog.getWindow().setAttributes(lp);

    // Close button
    ImageButton closeButton = dialog.findViewById(R.id.closeButton);
    closeButton.setOnClickListener(view -> dialog.dismiss());

    // Set content text
    TextView dialogContent = dialog.findViewById(R.id.dialogContent);
    dialogContent.setText(content);

    // Allow the dialog to be canceled if touched outside
    dialog.setCanceledOnTouchOutside(true);
    dialog.show();
  }
}
