package com.example.tp_final_android.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tareas_table",
        foreignKeys = @ForeignKey(entity = Lista.class,
                parentColumns = "id",
                childColumns = "listaId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("listaId")})
public class Tarea {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;
    private int listaId;

    // NUEVO: Campo para guardar la ruta del archivo de la imagen
    private String imagePath;

    // CAMBIO: Constructor actualizado
    public Tarea(String nombre, int listaId, String imagePath) {
        this.nombre = nombre;
        this.listaId = listaId;
        this.imagePath = imagePath;
    }

    // --- Getters y Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getListaId() { return listaId; }
    public void setListaId(int listaId) { this.listaId = listaId; }

    // NUEVO: Getters y setters para la ruta
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}