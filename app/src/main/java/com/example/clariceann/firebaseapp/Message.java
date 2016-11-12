package com.example.clariceann.firebaseapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by clariceann on 12/11/2016.
 */
@IgnoreExtraProperties
public class Message {

    private String message;

    public Message() {

    }

    public Message(String txt) {
        this.message = txt;
    }

    public String getText() {
        return message;
    }

    public void setText(String text) {
        this.message = text;
    }
}
