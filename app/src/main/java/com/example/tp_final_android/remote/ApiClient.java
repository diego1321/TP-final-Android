package com.example.tp_final_android.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Cliente Retrofit. Sigue el patrón Singleton.
 * Se encarga de crear la instancia de Retrofit.
 */
public class ApiClient {

    // URL base de la API pública que usaremos
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private static Retrofit retrofit = null;

    /**
     * Obtiene la instancia de Retrofit (Singleton)
     */
    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Usa Gson
                    .build();
        }
        return retrofit;
    }

    /**
     * Método público para obtener el servicio ApiService
     */
    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}