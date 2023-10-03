package com.tartu.sensorbot.activityHandlers;

import android.app.Dialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.tartu.sensorbot.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserManualHandler implements ActivityHandler {

  private final TextView userManualText;
  private final Context context;

  public UserManualHandler(View rootView, Context context) {
    this.context = context;
    this.userManualText = rootView.findViewById(R.id.userManualText);
  }

  @Override
  public void initialize() {
    userManualText.setOnClickListener(v -> {
      InputStream is = context.getResources().openRawResource(R.raw.user_manual);
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

  private void showModalDialog(Spanned content) {
    // Create custom dialog object
    final Dialog dialog = new Dialog(context);

    // Set dialog to not use the title bar
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.dialog_content);

    // Set dialog width and height as described
    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.85);
    lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.67);
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
