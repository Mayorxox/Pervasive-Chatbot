package com.tartu.sensorbot.bot;

import static com.tartu.sensorbot.bot.BotMessageTemplates.DICTIONARY;
import static com.tartu.sensorbot.bot.BotMessageTemplates.SYNONYM_MAP;
import static com.tartu.sensorbot.bot.BotMessageTemplates.questions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class BotQuestionMatcher {

  private final Tokenizer tokenizer;
  private final POSTaggerME posTagger;
  private final List<String> mockQuestions;

  public BotQuestionMatcher(InputStream tokenizerModelStream, InputStream posModelStream) {
    try {
      this.tokenizer = new TokenizerME(new TokenizerModel(tokenizerModelStream));
      this.posTagger = new POSTaggerME(new POSModel(posModelStream));
      this.mockQuestions = new ArrayList<>(questions.keySet());
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize.", e);
    }
  }

  public String getBestMatchingQuestion(String userInput) {
    List<String> userInputTokens = tokenizeAndNormalize(userInput);
    String bestMatch = null;
    double maxScore = -1;

    for (String mockQuestion : mockQuestions) {
      double score = computeTokenOverlapScore(userInputTokens, tokenizeAndNormalize(mockQuestion));
      if (score > maxScore) {
        maxScore = score;
        bestMatch = mockQuestion;
      }
    }
    return bestMatch;
  }

  private List<String> tokenizeAndNormalize(String text) {
    String[] tokens = tokenizer.tokenize(spellCorrection(text));
    String[] posTags = posTagger.tag(tokens);

    List<String> normalizedTokens = new ArrayList<>();
    for (int i = 0; i < tokens.length; i++) {
      String token = tokens[i].toLowerCase();
      List<String> synonyms = new ArrayList<>(
          Objects.requireNonNull(SYNONYM_MAP.getOrDefault(token, Set.of(token))));
      if (posTags[i].startsWith("NN")) {  // Consider more POS tags as needed
        normalizedTokens.addAll(synonyms);
        normalizedTokens.addAll(synonyms);
      } else {
        normalizedTokens.addAll(synonyms);
      }
    }
    return normalizedTokens;
  }

  public String spellCorrection(String text) {
    return Arrays.stream(text.split("\\s+"))
        .map(BotQuestionMatcher::getWordFromDictionary)
        .collect(Collectors.joining(" "));
  }

  private static String getWordFromDictionary(String word) {
    LevenshteinDistance ld = new LevenshteinDistance(2);
    if (DICTIONARY.contains(word)) {
      return word;
    }
    return DICTIONARY.stream()
        .min(Comparator.comparingInt(target -> ld.apply(word, target)))
        .orElse(word);
  }

  private double computeTokenOverlapScore(List<String> tokensA, List<String> tokensB) {
    long commonTokens = tokensA.stream().filter(tokensB::contains).count();
    return (double) commonTokens / (tokensA.size() + tokensB.size() - commonTokens);
  }
}
