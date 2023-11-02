package com.tartu.sensorbot.message;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;
import org.assertj.core.api.Assertions;

public class MessageTest extends TestCase {

  public void testMessageInitializationWithViewType() {
    Message message = new Message("Hello", Message.VIEW_TYPE_BOT);

    Assertions.assertThat(message.getText()).isEqualTo("Hello");
    Assertions.assertThat(message.getViewType()).isEqualTo(Message.VIEW_TYPE_BOT);
    Assertions.assertThat(message.getSteps()).isEmpty();
  }

  public void testMessageInitializationWithIsBot() {
    Message userMessage = new Message("Hello", false);
    Message botMessage = new Message("Hello", true);

    Assertions.assertThat(userMessage.getViewType()).isEqualTo(Message.VIEW_TYPE_USER);
    Assertions.assertThat(botMessage.getViewType()).isEqualTo(Message.VIEW_TYPE_BOT);
  }

  public void testMessageInitializationWithSteps() {
    MessageStep step1 = new MessageStep(1, "test-1", null, List.of());
    MessageStep step2 = new MessageStep(2, "test-2", null, List.of());
    Message message = new Message(Arrays.asList(step1, step2));

    Assertions.assertThat(message.getText()).isNull();
    Assertions.assertThat(message.getViewType()).isEqualTo(Message.VIEW_TYPE_COMPLEX_BOT);
    Assertions.assertThat(message.getSteps()).containsExactly(step1, step2);
  }

  public void testMessageToString() {
    Message message = new Message("Hello", Message.VIEW_TYPE_BOT);
    Assertions.assertThat(message.toString()).contains("Hello", "2");
  }

  public void testMessageEquals() {
    Message message1 = new Message("Hello", Message.VIEW_TYPE_BOT);
    Message message2 = new Message("Hello", Message.VIEW_TYPE_BOT);

    Assertions.assertThat(message1).isEqualTo(message2);
  }

  public void testMessageNotEqualsDifferentText() {
    Message message1 = new Message("Hello", Message.VIEW_TYPE_BOT);
    Message message2 = new Message("Hi", Message.VIEW_TYPE_BOT);

    Assertions.assertThat(message1).isNotEqualTo(message2);
  }

  public void testMessageNotEqualsDifferentType() {
    Message message1 = new Message("Hello", Message.VIEW_TYPE_BOT);
    Message message2 = new Message("Hello", Message.VIEW_TYPE_USER);

    Assertions.assertThat(message1).isNotEqualTo(message2);
  }

  public void testMessageNotEqualsDifferentSteps() {
    MessageStep step1 = new MessageStep(1, "test-1", null, List.of());
    MessageStep step2 = new MessageStep(2, "test-2", null, List.of());
    Message message1 = new Message(Collections.singletonList(step1));
    Message message2 = new Message(Collections.singletonList(step2));

    Assertions.assertThat(message1).isNotEqualTo(message2);
  }

  public void testMessageHashCode() {
    Message message1 = new Message("Hello", Message.VIEW_TYPE_BOT);
    Message message2 = new Message("Hello", Message.VIEW_TYPE_BOT);

    Assertions.assertThat(message1.hashCode()).isEqualTo(message2.hashCode());
  }
}
