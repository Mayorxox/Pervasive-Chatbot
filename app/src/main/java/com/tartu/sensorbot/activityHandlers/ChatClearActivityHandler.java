package com.tartu.sensorbot.activityHandlers;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.tartu.sensorbot.R;
import com.tartu.sensorbot.bot.BotMessageTemplates;
import com.tartu.sensorbot.message.MessageAdapter;

public class ChatClearActivityHandler {

  private final MessageAdapter messageAdapter;
  private final Context context;

  public ChatClearActivityHandler(View rootView, Context context, MessageAdapter messageAdapter) {
    this.messageAdapter = messageAdapter;
    this.context = context;
    ImageView menuIcon = rootView.findViewById(R.id.menuIcon);

    menuIcon.setOnClickListener(v -> showClearConfirmationDialog());
  }

  private void showClearConfirmationDialog() {
    new AlertDialog.Builder(context)
        .setTitle("Clear Chat")
        .setMessage("Are you sure you want to start a new chat?")
        .setPositiveButton("Yes", (dialog, which) -> clearChat())
        .setNegativeButton("No", null)
        .show();
  }

  private void clearChat() {
    messageAdapter.clearMessages();
    messageAdapter.addMessage(BotMessageTemplates.INITIAL_BOT_MESSAGE, true);
  }
}
