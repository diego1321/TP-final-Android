package com.example.tp_final_android.ui.listas;

import android.content.res.ColorStateList;
import android.graphics.Color;
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
 * Adaptador para el RecyclerView de la pantalla de Listas.
 * Conecta la lista de datos (Strings) con las vistas (item_list.xml).
 */
public class ListasAdapter extends RecyclerView.Adapter<ListasAdapter.ListasViewHolder> {

    // Lista de datos (Strings) que el adaptador manejará
    private List<String> listas = new ArrayList<>();
    // Listener para manejar los eventos de clic
    private final OnListListener listener;

    /**
     * Interfaz para manejar los clics en los ítems y en los CheckBox.
     * El Fragmento (ListasFragment) implementará esta interfaz.
     */
    public interface OnListListener {
        void onListClick(int position); // Clic en el ítem (para navegar)
        void onDeleteClick(int position); // Clic en el CheckBox (para borrar)
    }

    /**
     * Constructor del adaptador.
     * @param listener El fragmento que escucha los eventos de clic.
     */
    public ListasAdapter(OnListListener listener) {
        this.listener = listener;
    }

    /**
     * Se llama cuando el RecyclerView necesita un nuevo ViewHolder (una nueva fila).
     * Infla el layout (item_list.xml) y lo pasa al constructor del ViewHolder.
     */
    @NonNull
    @Override
    public ListasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ListasViewHolder(view, listener);
    }

    /**
     * Se llama cuando el RecyclerView quiere rellenar una fila (ViewHolder) con datos.
     * Obtiene el dato (String) de la posición y lo asigna al TextView del ViewHolder.
     */
    @Override
    public void onBindViewHolder(@NonNull ListasViewHolder holder, int position) {
        String listName = listas.get(position);
        holder.listNameTextView.setText(listName);
        // Asegurarse de que el CheckBox esté tildado por defecto
        holder.checkboxItem.setChecked(true);
    }

    /**
     * Devuelve la cantidad total de ítems en la lista de datos.
     */
    @Override
    public int getItemCount() {
        return listas.size();
    }

    /**
     * Método para actualizar la lista de datos del adaptador desde el ViewModel.
     * Notifica al RecyclerView que los datos han cambiado.
     */
    public void setListas(List<String> nuevasListas) {
        this.listas = nuevasListas;
        notifyDataSetChanged(); // Recarga toda la lista (simple, pero efectivo)
    }

    // --- ViewHolder ---

    /**
     * Clase interna que representa CADA fila individual (ítem) en la lista.
     * Contiene las referencias a las vistas (TextView, CheckBox) dentro de item_list.xml.
     */
    public static class ListasViewHolder extends RecyclerView.ViewHolder {

        public TextView listNameTextView;
        public CheckBox checkboxItem;

        /**
         * Constructor del ViewHolder.
         * Encuentra las vistas por su ID y configura los listeners de clic.
         */
        public ListasViewHolder(@NonNull View itemView, OnListListener listener) {
            super(itemView);
            // 1. Encontrar las vistas dentro del item_list.xml
            listNameTextView = itemView.findViewById(R.id.listName);
            checkboxItem = itemView.findViewById(R.id.checkboxItem); // ID del CheckBox

            // Configurar el tinte del CheckBox (morado)
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked}, // no tildado (nunca pasa)
                            new int[]{android.R.attr.state_checked}  // tildado
                    },
                    new int[]{
                            Color.GRAY,  // Color si no estuviera tildado
                            Color.parseColor("#673AB7") // Color morado cuando está tildado
                    }
            );
            checkboxItem.setButtonTintList(colorStateList);


            // 2. Configurar el listener para el clic EN EL ÍTEM (para navegar)
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onListClick(position);
                }
            });

            // 3. Configurar el listener para el clic EN EL CHECKBOX (para borrar)
            checkboxItem.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    // Solo llamar a borrar SI el usuario lo está DESTILDANDO
                    if (!checkboxItem.isChecked()) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }
}

