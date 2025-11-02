package com.example.tp_final_android.ui.tareas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel para la pantalla de Tareas (compartido con CrearTareaFragment).
 * Maneja la lógica de negocio para las tareas (CRUD).
 */
public class TareasViewModel extends ViewModel {

    // Usamos un MutableLiveData que contiene una Lista de Strings.
    private final MutableLiveData<List<String>> taskData = new MutableLiveData<>();

    public TareasViewModel() {
        // Inicializar con datos de prueba (mockeados)
        // Usamos ArrayList para que sea una lista mutable
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
     * El Fragment observa este LiveData para actualizar la UI.
     */
    public LiveData<List<String>> getTasks() {
        return taskData;
    }

    // --- Lógica de CRUD (Crear, Leer, Actualizar, Borrar) ---

    /**
     * Añade una nueva tarea a la lista.
     * (Usado por CrearTareaFragment)
     */
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

    /**
     * Borra una tarea de la lista por su posición.
     * (Usado por TareasFragment)
     */
    public void deleteTask(int position) {
        List<String> currentList = taskData.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            ArrayList<String> mutableList = new ArrayList<>(currentList);
            mutableList.remove(position);
            taskData.setValue(mutableList);
        }
    }

    // --- NUEVOS MÉTODOS PARA "MODIFICAR TAREA" ---

    /**
     * Actualiza el texto de una tarea existente en una posición específica.
     * @param position La posición de la tarea a actualizar.
     * @param newText El nuevo texto para la tarea.
     */
    public void updateTask(int position, String newText) {
        List<String> currentList = taskData.getValue();
        // Validamos la posición y que el texto no esté vacío
        if (currentList != null && position >= 0 && position < currentList.size() && !newText.trim().isEmpty()) {
            ArrayList<String> mutableList = new ArrayList<>(currentList);
            mutableList.set(position, newText); // Actualiza el elemento en la posición
            taskData.setValue(mutableList);
        }
    }

    /**
     * Obtiene el texto de una tarea específica por su posición.
     * @param position La posición de la tarea.
     * @return El texto de la tarea, o null si la posición es inválida.
     */
    public String getTask(int position) {
        List<String> currentList = taskData.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            return currentList.get(position);
        }
        return null;
    }
}

