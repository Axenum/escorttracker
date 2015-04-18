package com.allits.escorttracker.rest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class LoginUserDto implements Serializable {

    private String userName;
    private String password;

    public LoginUserDto(LoginUserDtoBuilder registerUserDtoBuilder) {
        this.userName = registerUserDtoBuilder.userName;
        this.password = registerUserDtoBuilder.password;
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

    public static class LoginUserDtoBuilder {
        private String userName;
        private String password;

        public LoginUserDtoBuilder() {
        }

        public LoginUserDtoBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public LoginUserDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public LoginUserDto build() {
            return new LoginUserDto(this);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }
}
