package com.example.tp_final_android.ui.listas; 

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

/**
 * REPOSITORY (NUEVA CLASE)
 * Es la ÚNICA fuente de verdad para los datos de Listas.
 */
public class ListasRepository {

    // El LiveData y la lista mutable VIVEN AQUÍ
    private final MutableLiveData<List<String>> listData = new MutableLiveData<>();

    public ListasRepository() {
        ArrayList<String> mockListas = new ArrayList<>();
        mockListas.add("Lista Tareas Universidad");
        mockListas.add("Lista Compras");
        mockListas.add("Lista Viaje");
        listData.setValue(mockListas);
    }

    // --- Getters de LiveData ---
    public LiveData<List<String>> getListas() {
        return listData;
    }

    // --- Lógica de CRUD (movida desde el ViewModel) ---

    public void deleteList(int position) {
        // Obtenemos la lista actual (que es un ArrayList)
        ArrayList<String> currentList = (ArrayList<String>) listData.getValue();

        // Validar y eliminar el ítem
        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.remove(position);
            // Publicar la lista actualizada al LiveData
            listData.setValue(currentList);
        }
    }

    public void addList(String listName) {
        // Obtener la lista actual (que es un ArrayList)
        ArrayList<String> currentList = (ArrayList<String>) listData.getValue();

        // Si la lista no es nula, añadir el nuevo item
        if (currentList != null) {
            currentList.add(listName);
            // Publicar la lista actualizada al LiveData
            listData.setValue(currentList);
        } else {
            // Caso borde: si la lista es nula por alguna razón, crear una nueva
            ArrayList<String> newList = new ArrayList<>();
            newList.add(listName);
            listData.setValue(newList);
        }
    }
}
