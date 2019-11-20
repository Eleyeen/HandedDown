package com.example.handeddown.Models;

import com.google.gson.annotations.Expose;

public class RegistrationResponseModel {

    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
