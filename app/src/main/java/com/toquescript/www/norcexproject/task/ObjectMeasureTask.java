package com.toquescript.www.norcexproject.task;

import android.content.Context;
import android.util.Log;

import com.toquescript.www.norcexproject.api.ApiNorcex;
import com.toquescript.www.norcexproject.constant.Constant;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by BALAREZO on 06/06/2016.
 */
public class ObjectMeasureTask extends BaseAsyncTask{

    public ObjectMeasureTask(Context context, ApiNorcex api, String _token) {
        TAG = this.getClass().getSimpleName();
        Log.d(TAG, "INTANCE " + TAG);
        mContext = context;
        apiNorcex = api;
        stringMap = new HashMap<>();
        token = _token;
    }

    protected void onPreExecute() {
        Log.d(TAG, "On onPreExecute..." + TAG);
        stringMap.put("grant_type", Constant.GRANT_TYPE_ACCESS_RESOURCE);
        stringMap.put("access_token", token);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(TAG, "On doInBackground..." + TAG);
        try {
            objectMeasureResponseCall = apiNorcex.getObjectMeasures(stringMap);
            responseObjectMeasure = objectMeasureResponseCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "On onPostExecute..." + TAG);
        if (responseObjectMeasure != null) {
            switch (responseObjectMeasure.code()) {
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
        objectMeasureResponse = responseObjectMeasure.body();
        error = objectMeasureResponse.error;
        message = objectMeasureResponse.message;
        if (!error) {
            resultObjectMeasureModels = objectMeasureResponse.result;
            taksFinish.taskFinishObjectMeasure(resultObjectMeasureModels);
        } else {
            Log.d(TAG, "On code200... ERROR MESSAGE " + message + " " + TAG);
        }
    }
}