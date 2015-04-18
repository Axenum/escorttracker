package com.allits.escorttracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang.StringUtils;

import com.allits.escorttracker.activity.BaseActivity;
import com.allits.escorttracker.application.SlidingMenuApplication;
import com.allits.escorttracker.common.Constants;
import com.allits.escorttracker.common.EmailValidator;
import com.allits.escorttracker.dispatcher.AddUserDispatcher;
import com.allits.escorttracker.dispatcher.IAddUserActivity;
import com.allits.escorttracker.rest.RegisterUser;
import com.allits.escorttracker.rest.RegisterUserDto;
import com.allits.escorttracker.user.CurrentUser;
import com.allits.escorttracker.user.UserHolder;

public class RegisterActivity extends BaseActivity implements IAddUserActivity {

    private EditText etUsername, etPassword, etEmail, etPasswordRepeation;

    private CurrentUser newCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordRepeation = (EditText) findViewById(R.id.etPasswordRepeation);
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

    public void onClickRegister(final View v) {
        if (StringUtils.isEmpty(etEmail.getText().toString())) {
            showHint(getResources().getString(R.string.PleaseEnterYourEmailAddress));
            return;
        }
        if (!EmailValidator.isValid(etEmail.getText().toString())) {
            showHint(getResources().getString(R.string.PleaseEnterAValidEmailAddress));
            return;
        }
        if (StringUtils.isEmpty(etUsername.getText().toString())) {
            showHint(getResources().getString(R.string.PleaseEnterYourUsername));
            return;
        }
        if (StringUtils.isEmpty(etPassword.getText().toString())) {
            showHint(getResources().getString(R.string.PleaseEnterYourPassword));
            return;
        }
        if (etPassword.getText().toString().length() < Constants.MINIMUM_PASSWORD_LENGTH) {
            showHint(getResources().getString(R.string.YourNewPasswordHasToBeAtLeastXCharactersLong, Constants.MINIMUM_PASSWORD_LENGTH));
            return;
        }
        if (!etPassword.getText().toString().equals(etPasswordRepeation.getText().toString())) {
            showHint(getResources().getString(R.string.ThePasswordRepeatDoesNotMatch));
            return;
        }
        showLoadingProgress();
        newCurrentUser = new CurrentUser("n/a").userName(etUsername.getText().toString()).email(etUsername.getText().toString());
        RegisterUserDto.RegisterUserDtoBuilder registerUserDtoBuilder = new RegisterUserDto.RegisterUserDtoBuilder();
        registerUserDtoBuilder.email(etEmail.getText().toString());
        registerUserDtoBuilder.userName(etUsername.getText().toString());
        registerUserDtoBuilder.password(etPassword.getText().toString());
        RegisterUserDto registerUserDto = registerUserDtoBuilder.build();
        RegisterUser registerUser = new RegisterUser();
        registerUser.execute(registerUserDto);
    }

    @Override
    public void userAdded() {
        UserHolder.getInstance().setCurrentUser(newCurrentUser);
        ((SlidingMenuApplication) getApplication()).persistUser();
        hideLoadingProgress();
        goToActvityAndClearStack(MainActivity.class);
    }

    @Override
    public void userAddFailed(final String message) {
        Log.e(((Object) this).getClass().getName(), "Cannot register user: " + message);
        hideLoadingProgress();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (message.equalsIgnoreCase("I'm sorry, this username is already in use")) {
                    showError(getResources().getString(R.string.TheUsernameIsAlreadyInUse));
                } else {
                    showError(getResources().getString(R.string.CanNotRegisterUser) + ": " + message);
                }
            }
        });
    }

}
