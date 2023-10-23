package com.tartu.sensorbot.bot;

import android.content.Context;
import com.tartu.sensorbot.message.Message;
import com.tartu.sensorbot.util.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BotResponseGenerator {

  private final BotQuestionMatcher questionMatcher;

  public BotResponseGenerator(Context context) throws IOException {
    InputStream tokenizerModelStream = context.getResources().getAssets()
        .open("opennlp-tokenizer-model.bin");
    this.questionMatcher = new BotQuestionMatcher(tokenizerModelStream);
  }

  public List<Message> generateResponse(String userQuery) {
    String normalizedUserInput = questionMatcher.getBestMatchingQuestion(userQuery);
    if (StringUtil.isNotEmpty(normalizedUserInput) && BotMockQuestions.isQuestionValid(
        normalizedUserInput)) {
      return generateBotResponses(normalizedUserInput);
    }
    return List.of(new Message(BotMessageTemplates.BOT_MESSAGE_NOT_FOUND, true));
  }

  private List<Message> generateBotResponses(String normalizedUserInput) {
    List<Message> responses = new ArrayList<>();
    responses.add(new Message(BotMessageTemplates.BOT_RESPONSE_START, true));
    Optional<BotAnswer> botAnswer = BotMockQuestions.getBotAnswer(normalizedUserInput);
    botAnswer.map(q -> new Message(q.getSteps())).ifPresent(responses::add);
    return responses;
  }
}
