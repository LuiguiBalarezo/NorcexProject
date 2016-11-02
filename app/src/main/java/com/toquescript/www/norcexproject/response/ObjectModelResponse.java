package com.toquescript.www.norcexproject.response;

import com.google.gson.annotations.SerializedName;
import com.toquescript.www.norcexproject.model.ObjectRealmModel;

import java.util.List;

/**
 * Created by BALAREZO on 26/05/2016.
 */
public class ObjectModelResponse {
    @SerializedName("error")
    public boolean error;
    @SerializedName("result")
    public List<ObjectRealmModel> result;
    @SerializedName("message")
    public String message;
}
