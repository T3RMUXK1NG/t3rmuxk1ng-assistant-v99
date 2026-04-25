package com.rsassistant.v99.nlp;

import android.content.Context;
import java.util.*;

public class HinglishProcessor {
    private static final Map<String, String> HINGLISH_TO_ENGLISH = new HashMap<>();
    
    static {
        HINGLISH_TO_ENGLISH.put("karo", "do");
        HINGLISH_TO_ENGLISH.put("kar do", "do");
        HINGLISH_TO_ENGLISH.put("bhejo", "send");
        HINGLISH_TO_ENGLISH.put("kholo", "open");
        HINGLISH_TO_ENGLISH.put("band karo", "close");
        HINGLISH_TO_ENGLISH.put("dikhao", "show");
        HINGLISH_TO_ENGLISH.put("batao", "tell");
        HINGLISH_TO_ENGLISH.put("on kar do", "turn on");
        HINGLISH_TO_ENGLISH.put("off kar do", "turn off");
        HINGLISH_TO_ENGLISH.put("badhao", "increase");
        HINGLISH_TO_ENGLISH.put("kam karo", "decrease");
    }

    public HinglishProcessor(Context context) {}

    public String process(String input) {
        if (input == null || input.isEmpty()) return input;
        String result = input.toLowerCase();
        for (Map.Entry<String, String> e : HINGLISH_TO_ENGLISH.entrySet()) {
            result = result.replace(e.getKey(), e.getValue());
        }
        return result;
    }

    public boolean isCallIntent(String input) { return input.toLowerCase().contains("call"); }
    public boolean isCameraIntent(String input) { return input.toLowerCase().contains("camera") || input.toLowerCase().contains("photo"); }
    public boolean isAskingForTime(String input) { return input.toLowerCase().contains("time") || input.toLowerCase().contains("samay"); }
    public boolean isAskingForDate(String input) { return input.toLowerCase().contains("date") || input.toLowerCase().contains("aaj"); }
}
