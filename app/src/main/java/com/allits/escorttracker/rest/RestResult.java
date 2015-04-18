package com.allits.escorttracker.rest;

import org.apache.commons.lang.builder.ToStringBuilder;

public class RestResult {

    private boolean success;
    private String message;

    public RestResult(boolean success) {
        this.success = success;
    }

    public RestResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
