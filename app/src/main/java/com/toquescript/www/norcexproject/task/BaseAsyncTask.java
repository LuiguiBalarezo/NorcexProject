package com.toquescript.www.norcexproject.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.toquescript.www.norcexproject.api.ApiNorcex;
import com.toquescript.www.norcexproject.callbacks.CallbackTaskFinish;
import com.toquescript.www.norcexproject.callbacks.LoginTaksFinish;
import com.toquescript.www.norcexproject.model.MeRealmModel;
import com.toquescript.www.norcexproject.model.MeasureModel;
import com.toquescript.www.norcexproject.model.ObjectRealmModel;
import com.toquescript.www.norcexproject.response.MeResponse;
import com.toquescript.www.norcexproject.response.MeasureResponse;
import com.toquescript.www.norcexproject.model.ObjectMeasureModel;
import com.toquescript.www.norcexproject.response.ObjectMeasureResponse;
import com.toquescript.www.norcexproject.response.ObjectModelResponse;
import com.toquescript.www.norcexproject.model.SignIn;
import com.toquescript.www.norcexproject.request.SignInRequest;
import com.toquescript.www.norcexproject.response.SignInResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by BALAREZO on 08/06/2016.
 */
public class BaseAsyncTask extends AsyncTask<Void, Void, Void> {

    /*VARIABLES GENERALES*/
    public String TAG;
    public Context mContext;
    public ApiNorcex apiNorcex;
    public String token;
    public boolean error;
    public Integer expires_in;
    public Intent intent;
    public String message;
    public Map<String, String> stringMap;

    /*VARIABLES LOGINTASK*/
    public SignIn result = null;
    public SignInResponse signInResponse;
    public Call<SignInResponse> responseCall;
    public SignInRequest signinrequest;
    public Response<SignInResponse> response;
    public String accountType, username, refresh_token, token_type, scope;
    public LoginTaksFinish loginTaksFinish;

    /*VARIABLES METASK*/
    public MeRealmModel resultMeModel = null;
    public MeResponse meResponse;
    public Call<MeResponse> meResponseCall;
    public Map<String, String> stringMapMe;
    public Response<MeResponse> responseMe;
    public CallbackTaskFinish taksFinish;

     /*VARIABLES OBJECTTASK*/
    public List<ObjectRealmModel> resultObjectModels = null;
    public ObjectModelResponse objectResponse;
    public Call<ObjectModelResponse> objectResponseCall;
    public Response<ObjectModelResponse> responseObject;

    /*VARIABLES MEASURETASK*/
    public List<MeasureModel> resultMeasureModels = null;
    public MeasureResponse measureResponse;
    public Call<MeasureResponse> measureResponseCall;
    public Response<MeasureResponse> responseMeasure;

    /*VARIABLES MEASURETASK*/
    public List<ObjectMeasureModel> resultObjectMeasureModels = null;
    public ObjectMeasureResponse objectMeasureResponse;
    public Call<ObjectMeasureResponse> objectMeasureResponseCall;
    public Response<ObjectMeasureResponse> responseObjectMeasure;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
