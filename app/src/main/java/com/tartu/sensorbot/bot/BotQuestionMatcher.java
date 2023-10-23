package com.tartu.sensorbot.bot;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class BotQuestionMatcher {

  private final Tokenizer tokenizer;

  public BotQuestionMatcher(InputStream tokenizerModelStream) {
    try {
      this.tokenizer = new TokenizerME(new TokenizerModel(tokenizerModelStream));
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize.", e);
    }
  }

  private static String getWordFromDictionary(String word) {
    LevenshteinDistance ld = new LevenshteinDistance(2);
    if (BotMessageTemplates.DICTIONARY.contains(word)) {
      return word;
    }
    return BotMessageTemplates.DICTIONARY.stream()
        .min(Comparator.comparingInt(target -> ld.apply(word, target)))
        .orElse(word);
  }

  public String getBestMatchingQuestion(String userInput) {
    List<String> userInputTokens = tokenizeAndNormalize(userInput);
    String bestMatch = null;
    double maxScore = -1;
    final double THRESHOLD = 0.3;

    for (String mockQuestion : BotMockQuestions.MOCK_QUESTIONS) {
      double score = computeTokenOverlapScore(userInputTokens, tokenizeAndNormalize(mockQuestion));
      if (score > maxScore && score >= THRESHOLD) {
        maxScore = score;
        bestMatch = mockQuestion;
      }
    }
    return bestMatch;
  }

  private List<String> tokenizeAndNormalize(String text) {
    String[] tokens = tokenizer.tokenize(spellCorrection(text));
    List<String> normalizedTokens = new ArrayList<>();
    for (String s : tokens) {
      String token = s.toLowerCase();
      List<String> synonyms = new ArrayList<>(
          Objects.requireNonNull(BotMessageTemplates.SYNONYM_MAP.getOrDefault(token, Set.of(token))));
      normalizedTokens.addAll(synonyms);
    }
    return normalizedTokens;
  }

  public String spellCorrection(String text) {
    return Arrays.stream(text.split("\\s+"))
        .map(BotQuestionMatcher::getWordFromDictionary)
        .collect(Collectors.joining(" "));
  }

  private double computeTokenOverlapScore(List<String> tokensA, List<String> tokensB) {
    long commonTokens = tokensA.stream().filter(tokensB::contains).count();
    return (double) commonTokens / (tokensA.size() + tokensB.size() - commonTokens);
  }
}
