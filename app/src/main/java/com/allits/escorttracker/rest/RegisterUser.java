package com.allits.escorttracker.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

import com.allits.escorttracker.common.Constants;
import com.allits.escorttracker.dispatcher.AddUserDispatcher;

public class RegisterUser extends AsyncTask<RegisterUserDto, Void, RestResult> {

    private RegisterUserDto registerUserDto;

    private static final String URL_STRING = Constants.URL_PREFIX + "register.php";

    @Override
    protected RestResult doInBackground(RegisterUserDto... params) {
        if (params == null) {
            Log.e(((Object) this).getClass().getName(), "Can not handle no provided params");
            return new RestResult(false);
        }
        if (params.length != 1) {
            Log.e(((Object) this).getClass().getName(), "Can handle only one RegisterUserDto, but received " + params.length + " dtos");
            return new RestResult(false);
        }
        this.registerUserDto = params[0];
        try {
            RestResult restResult = postNewUserRequest();
            if (restResult.isSuccess()) {
                AddUserDispatcher.getInstance().userAdded();
            } else {
                AddUserDispatcher.getInstance().userAddFailed(restResult.getMessage());
            }
            return restResult;
        } catch (Exception e) {
            Log.e(getClass().getName(), "Exception registering user " + registerUserDto, e);
            AddUserDispatcher.getInstance().userAddFailed(e.getMessage());
        }
        return new RestResult(false);
    }

    private RestResult postNewUserRequest() throws IOException {
        Connection connection = Jsoup.connect(URL_STRING).timeout(Constants.REST_TIMEOUT_IN_MILLIS);
        HeadersHelper.addHeadersToConnection(connection);
        connection.method(Connection.Method.POST);
        connection.data("username", registerUserDto.getUserName());
        connection.data("password", registerUserDto.getPassword());
        connection.data("email", registerUserDto.geteMail());
        connection.post();
        if (connection.response().statusCode() == HttpStatus.SC_OK) {
            String resultBody = connection.response().body();
            if (resultBody != null) {
                Gson gson = new Gson();
                PostResult postResult = gson.fromJson(resultBody.substring(0, resultBody.indexOf("}") + 1), PostResult.class);
                return new RestResult(postResult.isSuccess(), postResult.getMessage());
            }
        }
        return new RestResult(false);
    }
}
