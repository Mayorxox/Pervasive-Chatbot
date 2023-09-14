package com.tartu.sensorbot.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BotResponseGenerator {

    private final String condition;

    public BotResponseGenerator(String condition) {
        this.condition = condition;
    }

    public List<Message> generateResponse(String userQuery) {
        List<Message> responses = new ArrayList<>();
        responses.add(new Message("Sure! Here are some suggestions on how you can do that", true));
        responses.add(generateStepsAndTimes(userQuery));
        return responses;
    }

    private Message generateStepsAndTimes(String userQuery) {
        if (Objects.equals(condition, ChatbotCondition.pervasive)) {
            return generatePCStepsAndTimes(userQuery);
        }
        return generateRCStepsAndTimes(userQuery);
    }

    // pervasive chatbot
    private Message generatePCStepsAndTimes(String userQuery) {
        String[] steps;
        String[] times;

        if (userQuery.toLowerCase().contains("how to save energy")) {
            steps = new String[] {
                    "Close all the apps",
                    "Activate saving mode",
                    "Migrate computation to your friends or surrounding devices?"
            };
            times = new String[] {"2 min", "2 min", "6 min"};
        } else {
            steps = new String[] {"Perform this action"};
            times = new String[] {"3 min"};
        }

        return new Message(steps, times);
    }

    // reference chatbot
    private Message generateRCStepsAndTimes(String userQuery) {
        String[] steps;
        String[] times;

        if (userQuery.toLowerCase().contains("how to save energy")) {
            steps = new String[] {
                    "Close all apps",
                    "Activate battery saving mode",
                    "Migrate"
            };
            times = new String[] {"2 min", "2 min", "6 min"};
        } else {
            steps = new String[] {"Perform this action"};
            times = new String[] {"3 min"};
        }

        return new Message(steps, times);
    }

}
