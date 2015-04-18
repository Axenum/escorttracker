package com.allits.escorttracker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang.StringUtils;

import com.allits.escorttracker.R;
import com.allits.escorttracker.common.EmailValidator;
import com.allits.escorttracker.dispatcher.ForgottenPasswordDispatcher;
import com.allits.escorttracker.dispatcher.IForgotPasswordActivity;
import com.allits.escorttracker.rest.ForgottenPassword;
import com.allits.escorttracker.rest.ForgottenPasswordDto;

public class ForgottenPasswordActivity extends BaseActivity implements IForgotPasswordActivity {


    private EditText etUsername;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgottenpassword);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        setTitle(R.string.ForgotPassword);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(((Object) this).getClass().getName(), "OnResume");
        ForgottenPasswordDispatcher.getInstance().addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(((Object) this).getClass().getName(), "OnPause");
        ForgottenPasswordDispatcher.getInstance().removeObserver(this);
    }

    public void onClickResendPassword(final View view) {
        if ((StringUtils.isEmpty(etUsername.getText().toString())) && (StringUtils.isEmpty(etEmail.getText().toString()))) {
            showHint(getResources().getString(R.string.PleaseEnterYourUsernameOrEmailAddress));
            return;
        }
        if (StringUtils.isNotEmpty(etEmail.getText().toString())) {
            if (!EmailValidator.isValid(etEmail.getText().toString())) {
                showHint(getResources().getString(R.string.PleaseEnterAValidEmailAddress));
                return;
            }
        }
        showLoadingProgress();
        ForgottenPasswordDto forgottenPasswordDto = new ForgottenPasswordDto()
                .eMail(etEmail.getText().toString())
                .userName(etUsername.getText().toString());
        ForgottenPassword forgottenPassword = new ForgottenPassword();
        forgottenPassword.execute(forgottenPasswordDto);
    }

    @Override
    public void newPasswordRetrieved() {
        hideLoadingProgress();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.Success));
        builder.setMessage(getResources().getString(R.string.ANewPasswordHasBeenSentToYouByEmail));
        builder.setPositiveButton(getResources().getString(R.string.Okay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void passwordRetrievementFailed(final String message) {
        Log.e(((Object) this).getClass().getName(), "Cannot retrieve new password: " + message);
        hideLoadingProgress();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showError(getResources().getString(R.string.CanNotRetrieveNewPassword) + ": " + message);
            }
        });
    }
}
