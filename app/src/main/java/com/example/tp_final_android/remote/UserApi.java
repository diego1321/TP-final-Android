package com.example.tp_final_android.remote;

import com.google.gson.annotations.SerializedName;

/**
 * POJO que representa la respuesta de la API.
 * Solo mapeamos los campos que nos interesan (id y name).
 */
public class UserApi {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}