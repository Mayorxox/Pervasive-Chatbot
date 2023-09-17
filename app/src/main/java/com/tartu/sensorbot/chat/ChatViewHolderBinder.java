package com.tartu.sensorbot.chat;

import com.tartu.sensorbot.R;
import com.tartu.sensorbot.message.Message;

public class ChatViewHolderBinder {

    public static int getViewLayout(int viewType) {
        switch (viewType) {
            case Message.VIEW_TYPE_USER:
                return R.layout.message_item_user;
            case Message.VIEW_TYPE_COMPLEX_BOT:
                return R.layout.message_item_complex_bot;
            default:
                return R.layout.message_item_bot;
        }
    }

    public static void bindView(ChatViewHolder holder, Message message) {
        switch (message.getViewType()) {
            case Message.VIEW_TYPE_USER:
                holder.bindUserMessage(message);
            case Message.VIEW_TYPE_COMPLEX_BOT:
                holder.bindComplexBotMessage(message);
            default:
                holder.bindBotMessage(message);
        }
    }
}