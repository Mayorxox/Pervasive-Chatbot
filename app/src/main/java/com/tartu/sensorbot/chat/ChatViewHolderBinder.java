package com.tartu.sensorbot.chat;

import com.tartu.sensorbot.R;

import java.util.Objects;

public class ChatViewHolderBinder {

    public static int getViewLayout(int viewType, String condition) {
        switch (viewType) {
            case Message.VIEW_TYPE_USER:
                return R.layout.message_item_user;
            case Message.VIEW_TYPE_COMPLEX_BOT:
                if (Objects.equals(condition, ChatbotCondition.pervasive)) {
                    return R.layout.message_item_complex_bot_pc;
                }
                return R.layout.message_item_complex_bot_rc;
            default:
                return R.layout.message_item_bot;
        }
    }

    public static void bindView(ChatViewHolder holder, Message message) {
        if (message.viewType == Message.VIEW_TYPE_USER) {
            holder.bindUserMessage(message);
        } else if (message.viewType == Message.VIEW_TYPE_BOT) {
            holder.bindBotMessage(message);
        } else if (message.viewType == Message.VIEW_TYPE_COMPLEX_BOT) {
            holder.bindComplexBotMessage(message);
        }
    }
}