package com.example.tp_final_android;

import android.app.Application;

import com.example.tp_final_android.model.AppDatabase;
import com.example.tp_final_android.remote.ApiClient;
import com.example.tp_final_android.remote.ApiService;
import com.example.tp_final_android.ui.listas.ListasRepository; // <-- IMPORT AÑADIDO
import com.example.tp_final_android.ui.tareas.TareasRepository; // <-- IMPORT AÑADIDO

/**
 * Clase Application personalizada para actuar como
 * contenedor de dependencias (Service Locator).
 *
 * Crea singletons para la Base de Datos y los Repositorios.
 */
public class MyApplication extends Application {

    // Instancias Singleton
    private AppDatabase database;
    public ListasRepository listasRepository;
    public TareasRepository tareasRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializar todas las dependencias una sola vez

        // 1. Base de Datos
        database = AppDatabase.getDatabase(this);

        // 2. ApiService
        ApiService apiService = ApiClient.getApiService();

        // 3. Repositorios (inyectando sus dependencias)
        listasRepository = new ListasRepository(database.listaDao(), apiService);
        tareasRepository = new TareasRepository(database.tareaDao());
    }
}