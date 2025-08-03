package com.example.cocapi.exceptions;

public class ErrorDetails {
    private String message;
    private String details;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
