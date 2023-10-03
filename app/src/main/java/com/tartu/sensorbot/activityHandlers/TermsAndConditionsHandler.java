package com.tartu.sensorbot.activityHandlers;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tartu.sensorbot.R;

public class TermsAndConditionsHandler implements ActivityHandler {

  private static final String TERMS_TEXT = "By clicking this you are accepting our terms and conditions";
  private final TextView termsLink;

  public TermsAndConditionsHandler(View rootView) {
    this.termsLink = rootView.findViewById(R.id.termsLink);
  }

  @Override
  public void initialize() {
    ClickableSpan clickableSpan = getClickableSpan();
    int startPos = "By clicking this you are accepting our ".length();
    int endPos = startPos + "terms and conditions".length();
    SpannableString spannableString = new SpannableString(TERMS_TEXT);
    spannableString.setSpan(clickableSpan, startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    termsLink.setText(spannableString);
    termsLink.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private ClickableSpan getClickableSpan() {
    return new ClickableSpan() {
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
  }
}
