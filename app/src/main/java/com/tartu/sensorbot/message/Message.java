package com.tartu.sensorbot.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private final String text;
    private final int viewType;
    private List<String> steps = new ArrayList<>();
    private List<String> times = new ArrayList<>();

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

    public Message(String[] steps, String[] times) {
        this("", VIEW_TYPE_COMPLEX_BOT);
        this.steps = Arrays.asList(steps);
        this.times = Arrays.asList(times);
    }

    public String getText() {
        return text;
    }

    public int getViewType() {
        return viewType;
    }

    public List<String> getSteps() { return steps; }

    public List<String> getTimes() { return times; }
}
