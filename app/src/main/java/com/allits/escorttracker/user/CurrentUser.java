package com.allits.escorttracker.user;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class CurrentUser implements Serializable {

    private static final long serialVersionUID = -1458066249299204690L;

    private String currentSessionId;

    private String userName;

    private String facebookId;

    private String email;

    private String firstName;

    private String lastName;

    private Gender gender = Gender.UNKNOWN;

    private String birthday;

    public CurrentUser(String currentSessionId) {
        this.currentSessionId = currentSessionId;
    }

    public CurrentUser() {
    }

    public String getCurrentSessionId() {
        return currentSessionId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isLoggedIn() {
        return StringUtils.isNotEmpty(this.currentSessionId);
    }

    public CurrentUser currentSessionId(String currentSessionId) {
        this.currentSessionId = currentSessionId;
        return this;
    }

    public CurrentUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CurrentUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public CurrentUser facebookId(String facebookId) {
        this.facebookId = facebookId;
        return this;
    }

    public CurrentUser email(String email) {
        this.email = email;
        return this;
    }

    public CurrentUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CurrentUser gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public CurrentUser birthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
