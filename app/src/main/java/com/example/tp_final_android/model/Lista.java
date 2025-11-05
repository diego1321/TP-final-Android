package com.example.tp_final_android.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad (Tabla) que representa una Lista.
 */
@Entity(tableName = "listas_table")
public class Lista {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;

    // Room usará este constructor
    public Lista(String nombre) {
        this.nombre = nombre;
    }

    // --- Getters y Setters (Room los necesita) ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
