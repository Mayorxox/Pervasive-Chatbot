package com.tartu.sensorbot.bot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BotMessageTemplates {

  public static final String INITIAL_BOT_MESSAGE = "Hello, how can I help you today?";
  public static final String BOT_RESPONSE_START = "Sure! Here are some suggestions on how you can do that";
  public static final String BOT_MESSAGE_NOT_FOUND = "I'm not sure about that. Can you provide more details or ask in a different way?";

  public static final Set<String> DICTIONARY = Set.of(
      "maximize", "extend", "battery", "life", "phone", "critically", "low", "conserve", "power",
      "save", "settings", "mode", "offload", "tasks", "drain", "consumption", "functions",
      "features", "apps"
  );

  public static final Map<String, Set<String>> SYNONYM_MAP = new HashMap<>() {{
    put("maximize", Set.of("conserve", "save"));
    put("extend", Set.of("lengthen"));
    put("battery", Set.of("power", "energy"));
    put("life", Set.of("duration", "longevity"));
    put("critically", Set.of("urgently", "significantly"));
    put("low", Set.of("depleted", "drained"));
    put("mode", Set.of("setting", "configuration"));
    put("offload", Set.of("transfer", "delegate"));
    put("tasks", Set.of("operations", "functions"));
    put("drain", Set.of("deplete", "consume"));
    put("consumption", Set.of("use", "usage"));
  }};
}
