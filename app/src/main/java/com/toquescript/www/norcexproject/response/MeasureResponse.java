package com.toquescript.www.norcexproject.response;

import com.google.gson.annotations.SerializedName;
import com.toquescript.www.norcexproject.model.MeasureModel;

import java.util.List;

/**
 * Created by BALAREZO on 26/05/2016.
 */
public class MeasureResponse {
    @SerializedName("error")
    public boolean error;
    @SerializedName("result")
    public List<MeasureModel> result;
    @SerializedName("message")
    public String message;
}
