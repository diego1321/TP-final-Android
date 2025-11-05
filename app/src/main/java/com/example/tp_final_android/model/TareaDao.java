package com.example.tp_final_android.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Tarea.
 */
@Dao
public interface TareaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tarea tarea);

    @Update
    void update(Tarea tarea);

    @Delete
    void delete(Tarea tarea);

    /**
     * Consulta clave: Obtiene solo las tareas que pertenecen
     * a un ID de lista específico.
     * @param listId El ID de la Lista padre.
     * @return Un LiveData con la lista de Tareas.
     */
    @Query("SELECT * FROM tareas_table WHERE listaId = :listId ORDER BY nombre ASC")
    LiveData<List<Tarea>> getTasksForList(int listId);
}