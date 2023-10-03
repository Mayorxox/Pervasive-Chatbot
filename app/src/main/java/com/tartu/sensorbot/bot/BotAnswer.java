package com.tartu.sensorbot.bot;

import androidx.annotation.NonNull;
import com.tartu.sensorbot.message.MessageStep;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BotAnswer {

  private final List<MessageStep> steps;

  private final String message;
  public BotAnswer(List<MessageStep> steps, String message) {
    this.steps = steps;
    this.message = message;
  }

  public List<MessageStep> getSteps() {
    return Objects.requireNonNullElse(steps, Collections.emptyList());
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (BotAnswer) obj;
    return Objects.equals(this.steps, that.steps) &&
        Objects.equals(this.message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(steps, message);
  }

  @NonNull
  @Override
  public String toString() {
    return "BotAnswer[" +
        "steps=" + steps + ", " +
        "message=" + message + ']';
  }

}
