package com.tartu.sensorbot.activityHandlers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.tartu.sensorbot.R;
import com.tartu.sensorbot.util.DialogDisplayUtil;

public class UserManualHandler {

  private final TextView userManualText;
  private final Context context;

  public UserManualHandler(View rootView) {
    this.userManualText = rootView.findViewById(R.id.userManualText);
    this.context = rootView.getContext();
    initialize();
  }

  private void initialize() {
    userManualText.setOnClickListener(v -> DialogDisplayUtil.display(context, R.raw.user_manual));
  }
}
