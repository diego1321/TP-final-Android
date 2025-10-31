package com.example.tp_final_android.ui.listas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList; // Importar ArrayList
import java.util.List;

/**
 * ViewModel para la pantalla principal de Listas (siguiendo el patrón MVVM y el requisito de Java).
 * Contendrá la lógica de negocio para obtener, crear y manipular las listas.
 */
public class ListViewModel extends ViewModel {

    // LiveData para la lista de listas (datos observables)
    private final MutableLiveData<List<String>> listData = new MutableLiveData<>();

    // LiveData para el estado del tema (dark mode)
    private final MutableLiveData<Boolean> isDarkMode = new MutableLiveData<>();

    public ListViewModel() {
        // 1. Crear una lista mutable (ArrayList) para poder añadir y borrar
        ArrayList<String> mockListas = new ArrayList<>();
        mockListas.add("Lista Tareas Universidad");
        mockListas.add("Lista Compras");
        mockListas.add("Lista Viaje");

        listData.setValue(mockListas); // Establecer la lista mutable
        isDarkMode.setValue(false); // Tema por defecto claro
    }

    // --- Getters de LiveData (Solo lectura desde la Vista/Fragment) ---

    public LiveData<List<String>> getListas() {
        return listData;
    }

    public LiveData<Boolean> isDarkModeEnabled() {
        return isDarkMode;
    }

    // --- Lógica de Negocio ---

    /**
     * Alterna el estado del tema claro/oscuro.
     */
    public void toggleDarkMode() {
        Boolean currentMode = isDarkMode.getValue();
        if (currentMode != null) {
            isDarkMode.setValue(!currentMode);
        } else {
            isDarkMode.setValue(true);
        }
    }

    /**
     * Lógica de CRUD: Eliminar una lista por su posición.
     * @param position La posición de la lista a eliminar.
     */
    public void deleteList(int position) {
        // 2. Obtener la lista actual (que ahora es mutable)
        ArrayList<String> currentList = (ArrayList<String>) listData.getValue();

        // 3. Validar y eliminar el ítem
        if (currentList != null && position >= 0 && position < currentList.size()) {
            currentList.remove(position);
            // 4. Publicar la lista actualizada al LiveData
            listData.setValue(currentList);
        }
    }

    /**
     * Lógica de CRUD: Añadir una nueva lista.
     * @param listName El nombre de la nueva lista.
     */
    public void addList(String listName) {
        // 1. Obtener la lista actual (que es un ArrayList)
        ArrayList<String> currentList = (ArrayList<String>) listData.getValue();

        // 2. Si la lista no es nula, añadir el nuevo item
        if (currentList != null) {
            currentList.add(listName);
            // 3. Publicar la lista actualizada al LiveData
            listData.setValue(currentList);
        } else {
            // Caso borde: si la lista es nula por alguna razón, crear una nueva
            ArrayList<String> newList = new ArrayList<>();
            newList.add(listName);
            listData.setValue(newList);
        }
        // NOTA: En una app real con base de datos, aquí llamaríamos al Repositorio
        // para insertar el dato, y la base de datos (con Room) notificaría
        // automáticamente al LiveData, por lo que no necesitaríamos llamar a setValue().
    }

    // TODO: Implementar métodos para Cambiar Lista (CRUD)
}

