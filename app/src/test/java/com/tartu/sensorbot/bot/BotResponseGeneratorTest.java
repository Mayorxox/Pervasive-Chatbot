package com.tartu.sensorbot.bot;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import com.tartu.sensorbot.chat.ChatbotCondition;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.message.MessageStep;
import java.io.IOException;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class BotResponseGeneratorTest extends TestCase {

  private Context context;

  @Before
  public void setUp() {
    context = ApplicationProvider.getApplicationContext();
  }

  @Test
  public void testGenerateResponseWithReference() throws IOException {
    // Given
    String userQuery = "How to save energy";
    BotResponseGenerator generator = new BotResponseGenerator(ChatbotCondition.reference, context);

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
        """
            Step 1: Make sure you are connected to the same network with the other device by switching on your bluetooth

            Step 2: Search for the device that's within a range

            Step 3: Select the device you want to migrate computation to

            Step 4: Navigate to your process manager and select the process you want to migrate to

            """,
        Message.VIEW_TYPE_COMPLEX_BOT);
    assertEquals(message, responses.get(2));

    // Check end message
    assertEquals(BotMessageTemplates.BOT_RESPONSE_END, responses.get(3).getText());
  }

  @Test
  public void testGenerateResponseWithoutReference() throws IOException {
    // Given
    String userQuery = "How to save energy";
    BotResponseGenerator generator = new BotResponseGenerator(ChatbotCondition.pervasive, context);

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
