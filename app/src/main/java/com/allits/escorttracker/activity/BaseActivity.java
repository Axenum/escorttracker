package com.allits.escorttracker.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.allits.escorttracker.R;

public class BaseActivity extends Activity {

    protected ProgressDialog progressDialog;

    protected final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        progressDialog = new ProgressDialog(this);
    }

    protected void showLoadingProgress() {
        if (progressDialog != null) {
            progressDialog.setTitle(R.string.Loading);
            progressDialog.setMessage(getString(R.string.WaitWhileLoading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    protected synchronized void hideLoadingProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void showLoadingProgressWithMessage(String message) {
        if (progressDialog != null) {
            progressDialog.setTitle(R.string.Loading);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    protected void showHint(String hint) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage(hint)
                .setCancelable(true)
                .setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'Yes' Button
                    }
                });
        AlertDialog closeDialog = alt_bld.create();
        // Title for AlertDialog
        closeDialog.setTitle(R.string.Hint);
        closeDialog.show();
    }


    protected Context getContext() {
        return this;
    }

    protected void showError(String errorText) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage(errorText)
                .setCancelable(true)
                .setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'Yes' Button
                    }
                });
        AlertDialog closeDialog = alt_bld.create();
        // Title for AlertDialog
        closeDialog.setTitle(getString(R.string.Error));
        closeDialog.show();
    }

    protected void showMessage(String messageText) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage(messageText)
                .setCancelable(true)
                .setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'Yes' Button
                    }
                });
        AlertDialog closeDialog = alt_bld.create();
        // Title for AlertDialog
        closeDialog.setTitle(getString(R.string.Hint));
        closeDialog.show();
    }

    protected void goToActvityAndClearStack(Class<?> cls) {
        Intent i = new Intent(this, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    protected void goToActvity(Class<?> cls) {
        Intent i = new Intent(this, cls);
        startActivity(i);
    }
}
