package com.toquescript.www.norcexproject.task;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.toquescript.www.norcexproject.api.ApiNorcex;
import com.toquescript.www.norcexproject.constant.Constant;
import com.toquescript.www.norcexproject.request.SignInRequest;

import java.io.IOException;

/**
 * Created by BALAREZO on 06/06/2016.
 */
public class LoginTask extends BaseAsyncTask {

    public LoginTask(Context context, ApiNorcex api, SignInRequest signInRequest, String... args) {
        TAG = LoginTask.class.getSimpleName();
        Log.d(TAG, "INTANCE " + TAG);

        mContext = context;
        apiNorcex = api;
        signinrequest = signInRequest;
        accountType = args[0];
        username = args[1];
        intent = new Intent();
    }

    protected void onPreExecute() {
        Log.d(TAG, "On onPreExecute..." + TAG);
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.d(TAG, "On doInBackground..." + TAG);
        try {
            responseCall = apiNorcex.signin(signinrequest);
            response = responseCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "On onPostExecute..." + TAG);
        if (response != null) {
            switch (response.code()) {
                case 200:
                    code200();
                    break;
            }
        } else {
            Log.d(TAG, "On onPostExecute... ERROR!!" + TAG);
        }
    }

    private void code200() {
        Log.d(TAG, "On code200 "  + TAG);

        signInResponse = response.body();
        error = signInResponse.error;
        message = signInResponse.message;

        if (!error) {

            result = signInResponse.result;
            token = result.accessToken;
            refresh_token = result.refreshToken;
            expires_in = result.expiresIn;
            token_type = result.tokenType;
            scope = result.scope;

            try {
                intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
                intent.putExtra(AccountManager.KEY_AUTHTOKEN, token);
                intent.putExtra(Constant.ARG_TOKEN_EXPIRES_IN, expires_in);
                intent.putExtra(Constant.ARG_TOKEN_TYPE, token_type);
                intent.putExtra(Constant.ARG_REFRESH_TOKEN, refresh_token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "On onPostExecute... DATOS ALMACENADOS EN INTENT!! " + TAG);
            loginTaksFinish.taskFinishLogin(intent);
        } else {
            Log.d(TAG, "On onPostExecute... ERROR EN LA PETICION!! " + message + " " + TAG);
        }
    }
}