package com.allits.escorttracker.dispatcher;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.HashSet;
import java.util.Set;

public class AddUserDispatcher {

    private Set<IAddUserActivity> addUserActivities = new HashSet<>();

    private static AddUserDispatcher instance = new AddUserDispatcher();

    private AddUserDispatcher() {
    }

    public static AddUserDispatcher getInstance() {
        return instance;
    }

    public void addObserver(IAddUserActivity obsrNewObserver) {
        if (!addUserActivities.contains(obsrNewObserver)) {
            addUserActivities.add(obsrNewObserver);
        }
    }

    public void removeObserver(IAddUserActivity obsrToRemove) {
        addUserActivities.remove(obsrToRemove);
    }

    public void userAdded() {
        for (IAddUserActivity activity : addUserActivities) {
            activity.userAdded();
        }
    }

    public void userAddFailed(String message) {
        for (IAddUserActivity activity : addUserActivities) {
            activity.userAddFailed(message);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
