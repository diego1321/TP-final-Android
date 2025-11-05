package com.example.tp_final_android.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * DAO (Data Access Object) para la entidad Lista.
 * Define cómo accedemos a los datos.
 */
@Dao
public interface ListaDao {

    // Inserta una lista. Si ya existe (mismo id), la reemplaza.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Lista lista);

    // Borra una lista
    @Delete
    void delete(Lista lista);

    // Obtiene todas las listas ordenadas por nombre
    // Room mágicamente convierte esto en un LiveData que podemos observar
    @Query("SELECT * FROM listas_table ORDER BY nombre ASC")
    LiveData<List<Lista>> getAllListas();
}