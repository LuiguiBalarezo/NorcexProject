package com.toquescript.www.norcexproject.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by BALAREZO on 02/06/2016.
 */
public class ObjectRealmModel extends RealmObject{
    @SerializedName("id")
    @PrimaryKey
    public String id;
    @SerializedName("nombre")
    public String nombre;
    @SerializedName("id_tipo")
    public String id_tipo;
    @SerializedName("cantidad")
    public String cantidad;
    @SerializedName("medida")
    public String medida;
    @SerializedName("medidas")
    public RealmList<MeasuresRealmModel> medidas;
    @SerializedName("active")
    public boolean isActive;

}
