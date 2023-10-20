package com.tartu.sensorbot.bot;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import com.tartu.sensorbot.chat.ChatAction;
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
    assertEquals(3, responses.size());

    // Check start message
    assertEquals(BotMessageTemplates.BOT_RESPONSE_START, responses.get(0).getText());

    // check message steps
    List<MessageStep> messageSteps = List.of(
        new MessageStep(2, "Close the background apps", ChatAction.CLOSE_APPS),
        new MessageStep(2, "Activate saving mode", ChatAction.ACTIVATE_SAVING_MODE),
        new MessageStep(6, "Migrate computation to your friends or surrounding devices?",
            ChatAction.MIGRATE_COMPUTATION)
    );
    assertEquals(new Message(messageSteps), responses.get(1));

    // check reference messages
    Message message = new Message(
        """
          Step 1. Setup: Install and open a computational offloading app, ensuring network connectivity.
          
          Step 2. Task Selection: Choose the computational task and target device/server.
          
          Step 3. Execution: Send the task and data to the destination, monitor progress, and retrieve results.
          
          Step 4. Finalize: Safely disconnect, store, or utilize the obtained data as required.
          """,
        Message.VIEW_TYPE_COMPLEX_BOT);
    assertEquals(message, responses.get(2));
  }

  @Test
  public void testGenerateResponseWithoutReference() throws IOException {
    // Given
    String userQuery = "how to save battery ";
    BotResponseGenerator generator = new BotResponseGenerator(ChatbotCondition.pervasive, context);

    // When
    List<Message> responses = generator.generateResponse(userQuery);

    // Then
    assertEquals(2, responses.size());

    // Check start message
    assertEquals(BotMessageTemplates.BOT_RESPONSE_START, responses.get(0).getText());

    // check message steps
    List<MessageStep> messageSteps = List.of(
        new MessageStep(2, "Close the background apps", ChatAction.CLOSE_APPS),
        new MessageStep(2, "Activate saving mode", ChatAction.ACTIVATE_SAVING_MODE),
        new MessageStep(6, "Migrate computation to your friends or surrounding devices?",
            ChatAction.MIGRATE_COMPUTATION)
    );
    assertEquals(new Message(messageSteps), responses.get(1));
  }
}
