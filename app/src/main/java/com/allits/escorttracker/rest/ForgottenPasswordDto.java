package com.allits.escorttracker.rest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class ForgottenPasswordDto implements Serializable {

    private String userName;
    private String eMail;

    public ForgottenPasswordDto() {
    }

    public ForgottenPasswordDto(String userName, String eMail) {
        this.userName = userName;
        this.eMail = eMail;
    }

    public ForgottenPasswordDto userName(String userName) {
        this.userName = userName;
        return this;
    }

    public ForgottenPasswordDto eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public String geteMail() {
        return eMail;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
