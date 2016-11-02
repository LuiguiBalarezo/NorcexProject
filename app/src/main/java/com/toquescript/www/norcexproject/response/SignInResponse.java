package com.toquescript.www.norcexproject.response;

import com.google.gson.annotations.SerializedName;
import com.toquescript.www.norcexproject.model.SignIn;

/**
 * Created by BALAREZO on 26/05/2016.
 */
public class SignInResponse {
    @SerializedName("error")
    public boolean error;
    @SerializedName("result")
    public SignIn result;
    @SerializedName("message")
    public String message;
}
