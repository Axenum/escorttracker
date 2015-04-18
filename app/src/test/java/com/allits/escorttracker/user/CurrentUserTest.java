package com.allits.escorttracker.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentUserTest {

    @Test
    public void thatUserIsNotLoggedInIfThereIsNoSessionId () throws Exception {

        //when
        CurrentUser currentUser = new CurrentUser(null);

        //then
        assertFalse(currentUser.isLoggedIn());
    }

    @Test
    public void thatUserIsLoggedInIfThereIsASessionId() throws Exception {

        //when
        CurrentUser currentUser = new CurrentUser("123");

        //then
        assertTrue(currentUser.isLoggedIn());
    }
}
