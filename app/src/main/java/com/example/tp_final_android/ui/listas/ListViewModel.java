package com.example.tp_final_android.ui.listas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
<<<<<<< HEAD
import androidx.lifecycle.ViewModel;
import java.util.List;

/**
 * ViewModel para la pantalla principal de Listas (MODIFICADO).
 * Contiene la lógica de UI (dark mode) y delega los datos de listas al Repositorio.
=======
import androidx.lifecycle.ViewModel; // CAMBIO: de AndroidViewModel a ViewModel
import com.example.tp_final_android.model.Lista;
import java.util.List;

/**
 * ViewModel para Listas (MODIFICADO PARA DI).
 * - Extiende ViewModel (ya no necesita Application).
 * - Recibe el Repositorio en el constructor.
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
 */
public class ListViewModel extends ViewModel { // CAMBIO

<<<<<<< HEAD
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
=======
    private final ListasRepository repository; // Ya no es 'final'
    private final LiveData<List<Lista>> listData;
    private final MutableLiveData<Boolean> isDarkMode = new MutableLiveData<>();

    // CAMBIO: Constructor recibe el Repositorio
    public ListViewModel(ListasRepository repository) {
        this.repository = repository;
        this.listData = repository.getListas();
        isDarkMode.setValue(false);
    }

    // --- (El resto de la clase no cambia) ---

    public LiveData<List<Lista>> getListas() {
        return listData;
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
    }

    public LiveData<Boolean> isDarkModeEnabled() {
        return isDarkMode;
    }

<<<<<<< HEAD
    // --- Lógica de UI (Se queda aquí) ---

    /**
     * Alterna el estado del tema claro/oscuro.
     */
=======
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
    public void toggleDarkMode() {
        Boolean currentMode = isDarkMode.getValue();
        if (currentMode != null) {
            isDarkMode.setValue(!currentMode);
        } else {
            isDarkMode.setValue(true);
        }
    }

<<<<<<< HEAD
    // --- Lógica de Datos (Delega al Repositorio) ---

    /**
     * Lógica de CRUD: Eliminar una lista por su posición.
     * @param position La posición de la lista a eliminar.
     */
    public void deleteList(int position) {
        repository.deleteList(position);
=======
    public void deleteList(int position) {
        List<Lista> currentList = listData.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            Lista listaParaBorrar = currentList.get(position);
            repository.deleteList(listaParaBorrar);
        }
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
    }

    public void addList(String listName) {
        repository.addList(listName);
    }
<<<<<<< HEAD
}
=======

    public void refreshListas() {
        repository.refreshListasFromServer();
    }
}
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
