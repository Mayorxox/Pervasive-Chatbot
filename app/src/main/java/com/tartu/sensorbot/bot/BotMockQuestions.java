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
          Step 1: Ensure Network Connectivity. Connect your Android device to a network.
          
          Step 2: Install and Open an Offloading App. Download, install, and open a computational offloading app.
          
          Step 3: Securely log in or authenticate your device and the target device/server.
          
          Step 4: Identify and select the computational task to be offloaded.
          
          Step 5: Select the device or server to which the task will be offloaded.
          
          Step 6: Send the task and necessary data to the chosen device/server.
          
          Step 7: Track the progress of the offloaded computational task.
          
          Step 8: Once complete, retrieve and check the results for accuracy.
          
          Step 9: Safely disconnect from the target device/server.
          
          Step 10: Store the retrieved data securely or use it as needed.
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
