package com.tartu.sensorbot.message;

public class Message {
    private final String text;
    private final boolean isUser; // true if this message is from the user, false if it's from the bot

    public Message(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
    }

    public String getText() {
        return text;
    }

    public boolean isUser() {
        return isUser;
    }
}
