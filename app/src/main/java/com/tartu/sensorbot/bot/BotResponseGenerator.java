package com.tartu.sensorbot.bot;

import android.content.Context;
import androidx.annotation.NonNull;
import com.tartu.sensorbot.chat.ChatbotCondition;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.util.StringUtil;
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
    InputStream tokenizerModelStream = context.getResources().getAssets()
        .open("opennlp-tokenizer-model.bin");
    this.questionMatcher = new BotQuestionMatcher(tokenizerModelStream);
  }

  public List<Message> generateResponse(String userQuery) {
    String normalizedUserInput = questionMatcher.getBestMatchingQuestion(userQuery);
    if (StringUtil.isNotEmpty(normalizedUserInput)
        && BotMockQuestions.MOCK_QUESTIONS.containsKey(normalizedUserInput)) {
      return generateBotResponses(normalizedUserInput);
    }
    return List.of(new Message(BotMessageTemplates.BOT_MESSAGE_NOT_FOUND, true));
  }

  @NonNull
  private List<Message> generateBotResponses(String normalizedUserInput) {
    List<Message> responses = new ArrayList<>();
    responses.add(new Message(BotMessageTemplates.BOT_RESPONSE_START, true));
    getResponseSteps(normalizedUserInput).ifPresent(responses::add);
    if (Objects.equals(condition, ChatbotCondition.reference)) {
      getAdditionalReferenceSteps(normalizedUserInput).ifPresent(responses::add);
    }
    return responses;
  }

  private Optional<Message> getResponseSteps(String userQuery) {
    return Optional.ofNullable(BotMockQuestions.MOCK_QUESTIONS.get(userQuery))
        .map(q -> new Message(q.getSteps()));
  }

  private Optional<Message> getAdditionalReferenceSteps(String userQuery) {
    return Optional.ofNullable(BotMockQuestions.MOCK_QUESTIONS.get(userQuery))
        .map(q -> new Message(q.getMessage(), Message.VIEW_TYPE_COMPLEX_BOT));
  }

}
