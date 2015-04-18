package com.allits.escorttracker.user;

/**
 * Singleton for holding the current logged in user
 */
public class UserHolder {

    private static UserHolder instance = new UserHolder();

    private CurrentUser currentUser;

    private UserHolder() {
    }

    public static UserHolder getInstance() {
        return instance;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public void removeCurrentUser() {
        this.currentUser = null;
    }

    public boolean isUserLoggedIn() {
        return (currentUser != null) && (currentUser.isLoggedIn());
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }
}
