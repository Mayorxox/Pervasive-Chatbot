package com.tartu.sensorbot.bot;

import com.tartu.sensorbot.message.MessageStep;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BotMessageTemplates {

  public static final String INITIAL_BOT_MESSAGE = "Hello, how can I help you today?";
  public static final String BOT_RESPONSE_START = "Sure! Here are some suggestions on how you can do that";
  public static final String BOT_RESPONSE_END = "Will that be all?";

  public static final Map<String, BotAnswer> questions = Map.of(
      "How can I maximize or extend the remaining battery life on my phone when it's critically low?",
      new BotAnswer(
          List.of(
              new MessageStep(2, "Close all the apps"),
              new MessageStep(2, "Activate saving mode"),
              new MessageStep(6, "Migrate computation to your friends or surrounding devices?")
          ),
          """
             Step 1: Make sure you are connected to the same network with the other device by switching on your bluetooth

             Step 2: Search for the device that's within a range

              Step 3: Select the device you want to migrate computation to

              Step 4: Navigate to your process manager and select the process you want to migrate to

           """
      )
  );

  public static final Set<String> DICTIONARY = Set.of(
      "maximize",
      "extend",
      "battery",
      "life",
      "phone",
      "critically",
      "low",
      "conserve",
      "power",
      "save",
      "settings",
      "mode",
      "offload",
      "tasks",
      "drain",
      "consumption",
      "functions",
      "features",
      "apps"
  );

  public static final Map<String, Set<String>> SYNONYM_MAP = new HashMap<>() {{
    put("maximize", Set.of("conserve", "save", "prolong"));
    put("extend", Set.of("lengthen", "prolong"));
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
