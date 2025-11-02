package com.example.tp_final_android.ui.tareas; 

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;

/**
 * REPOSITORY (NUEVA CLASE)
 * Es la ÚNICA fuente de verdad para los datos de Tareas.
 * Maneja la lógica de datos (ahora mockeada, luego vendrá de la API).
 */
public class TareasRepository {

    // El LiveData y la lista mutable VIVEN AQUÍ, no en el ViewModel.
    private final MutableLiveData<List<String>> taskData = new MutableLiveData<>();

    public TareasRepository() {
        // Inicializar con datos de prueba (mockeados)
        ArrayList<String> tasks = new ArrayList<>();
        tasks.add("Tarea 1: Breve descripcion");
        tasks.add("Tarea 2: Breve descripcion");
        tasks.add("Tarea 3: Breve descripcion");
        tasks.add("Tarea 4: Breve descripcion");
        tasks.add("Tarea 5: Breve descripcion");
        taskData.setValue(tasks);
    }

    // --- Getters ---

    /**
     * El ViewModel observará este LiveData.
     */
    public LiveData<List<String>> getTasks() {
        return taskData;
    }

    /**
     * Obtiene el texto de una tarea (para editar).
     */
    public String getTask(int position) {
        List<String> currentList = taskData.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            return currentList.get(position);
        }
        return null;
    }

    // --- Lógica de CRUD (movida desde el ViewModel) ---

    public void addTask(String taskName) {
        List<String> currentList = taskData.getValue();
        if (currentList != null) {
            // Creamos una nueva lista mutable basada en la actual
            ArrayList<String> mutableList = new ArrayList<>(currentList);
            mutableList.add(taskName);
            // Publicamos la nueva lista en el LiveData
            taskData.setValue(mutableList);
        }
    }

    public void deleteTask(int position) {
        List<String> currentList = taskData.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            ArrayList<String> mutableList = new ArrayList<>(currentList);
            mutableList.remove(position);
            taskData.setValue(mutableList);
        }
    }

    public void updateTask(int position, String newText) {
        List<String> currentList = taskData.getValue();
        // Validamos la posición y que el texto no esté vacío
        if (currentList != null && position >= 0 && position < currentList.size() && !newText.trim().isEmpty()) {
            ArrayList<String> mutableList = new ArrayList<>(currentList);
            mutableList.set(position, newText); // Actualiza el elemento en la posición
            taskData.setValue(mutableList);
        }
    }
}
