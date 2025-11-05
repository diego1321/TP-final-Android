package com.example.tp_final_android.remote;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interfaz de Retrofit que define los endpoints de la API.
 */
public interface ApiService {

    /**
     * Obtiene una lista de usuarios de JSONPlaceholder.
     * Usaremos esto para simular una lista de "Listas"
     */
    @GET("users")
    Call<List<UserApi>> getUsers();

}