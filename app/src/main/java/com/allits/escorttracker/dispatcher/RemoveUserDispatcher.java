package com.allits.escorttracker.dispatcher;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.HashSet;
import java.util.Set;

public class RemoveUserDispatcher {

    private Set<IRemoveUserActivity> removeUserActivities = new HashSet<>();

    private static RemoveUserDispatcher instance = new RemoveUserDispatcher();

    private RemoveUserDispatcher() {
    }

    public static RemoveUserDispatcher getInstance() {
        return instance;
    }

    public void addObserver(IRemoveUserActivity obsrNewObserver) {
        if (!removeUserActivities.contains(obsrNewObserver)) {
            removeUserActivities.add(obsrNewObserver);
        }
    }

    public void removeObserver(IRemoveUserActivity obsrToRemove) {
        removeUserActivities.remove(obsrToRemove);
    }

    public void userRemoved() {
        for (IRemoveUserActivity activity : removeUserActivities) {
            activity.userRemoved();
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
