package com.tartu.sensorbot.message;

public class MessageStep {

    private final int timeInMinutes;
    private final String instruction;

    public MessageStep(int timeInMinutes, String instruction) {
        this.timeInMinutes = timeInMinutes;
        this.instruction = instruction;
    }

    public String getTime() {
        return timeInMinutes + " min";
    }

    public String getInstruction() {
        return instruction;
    }

    @Override
    public String toString() {
        return "MessageStep{" +
                "timeInMinutes=" + timeInMinutes +
                ", instruction='" + instruction + '\'' +
                '}';
    }
}
