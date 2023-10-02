package com.tartu.sensorbot.message;

import java.util.Objects;

public class MessageStep {

  private final int timeInMinutes;
  private final String instruction;

  public MessageStep(int timeInMinutes, String instruction) {
    this.timeInMinutes = timeInMinutes;
    this.instruction = instruction;
  }

  public String getTime() {
    return timeInMinutes + " min";
  }

  public String getInstruction() {
    return instruction;
  }

  @Override
  public String toString() {
    return "MessageStep{" +
        "timeInMinutes=" + timeInMinutes +
        ", instruction='" + instruction + '\'' +
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

    MessageStep that = (MessageStep) o;

    if (timeInMinutes != that.timeInMinutes) {
      return false;
    }
    return Objects.equals(instruction, that.instruction);
  }

  @Override
  public int hashCode() {
    int result = timeInMinutes;
    result = 31 * result + Objects.hashCode(instruction);
    return result;
  }
}
