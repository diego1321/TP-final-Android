package com.example.tp_final_android.ui.listas;

import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.tp_final_android.model.AppDatabase; // <-- EL IMPORT CLAVE
import com.example.tp_final_android.model.Lista;
import com.example.tp_final_android.model.ListaDao;
import com.example.tp_final_android.remote.ApiClient;
import com.example.tp_final_android.remote.ApiService;
import com.example.tp_final_android.remote.UserApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * REPOSITORY (MODIFICADO PARA DI)
 * Ahora recibe sus dependencias (DAO y ApiService) en el constructor.
 */
public class ListasRepository {

    private ListaDao mListaDao;
    private ApiService mApiService;
    private LiveData<List<Lista>> mAllListas;

    private static final String TAG = "ListasRepository";

    // CAMBIO: Constructor ahora recibe dependencias
    public ListasRepository(ListaDao listaDao, ApiService apiService) {
        mListaDao = listaDao;
        mApiService = apiService;
        mAllListas = mListaDao.getAllListas();
    }

    // --- Fuente Local (Room) ---

    public LiveData<List<Lista>> getListas() {
        return mAllListas;
    }

    public void deleteList(Lista lista) {
        // Obtenemos el executor de la BD de forma estática
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mListaDao.delete(lista);
        });
    }

    public void addList(String listName) {
        Lista newList = new Lista(listName);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mListaDao.insert(newList);
        });
    }

    // --- Fuente Remota (Retrofit) ---

    public void refreshListasFromServer() {
        Call<List<UserApi>> call = mApiService.getUsers(); // Usa la instancia inyectada

        call.enqueue(new Callback<List<UserApi>>() {
            @Override
            public void onResponse(Call<List<UserApi>> call, Response<List<UserApi>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UserApi> users = response.body();

                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        for (UserApi user : users) {
                            Lista newList = new Lista(user.getName());
                            mListaDao.insert(newList);
                        }
                        Log.d(TAG, "Datos de API insertados en Room.");
                    });
                } else {
                    Log.e(TAG, "Respuesta de API no fue exitosa.");
                }
            }

            @Override
            public void onFailure(Call<List<UserApi>> call, Throwable t) {
                Log.e(TAG, "Error al llamar a la API", t);
            }
        });
    }
}