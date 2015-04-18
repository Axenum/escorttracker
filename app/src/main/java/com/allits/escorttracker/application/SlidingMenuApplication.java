package com.allits.escorttracker.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.lang.StringUtils;

import com.allits.escorttracker.common.Constants;
import com.allits.escorttracker.user.CurrentUser;
import com.allits.escorttracker.user.UserHolder;

public class SlidingMenuApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setLastUser();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.v(((Object) this).getClass().getName(), "onTerminate called");
        persistUser();
    }

    public void persistUser() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        if (UserHolder.getInstance().isUserLoggedIn()) {
            Gson gson = new Gson();
            edit.putString(Constants.USER_PREFS, gson.toJson(UserHolder.getInstance().getCurrentUser()));
        } else {
            edit.putString(Constants.USER_PREFS, null);
        }
        edit.commit();
    }

    private void setLastUser() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String userAsGson = prefs.getString(Constants.USER_PREFS, null);
        if (StringUtils.isNotEmpty(userAsGson)) {
            Gson gson = new Gson();
            CurrentUser currentUser = gson.fromJson(userAsGson, CurrentUser.class);
            if ((currentUser != null) && (currentUser.getCurrentSessionId() != null)) {
                UserHolder.getInstance().setCurrentUser(currentUser);
            } else {
                Log.d(((Object) this).getClass().getName(), "Persisted user can not be read");
            }
        } else {
            Log.d(((Object) this).getClass().getName(), "No persisted user found");
        }
    }

}
