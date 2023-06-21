package com.tartu.sensorbot.message;

public class Message {
    private final String text;
    private final boolean isUser; // true if this message is from the user, false if it's from the bot

    private final boolean isSuggestion;

    public Message(String text, boolean isUser, boolean isSuggestion) {
        this.text = text;
        this.isUser = isUser;
        this.isSuggestion = isSuggestion;
    }

    public String getText() {
        return text;
    }

    public boolean isUser() {
        return isUser;
    }

    public boolean isSuggestion() {
        return isSuggestion;
    }
}
