package com.allits.escorttracker.dispatcher;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.HashSet;
import java.util.Set;

public class ForgottenPasswordDispatcher {

    private Set<IForgotPasswordActivity> forgotPasswordActivities = new HashSet<>();

    private static ForgottenPasswordDispatcher instance = new ForgottenPasswordDispatcher();

    private ForgottenPasswordDispatcher() {
    }

    public static ForgottenPasswordDispatcher getInstance() {
        return instance;
    }

    public void addObserver(IForgotPasswordActivity obsrNewObserver) {
        if (!forgotPasswordActivities.contains(obsrNewObserver)) {
            forgotPasswordActivities.add(obsrNewObserver);
        }
    }

    public void removeObserver(IForgotPasswordActivity obsrToRemove) {
        forgotPasswordActivities.remove(obsrToRemove);
    }

    public void newPasswordRetrieved() {
        for (IForgotPasswordActivity activity : forgotPasswordActivities) {
            activity.newPasswordRetrieved();
        }
    }

    public void passwordRetrievementFailed(String message) {
        for (IForgotPasswordActivity activity : forgotPasswordActivities) {
            activity.passwordRetrievementFailed(message);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
