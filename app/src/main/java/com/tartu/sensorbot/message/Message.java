package com.tartu.sensorbot.message;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Message {

  public static final int VIEW_TYPE_USER = 1;
  public static final int VIEW_TYPE_BOT = 2;
  public static final int VIEW_TYPE_COMPLEX_BOT = 3;
  private final String text;
  private final int viewType;
  private List<MessageStep> steps = new ArrayList<>();

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

  public List<MessageStep> getSteps() {
    return steps;
  }

  @NonNull
  @Override
  public String toString() {
    return "Message{" +
        "text='" + text + '\'' +
        ", viewType=" + viewType +
        ", steps=" + steps +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Message message = (Message) o;

    if (viewType != message.viewType) {
      return false;
    }
    if (!Objects.equals(text, message.text)) {
      return false;
    }
    return Objects.equals(steps, message.steps);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(text);
    result = 31 * result + viewType;
    result = 31 * result + Objects.hashCode(steps);
    return result;
  }
}
