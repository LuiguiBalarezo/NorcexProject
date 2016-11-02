package com.toquescript.www.norcexproject.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BALAREZO on 27/05/2016.
 */
public class SignIn {

    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("expires_in")
    public Integer expiresIn;
    @SerializedName("token_type")
    public String tokenType;
    @SerializedName("scope")
    public String scope;
    @SerializedName("refresh_token")
    public String refreshToken;

}
