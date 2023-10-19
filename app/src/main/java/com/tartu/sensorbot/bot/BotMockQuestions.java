package com.tartu.sensorbot.bot;

import com.tartu.sensorbot.message.MessageStep;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BotMockQuestions {

  public static final BotAnswer COMMON_ANSWER = new BotAnswer(
      List.of(
          new MessageStep(2, "Close all the apps"),
          new MessageStep(2, "Activate saving mode"),
          new MessageStep(6, "Migrate computation to your friends or surrounding devices?")
      ),
"""
      Step 1. Setup: Install and open a computational offloading app, ensuring network connectivity.
      
      Step 2. Task Selection: Choose the computational task and target device/server.
      
      Step 3. Execution: Send the task and data to the destination, monitor progress, and retrieve results.
      
      Step 4. Finalize: Safely disconnect, store, or utilize the obtained data as required.
      """
  );

  public static final Map<String, BotAnswer> MOCK_QUESTIONS = Map.of(
      "How can I conserve or save energy on my phone?", COMMON_ANSWER,
      "How can I maximize my phone's battery duration?", COMMON_ANSWER,
      "How can I use computational offloading to extend battery life when it's low?", COMMON_ANSWER
  );

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
