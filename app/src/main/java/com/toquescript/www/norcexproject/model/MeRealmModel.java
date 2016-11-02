package com.toquescript.www.norcexproject.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by BALAREZO on 02/06/2016.
 */
public class MeRealmModel extends RealmObject {
    @SerializedName("correo")
    @PrimaryKey
    public String correo;
    @SerializedName("nombres")
    public String nombres;
    @SerializedName("apellidos")
    public String apellidos;
}
