package com.roitraining.demos.web_flux_basics.entity;

import java.time.LocalTime;
import java.util.UUID;

public class DemoEvent {
    private LocalTime createdAt;
    private UUID messageId;
    private String message;

    public DemoEvent() {
        this.createdAt = LocalTime.now();
        this.messageId = UUID.randomUUID();
    }

    public DemoEvent(String message) {
        this();
        this.message = message;
    }

    public LocalTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
