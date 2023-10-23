package com.tartu.sensorbot.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.tartu.sensorbot.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DialogUtil {

  public static void display(Context context, int htmlResource) {
    InputStream is = context.getResources().openRawResource(htmlResource);
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
      Spanned content = Html.fromHtml(userManualHtml, Html.FROM_HTML_MODE_COMPACT);
      showModalDialog(context, content.toString());
    }
  }

  public static void display(Context context, String content) {
    showModalDialog(context, content);
  }

  private static void showModalDialog(Context context, String content) {
    // Create custom dialog object
    final Dialog dialog = new Dialog(context);

    // Set dialog to not use the title bar
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.dialog_content);

    // Set dialog width and height as described
    LayoutParams layoutParams = getScreenLayoutParams(context, dialog);
    dialog.getWindow().setAttributes(layoutParams);

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

  public static void showLoadingDialog(Context context, int delayMillis, Runnable onDismissed) {
    Dialog dialog = new Dialog(context);
    dialog.setContentView(R.layout.custom_loading_dialog);
    dialog.setCancelable(false); // Dialog cannot be canceled by the user
    dialog.show();

    // Automatically close the dialog after delayMillis milliseconds
    new Handler().postDelayed(() -> {
      if (dialog.isShowing()) {
        dialog.dismiss();
        if (onDismissed != null) {
          onDismissed.run();
        }
      }
    }, delayMillis);
  }

  @NonNull
  private static LayoutParams getScreenLayoutParams(Context context, Dialog dialog) {
    LayoutParams lp = new LayoutParams();
    lp.copyFrom(dialog.getWindow().getAttributes());
    lp.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.85);
    lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.67);
    return lp;
  }

}
