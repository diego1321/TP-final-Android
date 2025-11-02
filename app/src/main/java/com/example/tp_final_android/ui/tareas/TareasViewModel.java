package com.example.tp_final_android.ui.tareas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List; // Mantener esta importación

/**
 * ViewModel para la pantalla de Tareas (MODIFICADO).
 * Ya no contiene lógica de datos, solo delega al Repositorio.
 */
public class TareasViewModel extends ViewModel {

    // 1. Declarar el Repositorio
    private final TareasRepository repository;

    // 2. Declarar el LiveData (que viene del Repositorio)
    private final LiveData<List<String>> taskData;

    public TareasViewModel() {
        // 3. Inicializar el Repositorio
        // NOTA: Más adelante (con Hilt/Dagger) esto se "inyecta"
        repository = new TareasRepository(); 
        
        // 4. Obtener el LiveData desde el Repositorio
        taskData = repository.getTasks();
    }

    // --- Getters ---

    /**
     * El Fragment observa este LiveData (que viene del Repositorio).
     */
    public LiveData<List<String>> getTasks() {
        return taskData; // Devuelve el LiveData del repo
    }

    /**
     * Obtiene una tarea (solo pasa la llamada al repo).
     */
    public String getTask(int position) {
        // Solo delega la llamada
        return repository.getTask(position);
    }

    // --- Lógica de CRUD (Ahora solo DELEGA la llamada) ---

    /**
     * Añade una nueva tarea a la lista.
     * (Usado por CrearTareaFragment)
     */
    public void addTask(String taskName) {
        repository.addTask(taskName);
    }

    /**
     * Borra una tarea de la lista por su posición.
     * (Usado por TareasFragment)
     */
    public void deleteTask(int position) {
        repository.deleteTask(position);
    }

    /**
     * Actualiza el texto de una tarea existente en una posición específica.
     */
    public void updateTask(int position, String newText) {
        repository.updateTask(position, newText);
    }
}
