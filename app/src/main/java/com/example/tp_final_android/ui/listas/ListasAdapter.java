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
import com.example.tp_final_android.R;
import com.example.tp_final_android.model.Lista; // Importante

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para el RecyclerView de Listas (MODIFICADO).
 * Ahora trabaja con una lista de objetos <Lista> en lugar de <String>.
 */
public class ListasAdapter extends RecyclerView.Adapter<ListasAdapter.ListasViewHolder> {

    // CAMBIO: La lista ahora es de tipo 'Lista'
    private List<Lista> listas = new ArrayList<>();

    private final OnListListener listener;

    public interface OnListListener {
        void onListClick(int position);
        void onDeleteClick(int position);
    }

    public ListasAdapter(OnListListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ListasViewHolder(view, listener);
    }

    /**
     * Rellena la fila (ViewHolder) con datos.
     * Obtiene el objeto 'Lista' y usa su nombre.
     */
    @Override
    public void onBindViewHolder(@NonNull ListasViewHolder holder, int position) {
        // CAMBIO: Obtener el objeto 'Lista'
        Lista currentLista = listas.get(position);
        // CAMBIO: Usar el getter para el nombre
        holder.listNameTextView.setText(currentLista.getNombre());

        holder.checkboxItem.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    /**
     * Método para actualizar la lista de datos del adaptador.
     * CAMBIO: Acepta una lista de tipo 'Lista'.
     */
    public void setListas(List<Lista> nuevasListas) {
        this.listas = nuevasListas;
        notifyDataSetChanged();
    }

    // --- ViewHolder ---

    public static class ListasViewHolder extends RecyclerView.ViewHolder {

        public TextView listNameTextView;
        public CheckBox checkboxItem;

        public ListasViewHolder(@NonNull View itemView, OnListListener listener) {
            super(itemView);
            listNameTextView = itemView.findViewById(R.id.listName);
            checkboxItem = itemView.findViewById(R.id.checkboxItem);

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked},
                            new int[]{android.R.attr.state_checked}
                    },
                    new int[]{
                            Color.GRAY,
                            Color.parseColor("#673AB7")
                    }
            );
            checkboxItem.setButtonTintList(colorStateList);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onListClick(position);
                }
            });

            checkboxItem.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    if (!checkboxItem.isChecked()) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }
}