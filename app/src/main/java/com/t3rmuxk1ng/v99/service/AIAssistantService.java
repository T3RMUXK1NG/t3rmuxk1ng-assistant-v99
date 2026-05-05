package com.t3rmuxk1ng.v99.service;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AIAssistantService {
    private static AIAssistantService instance;
    private OkHttpClient client;
    private Gson gson;

    private AIAssistantService(Context context) {
        client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
        gson = new Gson();
    }

    public static AIAssistantService getInstance(Context context) {
        if (instance == null) instance = new AIAssistantService(context);
        return instance;
    }

    public static void getAIResponse(String query, ResponseCallback callback) {
        new Thread(() -> {
            try {
                String response = generateResponse(query);
                callback.onResponse(response);
            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

    private static String generateResponse(String query) {
        String lower = query.toLowerCase();
        if (lower.contains("hello") || lower.contains("hi")) 
            return "Hello! How can I help you today?";
        if (lower.contains("how are you")) 
            return "I'm doing great! Ready to assist you.";
        if (lower.contains("thank")) 
            return "You're welcome! Anything else I can help with?";
        return "I understand. Let me help you with that. " + query;
    }

    public interface ResponseCallback {
        void onResponse(String response);
        void onError(String error);
    }
}
