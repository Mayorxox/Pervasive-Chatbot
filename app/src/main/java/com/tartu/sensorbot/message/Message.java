package com.tartu.sensorbot.message;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private final String text;
    private final int viewType;
    private List<MessageStep> steps = new ArrayList<>();
    public static final int VIEW_TYPE_USER = 1;
    public static final int VIEW_TYPE_BOT = 2;
    public static final int VIEW_TYPE_COMPLEX_BOT = 3;

    public Message(String text, int viewType) {
        this.text = text;
        this.viewType = viewType;
    }

    public Message(String text, boolean isBot) {
       this(text, isBot ? VIEW_TYPE_BOT : VIEW_TYPE_USER);
    }

    public Message(List<MessageStep> steps) {
        this(null, VIEW_TYPE_COMPLEX_BOT);
        this.steps = steps;
    }

    public String getText() {
        return text;
    }

    public int getViewType() {
        return viewType;
    }

    public List<MessageStep> getSteps() { return steps; }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", viewType=" + viewType +
                ", steps=" + steps +
                '}';
    }
}
