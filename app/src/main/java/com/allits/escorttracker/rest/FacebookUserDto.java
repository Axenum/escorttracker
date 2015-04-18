package com.allits.escorttracker.rest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

import com.allits.escorttracker.user.Gender;

public class FacebookUserDto implements Serializable {

    private String email;
    private String facebookId;
    private String firstName;
    private String lastName;
    private final Gender gender;

    public FacebookUserDto(Gender gender) {
        this.gender = gender;
    }

    public FacebookUserDto email(String email) {
        this.email = email;
        return this;
    }

    public FacebookUserDto facebookId(String facebookId) {
        this.facebookId = facebookId;
        return this;
    }

    public FacebookUserDto firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public FacebookUserDto lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


