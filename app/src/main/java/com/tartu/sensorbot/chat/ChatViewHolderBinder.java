package com.tartu.sensorbot.chat;

import com.tartu.sensorbot.R;
import com.tartu.sensorbot.message.Message;

public class ChatViewHolderBinder {

  public static int getViewLayout(int viewType) {
    if (viewType == Message.VIEW_TYPE_USER) {
      return R.layout.message_item_user;
    } else if (viewType == Message.VIEW_TYPE_BOT) {
      return R.layout.message_item_bot;
    }
    return R.layout.message_item_complex_bot;
  }

  public static void bindView(ChatViewHolder holder, Message message) {
    if (message.getViewType() == Message.VIEW_TYPE_USER) {
      holder.bindUserMessage(message);
    } else if (message.getViewType() == Message.VIEW_TYPE_BOT) {
      holder.bindBotMessage(message);
    } else {
      holder.bindComplexBotMessage(message);
    }
  }
}