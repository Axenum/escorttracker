package com.allits.escorttracker;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang.StringUtils;

import com.allits.escorttracker.activity.BaseActivity;
import com.allits.escorttracker.activity.ForgottenPasswordActivity;
import com.allits.escorttracker.application.SlidingMenuApplication;
import com.allits.escorttracker.common.Constants;
import com.allits.escorttracker.dispatcher.AddUserDispatcher;
import com.allits.escorttracker.dispatcher.IAddUserActivity;
import com.allits.escorttracker.rest.LoginUser;
import com.allits.escorttracker.rest.LoginUserDto;
import com.allits.escorttracker.user.CurrentUser;
import com.allits.escorttracker.user.UserHolder;

public class LoginActivity extends BaseActivity implements IAddUserActivity {

    private EditText etUsername, etPassword;
    private CurrentUser newCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
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

    public void onClickLogin(final View v) {
        if (StringUtils.isEmpty(etUsername.getText().toString())) {
            showHint(getResources().getString(R.string.PleaseEnterYourUsername));
            return;
        }
        if (StringUtils.isEmpty(etPassword.getText().toString())) {
            showHint(getResources().getString(R.string.PleaseEnterYourPassword));
            return;
        }
        if (etPassword.getText().toString().length() < Constants.MINIMUM_PASSWORD_LENGTH) {
            showHint(getResources().getString(R.string.YourPasswordHasToBeAtLeastXCharactersLong, Constants.MINIMUM_PASSWORD_LENGTH));
            return;
        }
        showLoadingProgress();
        newCurrentUser = new CurrentUser("n/a").userName(etUsername.getText().toString()).email(etUsername.getText().toString());
        LoginUserDto.LoginUserDtoBuilder loginUserDtoBuilder = new LoginUserDto.LoginUserDtoBuilder();
        loginUserDtoBuilder.userName(etUsername.getText().toString());
        loginUserDtoBuilder.password(etPassword.getText().toString());
        LoginUserDto loginUserDto = loginUserDtoBuilder.build();
        LoginUser loginUser = new LoginUser();
        loginUser.execute(loginUserDto);
    }

    public void onClickPasswordForgotten(final View v) {
        goToActvity(ForgottenPasswordActivity.class);
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
        Log.e(((Object) this).getClass().getName(), "Cannot login user: " + message);
        hideLoadingProgress();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (message.equalsIgnoreCase("Invalid Credentials!")) {
                    showError(getResources().getString(R.string.CanNotLoginDueToInvalidCredentials));
                } else {
                    showError(getResources().getString(R.string.CanNotLoginUser) + ": " + message);
                }
            }
        });
    }

}
