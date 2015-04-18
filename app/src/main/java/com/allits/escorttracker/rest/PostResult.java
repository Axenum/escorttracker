package com.allits.escorttracker.rest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public final class PostResult {

    @SuppressWarnings("UnusedDeclaration")
    private Integer success;

    @SuppressWarnings("UnusedDeclaration")
    private String message;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success != null && success == 1;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}