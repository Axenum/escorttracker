package com.allits.escorttracker.dispatcher;

public interface IAddUserActivity {

    void userAdded();

    void userAddFailed(final String message);
}
