package com.rsassistant.v99.nlp;

import android.content.Context;
import java.util.regex.*;

public class IntentClassifier {
    public IntentClassifier(Context context) {}

    public IntentResult classify(String input) {
        String lower = input.toLowerCase();
        
        if (lower.contains("call")) return new IntentResult("call", extractName(input), input, true);
        if (lower.contains("camera") || lower.contains("photo") || lower.contains("selfie")) 
            return new IntentResult("camera", null, input, true);
        if (lower.contains("time") || lower.contains("samay")) 
            return new IntentResult("time", null, input, true);
        if (lower.contains("date") || lower.contains("aaj")) 
            return new IntentResult("date", null, input, true);
        if (lower.contains("wifi")) return new IntentResult("wifi", getState(lower), input, true);
        if (lower.contains("torch") || lower.contains("flashlight")) 
            return new IntentResult("flashlight", getState(lower), input, true);
        if (lower.contains("volume") || lower.contains("awaz")) 
            return new IntentResult("volume", getDirection(lower), input, true);
        if (lower.contains("open")) return new IntentResult("open", extractAppName(input), input, true);
        if (lower.contains("message") || lower.contains("sms")) 
            return new IntentResult("message", extractName(input), input, true);
        
        return new IntentResult("ai_query", null, input, false);
    }

    private String extractName(String input) {
        String[] words = input.split(" ");
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].equalsIgnoreCase("call") || words[i].equalsIgnoreCase("message"))
                return words[i + 1];
        }
        return null;
    }

    private String extractAppName(String input) {
        String[] parts = input.toLowerCase().split("open");
        return parts.length > 1 ? parts[1].trim() : null;
    }

    private String getState(String input) {
        if (input.contains("on")) return "on";
        if (input.contains("off")) return "off";
        return "toggle";
    }

    private String getDirection(String input) {
        if (input.contains("badhao") || input.contains("up") || input.contains("increase")) return "up";
        if (input.contains("kam") || input.contains("down") || input.contains("decrease")) return "down";
        return "up";
    }

    public static class IntentResult {
        private String action, target, originalText;
        private boolean actionable;
        
        public IntentResult(String action, String target, String originalText, boolean actionable) {
            this.action = action;
            this.target = target;
            this.originalText = originalText;
            this.actionable = actionable;
        }
        
        public String getAction() { return action; }
        public String getTarget() { return target; }
        public String getOriginalText() { return originalText; }
        public boolean isActionable() { return actionable; }
    }
}
