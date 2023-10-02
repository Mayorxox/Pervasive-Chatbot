package com.tartu.sensorbot.bot;

import androidx.annotation.NonNull;
import com.tartu.sensorbot.message.MessageStep;
import java.util.List;
import java.util.Objects;

public class BotAnswer {

  private List<MessageStep> steps;
  private String message;

  public BotAnswer(List<MessageStep> steps, String message) {
    this.steps = steps;
    this.message = message;
  }

  public List<MessageStep> getSteps() {
    return steps;
  }

  public void setSteps(List<MessageStep> steps) {
    this.steps = steps;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @NonNull
  @Override
  public String toString() {
    return "BotAnswer{" +
        "steps=" + steps +
        ", message='" + message + '\'' +
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

    BotAnswer botAnswer = (BotAnswer) o;

    if (!Objects.equals(steps, botAnswer.steps)) {
      return false;
    }
    return Objects.equals(message, botAnswer.message);
  }

  @Override
  public int hashCode() {
    int result = steps != null ? steps.hashCode() : 0;
    result = 31 * result + (message != null ? message.hashCode() : 0);
    return result;
  }
}
