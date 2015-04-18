package com.allits.escorttracker.dispatcher;

public interface IForgotPasswordActivity {

    void newPasswordRetrieved();

    void passwordRetrievementFailed(final String message);
}
