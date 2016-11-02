package com.toquescript.www.norcexproject.api;

import com.toquescript.www.norcexproject.response.MeResponse;
import com.toquescript.www.norcexproject.response.MeasureResponse;
import com.toquescript.www.norcexproject.response.ObjectMeasureResponse;
import com.toquescript.www.norcexproject.response.ObjectModelResponse;
import com.toquescript.www.norcexproject.request.SignInRequest;
import com.toquescript.www.norcexproject.response.SignInResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by BALAREZO on 26/05/2016.
 */
public interface ApiNorcex {

    @POST("authorization/signin")
    Call<SignInResponse> signin(@Body SignInRequest signInRequest);

    @GET("resources/me")
    Call<MeResponse> getMe(@QueryMap Map<String, String> options);

    @GET("resources/objects")
    Call<ObjectModelResponse> getObjects(@QueryMap Map<String, String> options);

    @GET("resources/measures")
    Call<MeasureResponse> getMeasures(@QueryMap Map<String, String> options);

    @GET("resources/objectmeasures")
    Call<ObjectMeasureResponse> getObjectMeasures(@QueryMap Map<String, String> options);

}
