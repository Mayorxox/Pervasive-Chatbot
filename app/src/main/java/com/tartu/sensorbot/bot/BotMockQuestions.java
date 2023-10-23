package com.tartu.sensorbot.bot;

import com.tartu.sensorbot.chat.ChatAction;
import com.tartu.sensorbot.message.CheckboxEnabler;
import com.tartu.sensorbot.message.MessageStep;
import com.tartu.sensorbot.util.StringUtil;
import java.util.List;
import java.util.Optional;

public class BotMockQuestions {

  public static final List<String> MOCK_QUESTIONS = List.of(
      "How can I conserve or save energy on my phone?",
      "How can I maximize my phone's battery duration?",
      "How can I use computational offloading to extend battery life when it's low?"
  );

  public static boolean isQuestionValid(String question) {
    if (StringUtil.isNotEmpty(question)) {
      return MOCK_QUESTIONS.contains(question);
    }
    return false;
  }

  public static Optional<BotAnswer> getBotAnswer(String question) {
    if (isQuestionValid(question)) {
      return Optional.of(getMockAnswer());
    }
    return Optional.empty();
  }

  private static BotAnswer getMockAnswer() {
    MessageStep close_the_background_apps = new MessageStep(2, "Close the background apps",
        ChatAction.CLOSE_APPS, CheckboxEnabler.NONE);
    MessageStep activate_battery_saving_mode = new MessageStep(2, "Activate battery saving mode",
        ChatAction.ACTIVATE_SAVING_MODE, CheckboxEnabler.NONE);
    List<MessageStep> referenceSteps = List.of(
        new MessageStep(0,
            "1. Ensure Bluetooth is enabled on both the Android device and the target device",
            ChatAction.NONE, CheckboxEnabler.BLUETOOTH_ON),
        new MessageStep(0,
            "2. Connect to target device via bluetooth.",
            ChatAction.NONE,
            CheckboxEnabler.BLUETOOTH_CONNECTED),
        new MessageStep(0,
            "3. Make sure you turned off bluetooth device.",
            ChatAction.NONE,
            CheckboxEnabler.NONE)
    );
    MessageStep perform_computational_offloading = new MessageStep(6,
        "Perform computational offloading", ChatAction.MIGRATE_COMPUTATION, referenceSteps);

    List<MessageStep> messageSteps = List.of(
        close_the_background_apps, activate_battery_saving_mode, perform_computational_offloading
    );

    return new BotAnswer(messageSteps, "");
  }
}
