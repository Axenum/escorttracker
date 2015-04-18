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

public class FacebookUser extends AsyncTask<FacebookUserDto, Void, RestResult> {

    private FacebookUserDto facebookUserDto;

    private static final String URL_STRING = Constants.URL_PREFIX + "addfb.php";

    @Override
    protected RestResult doInBackground(FacebookUserDto... params) {
        if (params == null) {
            Log.e(((Object) this).getClass().getName(), "Can not handle no provided params");
            return new RestResult(false);
        }
        if (params.length != 1) {
            Log.e(((Object) this).getClass().getName(), "Can handle only one FacebookUserDto, but received " + params.length + " dtos");
            return new RestResult(false);
        }
        this.facebookUserDto = params[0];
        try {
            RestResult restResult = postNewFacebookUserRequest();
            if (restResult.isSuccess()) {
                AddUserDispatcher.getInstance().userAdded();
            } else {
                AddUserDispatcher.getInstance().userAddFailed(restResult.getMessage());
            }
            return restResult;
        } catch (Exception e) {
            Log.e(getClass().getName(), "Exception adding facebook user " + facebookUserDto, e);
            AddUserDispatcher.getInstance().userAddFailed(e.getMessage());
        }
        return new RestResult(false);
    }

    private RestResult postNewFacebookUserRequest() throws IOException {
        Connection connection = Jsoup.connect(URL_STRING).timeout(Constants.REST_TIMEOUT_IN_MILLIS);
        HeadersHelper.addHeadersToConnection(connection);
        connection.method(Connection.Method.POST);
        connection.data("firstName", facebookUserDto.getFirstName());
        connection.data("lastName", facebookUserDto.getLastName());
        connection.data("facebookId", facebookUserDto.getFacebookId());
        connection.data("gender", facebookUserDto.getGender().getValue());
        connection.data("email", facebookUserDto.getEmail());
        connection.post();
        if (connection.response().statusCode() == HttpStatus.SC_OK) {
            String resultBody = connection.response().body();
            if (resultBody != null) {
                Gson gson = new Gson();
                PostResult postResult = gson.fromJson(resultBody, PostResult.class);
                return new RestResult(postResult.isSuccess());
            }
        }
        return new RestResult(false);
    }

    @Override
    protected void onPostExecute(RestResult restResult) {
        super.onPostExecute(restResult);
    }

}

