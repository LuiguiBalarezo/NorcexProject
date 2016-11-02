package com.toquescript.www.norcexproject.task;

import android.content.Context;
import android.util.Log;

import com.toquescript.www.norcexproject.api.ApiClient;
import com.toquescript.www.norcexproject.api.ApiNorcex;
import com.toquescript.www.norcexproject.constant.Constant;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by BALAREZO on 06/06/2016.
 */
public class MeTask extends BaseAsyncTask {

    public MeTask(Context context, String _token) {
        TAG = MeTask.class.getSimpleName();
        Log.d(TAG, "INTANCE " + TAG);
        mContext = context;
        apiNorcex = ApiClient.getClient().create(ApiNorcex.class);
        stringMapMe = new HashMap<>();
        token = _token;
    }

    protected void onPreExecute() {
        Log.d(TAG, "On onPreExecute..." + TAG);
        stringMapMe.put("grant_type", Constant.GRANT_TYPE_ACCESS_RESOURCE);
        stringMapMe.put("access_token", token);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "On doInBackground..." + TAG);
        try {
            meResponseCall = apiNorcex.getMe(stringMapMe);
            responseMe = meResponseCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "On onPostExecute..." + TAG);
        if (responseMe != null) {
            switch (responseMe.code()) {
                case 200:
                    code200();
                    break;
            }
        } else {
            Log.d(TAG, "On onPostExecute... ERROR!!" + TAG);
        }
    }

    private void code200() {
        Log.d(TAG, "On code200 " + TAG);
        meResponse = responseMe.body();
        error = meResponse.error;
        message = meResponse.message;
        if (!error) {
            resultMeModel = meResponse.result;
            taksFinish.taskFinishMe(resultMeModel);
        } else {
            Log.d(TAG, "On code200... ERROR MESSAGE " + message + " " + TAG);
        }
    }
}