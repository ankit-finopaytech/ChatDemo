package com.chatdemo.model;

/**
 * Created by ankit_aggarwal on 08-06-2017.
 */

public class ChatHistoryBean {

    private int id;
    private String message;
    private String date;
    private String messageSource;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(String messageSource) {
        this.messageSource = messageSource;
    }
}
