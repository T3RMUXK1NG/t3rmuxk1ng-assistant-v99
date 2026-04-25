package com.rsassistant.v99.model;

public class ConversationMessage {
    private String text;
    private boolean isUser;
    private long timestamp;

    public ConversationMessage(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
        this.timestamp = System.currentTimeMillis();
    }

    public String getText() { return text; }
    public boolean isUser() { return isUser; }
    public long getTimestamp() { return timestamp; }
}
