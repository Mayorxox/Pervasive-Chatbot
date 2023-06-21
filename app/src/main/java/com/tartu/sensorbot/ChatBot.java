package com.tartu.sensorbot;

import java.util.ArrayList;
import java.util.List;

public class ChatBot {
    private final List<String> suggestions;

    public ChatBot() {
        suggestions = new ArrayList<>();
        suggestions.add("How to save battery level?");
        suggestions.add("How to view network usage?");
        suggestions.add("Which sensors uses more power?");
    }

    public String getResponse(String userMessage) {
        if (userMessage.toLowerCase().contains("sensor")) {
            return "Here's some information about sensors...";
        } else if (userMessage.equalsIgnoreCase("How to save battery level?")) {
            return "Here's some information about battery level...";
        } else if (userMessage.equalsIgnoreCase("How to view network usage?")) {
            return "Here's some information about network usage...";
        } else if (userMessage.equalsIgnoreCase("Which sensors uses more power?")) {
            return "Here's some information about sensors based on power usage...";
        } else {
            return "I'm sorry, I didn't understand that. Here are some suggestions:";
        }
    }

    public List<String> getSuggestions() {
        return suggestions;
    }
}

