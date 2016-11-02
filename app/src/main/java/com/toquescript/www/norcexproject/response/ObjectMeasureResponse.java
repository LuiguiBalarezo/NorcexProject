package com.toquescript.www.norcexproject.response;

import com.google.gson.annotations.SerializedName;
import com.toquescript.www.norcexproject.model.ObjectMeasureModel;

import java.util.List;

/**
 * Created by BALAREZO on 26/05/2016.
 */
public class ObjectMeasureResponse {
    @SerializedName("error")
    public boolean error;
    @SerializedName("result")
    public List<ObjectMeasureModel> result;
    @SerializedName("message")
    public String message;
}
