package com.example.tp_final_android.ui.tareas;

import androidx.lifecycle.LiveData;
<<<<<<< HEAD
import androidx.lifecycle.ViewModel;
import java.util.List; // Mantener esta importación

/**
 * ViewModel para la pantalla de Tareas (MODIFICADO).
 * Ya no contiene lógica de datos, solo delega al Repositorio.
=======
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel; // CAMBIO: de AndroidViewModel a ViewModel
import com.example.tp_final_android.model.Tarea;
import java.util.List;

/**
 * ViewModel para Tareas (MODIFICADO PARA DI).
 * - Extiende ViewModel.
 * - Recibe el Repositorio en el constructor.
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
 */
public class TareasViewModel extends ViewModel { // CAMBIO

<<<<<<< HEAD
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
=======
    private final TareasRepository repository;
    private final MutableLiveData<Integer> currentListId = new MutableLiveData<>();
    private final LiveData<List<Tarea>> taskData;

    // CAMBIO: Constructor recibe el Repositorio
    public TareasViewModel(TareasRepository repository) {
        this.repository = repository;
        taskData = Transformations.switchMap(currentListId, id ->
                repository.getTasksForList(id)
        );
    }

    // --- (El resto de la clase no cambia) ---

    public void loadTasksForList(int listId) {
        currentListId.setValue(listId);
    }

    public LiveData<List<Tarea>> getTasks() {
        return taskData;
    }

    public void addTask(String taskName, String imagePath) {
        Integer listId = currentListId.getValue();
        if (taskName.isEmpty() || listId == null) {
            return;
        }
        repository.addTask(taskName, listId, imagePath);
    }

    public void deleteTask(int position) {
        List<Tarea> currentList = taskData.getValue();
        if (currentList != null && position >= 0 && position < currentList.size()) {
            Tarea tareaParaBorrar = currentList.get(position);
            repository.deleteTask(tareaParaBorrar);
        }
    }

    public void updateTask(int taskId, String newText, String imagePath) {
        Integer listId = currentListId.getValue();
        if (newText.isEmpty() || listId == null || taskId == -1) {
            return;
        }
        Tarea updatedTarea = new Tarea(newText, listId, imagePath);
        updatedTarea.setId(taskId);

        repository.updateTask(updatedTarea);
    }
}
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
