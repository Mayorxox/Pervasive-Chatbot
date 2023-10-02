package com.tartu.sensorbot.bot;

import com.tartu.sensorbot.message.MessageStep;
import java.util.List;

public record BotAnswer(List<MessageStep> steps, String message) {
}
