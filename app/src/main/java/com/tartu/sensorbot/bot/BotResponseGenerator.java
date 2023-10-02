package com.tartu.sensorbot.bot;

import static com.tartu.sensorbot.bot.BotMessageTemplates.questions;

import android.content.Context;
import com.tartu.sensorbot.chat.ChatbotCondition;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.message.MessageStep;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BotResponseGenerator {

  private final String condition;
  private final BotQuestionMatcher questionMatcher;

  public BotResponseGenerator(String condition, Context context) throws IOException {
    this.condition = condition;
    InputStream tokenizerModelStream = context.getResources().getAssets().open("opennlp-tokenizer-model.bin");
    InputStream posModelStream = context.getResources().getAssets().open("opennlp-pos-model.bin");

    this.questionMatcher = new BotQuestionMatcher(tokenizerModelStream, posModelStream);
  }

  public List<Message> generateResponse(String userQuery) {
    String normalizedUserInput = questionMatcher.getBestMatchingQuestion(userQuery);
    List<Message> responses = new ArrayList<>();
    if (!questions.containsKey(normalizedUserInput)){
      return responses;
    }

    responses.add(new Message(BotMessageTemplates.BOT_RESPONSE_START, true));
    generateStepsAndTimes(normalizedUserInput).ifPresent(responses::add);
    if (Objects.equals(condition, ChatbotCondition.reference)) {
      getAdditionalReferenceSteps(normalizedUserInput).ifPresent(responses::add);
    }
    responses.add(new Message(BotMessageTemplates.BOT_RESPONSE_END, true));
    return responses;
  }

  private Optional<Message> generateStepsAndTimes(String userQuery) {
    if (questions.containsKey(userQuery)) {
      List<MessageStep> steps = questions.get(userQuery).getSteps();
      return Optional.of(new Message(steps));
    }
    return Optional.empty();
  }

  private Optional<Message> getAdditionalReferenceSteps(String userQuery) {
    if (questions.containsKey(userQuery)) {
      String message = questions.get(userQuery).getMessage();
      return Optional.of(new Message(message, Message.VIEW_TYPE_COMPLEX_BOT));
    }
    return Optional.empty();
  }

}
