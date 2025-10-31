package com.example.tp_final_android.ui.tareas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp_final_android.R; // Asegúrate que el R sea el de tu paquete
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para el RecyclerView de la pantalla de Tareas.
 * Conecta la lista de Tareas (Strings) con las vistas (item_tarea.xml).
 */
public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareasViewHolder> {

    // Lista de datos (Strings) que el adaptador manejará
    private List<String> tasks = new ArrayList<>();
    // Listener para manejar los eventos de clic
    private final OnTaskListener listener;

    /**
     * Interfaz para manejar los clics en los ítems y en los CheckBox.
     * El Fragmento (TareasFragment) implementará esta interfaz.
     */
    public interface OnTaskListener {
        void onTaskClick(int position); // Clic en el ítem (para "Cambiar Tarea")
        void onTaskDeleteClick(int position); // Clic en el CheckBox (para "Eliminar Tarea")
    }

    /**
     * Constructor del adaptador.
     * @param listener El fragmento que escucha los eventos de clic.
     */
    public TareasAdapter(OnTaskListener listener) {
        this.listener = listener;
    }

    /**
     * Se llama cuando el RecyclerView necesita un nuevo ViewHolder (una nueva fila).
     * Infla el layout (item_tarea.xml) y lo pasa al constructor del ViewHolder.
     */
    @NonNull
    @Override
    public TareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new TareasViewHolder(view, listener);
    }

    /**
     * Se llama cuando el RecyclerView quiere rellenar una fila (ViewHolder) con datos.
     * Obtiene el dato (String) de la posición y lo asigna al TextView del ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull TareasViewHolder holder, int position) {
        String taskName = tasks.get(position);
        holder.taskNameTextView.setText(taskName);
        // Asegurarse de que el CheckBox esté tildado por defecto (como en Figma 2)
        holder.taskCheckbox.setChecked(true);
    }

    /**
     * Devuelve la cantidad total de ítems en la lista de datos.
     */
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /**
     * Método para actualizar la lista de datos del adaptador desde el ViewModel.
     * Notifica al RecyclerView que los datos han cambiado.
     */
    public void setTasks(List<String> nuevasTareas) {
        this.tasks = nuevasTareas;
        notifyDataSetChanged(); // Recarga toda la lista
    }

    // --- ViewHolder ---

    /**
     * Clase interna que representa CADA fila individual (ítem) en la lista.
     * Contiene las referencias a las vistas (TextView, CheckBox) dentro de item_tarea.xml.
     */
    public static class TareasViewHolder extends RecyclerView.ViewHolder {

        public TextView taskNameTextView;
        public CheckBox taskCheckbox;

        /**
         * Constructor del ViewHolder.
         * Encuentra las vistas por su ID y configura los listeners de clic.
         */
        public TareasViewHolder(@NonNull View itemView, OnTaskListener listener) {
            super(itemView);
            // 1. Encontrar las vistas dentro del item_tarea.xml
            taskNameTextView = itemView.findViewById(R.id.taskName);
            taskCheckbox = itemView.findViewById(R.id.taskCheckbox);

            // 2. Configurar el listener para el clic EN EL ÍTEM (para "Cambiar Tarea")
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onTaskClick(position);
                }
            });

            // 3. Configurar el listener para el clic EN EL CHECKBOX (para "Eliminar Tarea")
            taskCheckbox.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    // Solo llamar a borrar SI el usuario lo está DESTILDANDO
                    if (!taskCheckbox.isChecked()) {
                        listener.onTaskDeleteClick(position);
                    }
                }
            });
        }
    }
}
