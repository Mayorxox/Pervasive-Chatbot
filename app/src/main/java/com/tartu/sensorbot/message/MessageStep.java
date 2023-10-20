package com.tartu.sensorbot.message;

import androidx.annotation.NonNull;
import com.tartu.sensorbot.chat.ChatAction;
import java.util.Objects;

public class MessageStep {

  private final int timeInMinutes;
  private final String instruction;
  private final ChatAction chatAction;

  public MessageStep(int timeInMinutes, String instruction, ChatAction chatAction) {
    this.timeInMinutes = timeInMinutes;
    this.instruction = instruction;
    this.chatAction = chatAction;
  }

  public String getTime() {
    return timeInMinutes + " min";
  }

  public String getInstruction() {
    return instruction;
  }

  public ChatAction getChatAction() {
    return chatAction;
  }

  @NonNull
  @Override
  public String toString() {
    return "MessageStep{" +
        "timeInMinutes=" + timeInMinutes +
        ", instruction='" + instruction + '\'' +
        ", chatAction='" + chatAction + '\'' +
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
    if (chatAction != that.chatAction) {
      return false;
    }
    return Objects.equals(instruction, that.instruction);
  }

  @Override
  public int hashCode() {
    int result = timeInMinutes;
    result = 31 * result + Objects.hashCode(chatAction);
    result = 31 * result + Objects.hashCode(instruction);
    return result;
  }
}
