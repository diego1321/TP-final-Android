package com.example.tp_final_android.ui.listas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

/**
 * ViewModel para la pantalla principal de Listas (MODIFICADO).
 * Contiene la lógica de UI (dark mode) y delega los datos de listas al Repositorio.
 */
public class ListViewModel extends ViewModel {

    // --- Repositorio (NUEVO) ---
    private final ListasRepository repository;

    // --- LiveData de Listas (viene del Repo) ---
    private final LiveData<List<String>> listData;

    // --- LiveData de UI (Se queda en el ViewModel) ---
    // El estado del Dark Mode es un estado de UI, no de datos,
    // por lo que está bien que se quede en el ViewModel.
    private final MutableLiveData<Boolean> isDarkMode = new MutableLiveData<>();

    public ListViewModel() {
        // Inicializar el Repositorio
        repository = new ListasRepository();
        // Obtener los datos del Repositorio
        listData = repository.getListas();
        
        // Lógica de UI se queda aquí
        isDarkMode.setValue(false); // Tema por defecto claro
    }

    // --- Getters de LiveData ---

    public LiveData<List<String>> getListas() {
        return listData; // Devuelve el LiveData del repo
    }

    public LiveData<Boolean> isDarkModeEnabled() {
        return isDarkMode;
    }

    // --- Lógica de UI (Se queda aquí) ---

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

    // --- Lógica de Datos (Delega al Repositorio) ---

    /**
     * Lógica de CRUD: Eliminar una lista por su posición.
     * @param position La posición de la lista a eliminar.
     */
    public void deleteList(int position) {
        repository.deleteList(position);
    }

    /**
     * Lógica de CRUD: Añadir una nueva lista.
     * @param listName El nombre de la nueva lista.
     */
    public void addList(String listName) {
        repository.addList(listName);
    }
}
