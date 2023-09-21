package com.tartu.sensorbot.bot;

import com.tartu.sensorbot.chat.ChatbotCondition;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.message.MessageStep;
import java.util.List;
import junit.framework.TestCase;

public class BotResponseGeneratorTest extends TestCase {

  public void testGenerateResponseWithReference() {
    // Given
    String userQuery = "How to save energy";
    BotResponseGenerator generator = new BotResponseGenerator(ChatbotCondition.reference);

    // When
    List<Message> responses = generator.generateResponse(userQuery);

    // Then
    assertEquals(4, responses.size());

    // Check start message
    assertEquals(BotMessageTemplates.BOT_RESPONSE_START, responses.get(0).getText());

    // check message steps
    List<MessageStep> messageSteps = List.of(
        new MessageStep(2, "Close all the apps"),
        new MessageStep(2, "Activate saving mode"),
        new MessageStep(6, "Migrate computation to your friends or surrounding devices?")
    );
    assertEquals(new Message(messageSteps), responses.get(1));

    // check reference messages
    Message message = new Message(
        "Step 1: Make sure you are connected to the same network with the other device by switching on your bluetooth\n\n"
            +
            "Step 2: Search for the device that's within a range\n\n " +
            "Step 3: Select the device you want to migrate computation to \n\n " +
            "Step 4: Navigate to your process manager and select the process you want to migrate to\n\n",
        Message.VIEW_TYPE_COMPLEX_BOT);
    assertEquals(message, responses.get(2));

    // Check end message
    assertEquals(BotMessageTemplates.BOT_RESPONSE_END, responses.get(3).getText());
  }

  public void testGenerateResponseWithoutReference() {
    // Given
    String userQuery = "How to save energy";
    BotResponseGenerator generator = new BotResponseGenerator(ChatbotCondition.pervasive);

    // When
    List<Message> responses = generator.generateResponse(userQuery);

    // Then
    assertEquals(3, responses.size());

    // Check start message
    assertEquals(BotMessageTemplates.BOT_RESPONSE_START, responses.get(0).getText());

    // check message steps
    List<MessageStep> messageSteps = List.of(
        new MessageStep(2, "Close all the apps"),
        new MessageStep(2, "Activate saving mode"),
        new MessageStep(6, "Migrate computation to your friends or surrounding devices?")
    );
    assertEquals(new Message(messageSteps), responses.get(1));

    // Check end message
    assertEquals(BotMessageTemplates.BOT_RESPONSE_END, responses.get(2).getText());
  }
}
