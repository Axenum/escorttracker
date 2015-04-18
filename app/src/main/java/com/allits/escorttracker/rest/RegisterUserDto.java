package com.allits.escorttracker.rest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class RegisterUserDto implements Serializable {

    private String email;
    private String userName;
    private String password;

    public RegisterUserDto(RegisterUserDtoBuilder registerUserDtoBuilder) {
        this.email = registerUserDtoBuilder.email;
        this.userName = registerUserDtoBuilder.userName;
        this.password = registerUserDtoBuilder.password;
    }

    public String geteMail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class RegisterUserDtoBuilder {
        private String email;
        private String userName;
        private String password;

        public RegisterUserDtoBuilder() {
        }

        public RegisterUserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegisterUserDtoBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public RegisterUserDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterUserDto build() {
            return new RegisterUserDto(this);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }
}
