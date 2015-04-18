package com.allits.escorttracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;

import java.util.Arrays;

import com.allits.escorttracker.activity.BaseActivity;
import com.allits.escorttracker.application.SlidingMenuApplication;
import com.allits.escorttracker.dispatcher.AddUserDispatcher;
import com.allits.escorttracker.dispatcher.IAddUserActivity;
import com.allits.escorttracker.fragments.ReadComments;
import com.allits.escorttracker.function.SaveSharedPreference;
import com.allits.escorttracker.rest.FacebookUser;
import com.allits.escorttracker.rest.FacebookUserDto;
import com.allits.escorttracker.user.CurrentUser;
import com.allits.escorttracker.user.Gender;
import com.allits.escorttracker.user.UserHolder;

public class StartActivity extends BaseActivity implements OnClickListener, IAddUserActivity {

    private SaveSharedPreference ssp;

    private CurrentUser newCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        setTitle(getResources().getString(R.string.StartTitle));

        ssp = null;
        if (ssp.getUserName(getApplicationContext()).length() != 0) {
            Intent i = new Intent(getApplicationContext(), ReadComments.class);
            finish();
            startActivity(i);
        }

        Button mSubmit = (Button) findViewById(R.id.btnLog);
        Button mRegister = (Button) findViewById(R.id.btnReg);

        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);

        LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
        authButton.setOnErrorListener(new OnErrorListener() {

            @Override
            public void onError(FacebookException error) {
                Log.e(this.getClass().getName(), "Error " + error.getMessage());
            }
        });
        authButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        authButton.setSessionStatusCallback(new Session.StatusCallback() {

            @Override
            public void call(final Session session, SessionState state, Exception exception) {
                if (exception != null) {
                    showError(getResources().getString(R.string.FacebookLoginWasNotSuccessful));
                    Log.e(((Object) this).getClass().getName(), "Login to facebook was not successful", exception);
                    return;
                }
                if (session.isOpened()) {
                    Request.newMeRequest(session,
                            new Request.GraphUserCallback() {
                                @Override
                                public void onCompleted(GraphUser fbGraphUser, Response response) {
                                    if (fbGraphUser != null) {
                                        showLoadingProgress();
                                        newCurrentUser = createCurrentUser(fbGraphUser);
                                        //Collect data and send it to the server
                                        FacebookUserDto facebookUserDto = new FacebookUserDto(newCurrentUser.getGender())
                                                .facebookId(newCurrentUser.getFacebookId())
                                                .email(newCurrentUser.getEmail())
                                                .firstName(newCurrentUser.getFirstName())
                                                .lastName(newCurrentUser.getLastName());
                                        FacebookUser facebookUser = new FacebookUser();
                                        facebookUser.execute(facebookUserDto);
                                    } else {
                                        showError(getString(R.string.FacebookLoginWasNotSuccessful));
                                    }
                                }

                                private CurrentUser createCurrentUser(GraphUser fbGraphUser) {
                                    CurrentUser currentUser = new CurrentUser(session.getAccessToken())
                                            .facebookId(fbGraphUser.getId())
                                            .userName(fbGraphUser.getUsername())
                                            .firstName(fbGraphUser.getFirstName())
                                            .lastName(fbGraphUser.getLastName())
                                            .birthday(fbGraphUser.getBirthday());
                                    try {
                                        currentUser.email((String) fbGraphUser.getInnerJSONObject().get("email"));
                                    } catch (Exception e) {
                                        Log.v(((Object) this).getClass().getName(), "Cannot read email from facebook user", e);
                                    }
                                    try {
                                        currentUser.gender(Gender.valueOf(((String) fbGraphUser.getInnerJSONObject().get("gender")).toUpperCase()));
                                    } catch (Exception e) {
                                        Log.v(((Object) this).getClass().getName(), "Cannot read gender from facebook user - let gender to be unknown", e);
                                    }
                                    return currentUser;
                                }
                            }).executeAsync();
                } else if (session.isClosed()) {
                    UserHolder.getInstance().setCurrentUser(null);
                    ((SlidingMenuApplication) getApplication()).persistUser();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(((Object) this).getClass().getName(), "OnResume");
        AddUserDispatcher.getInstance().addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(((Object) this).getClass().getName(), "OnPause");
        AddUserDispatcher.getInstance().removeObserver(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLog:
                goToActvity(LoginActivity.class);
                break;
            case R.id.btnReg:
                goToActvity(RegisterActivity.class);
                break;
            default:
                Log.w(getClass().getName(), "Unknown button pressed");
        }
    }

    @Override
    public void userAdded() {
        UserHolder.getInstance().setCurrentUser(newCurrentUser);
        ((SlidingMenuApplication) getApplication()).persistUser();
        hideLoadingProgress();
        goToActvityAndClearStack(MainActivity.class);
    }

    @Override
    public void userAddFailed(String message) {
        Log.e(((Object) this).getClass().getName(), "Cannot add facebookuser: " + message);
        hideLoadingProgress();
        Session session = Session.getActiveSession();
        if (session != null) {
            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
            }
        } else {
            session = new Session(this);
            Session.setActiveSession(session);
            session.closeAndClearTokenInformation();
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showError(getResources().getString(R.string.FacebookLoginWasNotSuccessful));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApplication().onTerminate();
    }

}
