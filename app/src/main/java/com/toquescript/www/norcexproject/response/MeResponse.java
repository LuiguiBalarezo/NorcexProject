package com.toquescript.www.norcexproject.response;

import com.google.gson.annotations.SerializedName;
import com.toquescript.www.norcexproject.model.MeRealmModel;

/**
 * Created by BALAREZO on 26/05/2016.
 */
public class MeResponse {
    @SerializedName("error")
    public boolean error;
    @SerializedName("result")
    public MeRealmModel result;
    @SerializedName("message")
    public String message;
}
