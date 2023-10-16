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
              Step 1: Ensure Network Connectivity. Connect your Android device to a network.
              
              Step 2: Install and Open an Offloading App. Download, install, and open a computational offloading app.
              
              Step 3: Securely log in or authenticate your device and the target device/server.
              
              Step 4: Identify and select the computational task to be offloaded.
              
              Step 5: Select the device or server to which the task will be offloaded.
              
              Step 6: Send the task and necessary data to the chosen device/server.
              
              Step 7: Track the progress of the offloaded computational task.
              
              Step 8: Once complete, retrieve and check the results for accuracy.
              
              Step 9: Safely disconnect from the target device/server.
              
              Step 10: Store the retrieved data securely or use it as needed.
              """,
        Message.VIEW_TYPE_COMPLEX_BOT);
    assertEquals(message, responses.get(2));

    // Check end message
    assertEquals(BotMessageTemplates.BOT_RESPONSE_END, responses.get(3).getText());
  }

  @Test
  public void testGenerateResponseWithoutReference() throws IOException {
    // Given
    String userQuery = "How tosave battery";
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
