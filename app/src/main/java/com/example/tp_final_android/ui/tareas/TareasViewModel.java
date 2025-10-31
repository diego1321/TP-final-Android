package com.example.tp_final_android.ui.tareas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel para la pantalla de Tareas (siguiendo el patrón MVVM).
 * Contendrá la lógica de negocio para obtener, crear y manipular las Tareas
 * de UNA lista específica.
 */
public class TareasViewModel extends ViewModel {

    // TODO: Reemplazar String con una clase de modelo de datos 'Tarea' real
    // LiveData para la lista de tareas (datos observables)
    private final MutableLiveData<List<String>> taskData = new MutableLiveData<>();

    public TareasViewModel() {
        // Inicializar datos de prueba (mock) para las tareas
        ArrayList<String> mockTasks = new ArrayList<>();
        mockTasks.add("Tarea 1: Breve descripcion");
        mockTasks.add("Tarea 2: Breve descripcion");
        mockTasks.add("Tarea 3: Breve descripcion");
        mockTasks.add("Tarea 4: Breve descripcion");
        mockTasks.add("Tarea 5: Breve descripcion");
        taskData.setValue(mockTasks);
    }

    // --- Getter de LiveData (Solo lectura desde la Vista/Fragment) ---

    public LiveData<List<String>> getTasks() {
        return taskData;
    }

    // --- Lógica de Negocio (CRUD) ---

    /**
     * Lógica de CRUD: Añadir una nueva tarea.
     * @param taskName El nombre de la new task.
     */
    public void addTask(String taskName) {
        // TODO: Esta lógica se llamará desde CrearTareaFragment
        List<String> currentTasks = taskData.getValue();
        if (currentTasks != null) {
            // Creamos una nueva lista para notificar al observer
            ArrayList<String> updatedTasks = new ArrayList<>(currentTasks);
            updatedTasks.add(taskName);
            taskData.setValue(updatedTasks);
        }
    }

    /**
     * Lógica de CRUD: Eliminar una tarea (por posición).
     * @param position La posición de la tarea a eliminar.
     */
    public void deleteTask(int position) {
        List<String> currentTasks = taskData.getValue();
        if (currentTasks != null && position >= 0 && position < currentTasks.size()) {
            // Creamos una nueva lista para notificar al observer
            ArrayList<String> updatedTasks = new ArrayList<>(currentTasks);
            updatedTasks.remove(position);
            taskData.setValue(updatedTasks);
        }
    }

    // TODO: Implementar método para Cambiar Tarea (Update)
}
