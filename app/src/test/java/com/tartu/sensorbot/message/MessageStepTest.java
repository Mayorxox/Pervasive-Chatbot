package com.tartu.sensorbot.message;

import com.tartu.sensorbot.chat.ChatAction;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.assertj.core.api.Assertions;

public class MessageStepTest extends TestCase {

  public void testInitializationWithCheckboxEnabler() {
    MessageStep step = new MessageStep(10, "instruction", ChatAction.CLOSE_APPS,
        CheckboxEnabler.BLUETOOTH_ON);

    Assertions.assertThat(step.getTimeInMinutes()).isEqualTo(10);
    Assertions.assertThat(step.getInstruction()).isEqualTo("instruction");
    Assertions.assertThat(step.getChatAction()).isEqualTo(ChatAction.CLOSE_APPS);
    Assertions.assertThat(step.getAdditionalSteps()).isEmpty();
  }

  public void testInitializationWithAdditionalSteps() {
    List<MessageStep> additionalSteps = Arrays.asList(
        new MessageStep(5, "step1", ChatAction.CLOSE_APPS, CheckboxEnabler.BLUETOOTH_ON),
        new MessageStep(5, "step2", ChatAction.ACTIVATE_SAVING_MODE, CheckboxEnabler.NONE)
    );
    MessageStep step = new MessageStep(10, "instruction", ChatAction.MIGRATE_COMPUTATION,
        additionalSteps);

    Assertions.assertThat(step.getAdditionalSteps()).hasSize(2);
  }

  public void testGetTime() {
    MessageStep step = new MessageStep(10, "instruction", ChatAction.CLOSE_APPS,
        CheckboxEnabler.NONE);
    Assertions.assertThat(step.getTime()).isEqualTo("10 min");
  }

  public void testToString() {
    MessageStep step = new MessageStep(10, "instruction", ChatAction.CLOSE_APPS,
        CheckboxEnabler.NONE);
    Assertions.assertThat(step.toString()).contains("10", "instruction", "CLOSE_APPS");
  }

  public void testEquals() {
    MessageStep step1 = new MessageStep(10, "instruction", ChatAction.CLOSE_APPS,
        CheckboxEnabler.NONE);
    MessageStep step2 = new MessageStep(10, "instruction", ChatAction.CLOSE_APPS,
        CheckboxEnabler.NONE);

    Assertions.assertThat(step1).isEqualTo(step2);
  }

  public void testNotEqualsDifferentInstruction() {
    MessageStep step1 = new MessageStep(10, "instruction", ChatAction.CLOSE_APPS,
        CheckboxEnabler.NONE);
    MessageStep step2 = new MessageStep(10, "different", ChatAction.CLOSE_APPS,
        CheckboxEnabler.NONE);

    Assertions.assertThat(step1).isNotEqualTo(step2);
  }

  public void testHashCode() {
    MessageStep step1 = new MessageStep(10, "instruction", ChatAction.CLOSE_APPS,
        CheckboxEnabler.NONE);
    MessageStep step2 = new MessageStep(10, "instruction", ChatAction.CLOSE_APPS,
        CheckboxEnabler.NONE);

    Assertions.assertThat(step1.hashCode()).isEqualTo(step2.hashCode());
  }
}
