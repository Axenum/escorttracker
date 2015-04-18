package com.allits.escorttracker.rest;

import android.os.AsyncTask;

import org.junit.Test;

import java.lang.Void;

import com.allits.escorttracker.user.Gender;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class FacebookUserTest {

    @Test
    public void thatFacebookuserIsNotSentIfThereIsNoFacebookUser() throws Exception {

        //given
        FacebookUser facebookUser = new FacebookUser();

        //when
        AsyncTask<FacebookUserDto, Void, RestResult> execute = facebookUser.execute();

        //then
        assertFalse(execute.get().isSuccess());
    }

    @Test
    public void thatFacebookuserIsNotSentIfThereIsMoreThanOneFacebookUser() throws Exception {

        //given
        FacebookUser facebookUser = new FacebookUser();

        //when
        AsyncTask<FacebookUserDto, Void, RestResult> execute = facebookUser.execute(getFacebookUserDto(), getFacebookUserDto());

        //then
        assertFalse(execute.get().isSuccess());
    }

    @Test
    public void thatFacebookuserIsSent() throws Exception {

        //given
        FacebookUser facebookUser = new FacebookUser();

        //when
        AsyncTask<FacebookUserDto, Void, RestResult> execute = facebookUser.execute(getFacebookUserDto());

        //then
        assertTrue(execute.get().isSuccess());
    }

    private FacebookUserDto getFacebookUserDto() {
        return new FacebookUserDto(Gender.MALE).firstName("Klaus").lastName("Meyer")
                .email("klaus@meyer.de").facebookId("12");
    }
}
