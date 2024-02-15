package com.limanov.t1.model;

public class MessageDto {
    private String contentMessage;

    public MessageDto() {
    }

    public MessageDto(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }
}
