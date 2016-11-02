package com.toquescript.www.norcexproject.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by BALAREZO on 13/06/2016.
 */
public class MeasuresRealmModel extends RealmObject {
    @SerializedName("id")
    @PrimaryKey
    public String id;
    @SerializedName("tipo")
    public String tipo;
}
