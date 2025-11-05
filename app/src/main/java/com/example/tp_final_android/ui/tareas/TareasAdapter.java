package com.example.tp_final_android.ui.tareas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView; // Importar
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_final_android.R;
import com.example.tp_final_android.model.Tarea;

import java.util.ArrayList;
import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareasViewHolder> {

    private List<Tarea> tasks = new ArrayList<>();
    private final OnTaskListener listener;

    public interface OnTaskListener {
        void onTaskClick(int position);
        void onTaskDeleteClick(int position);
    }

    public TareasAdapter(OnTaskListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new TareasViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TareasViewHolder holder, int position) {
        Tarea currentTask = tasks.get(position);
        holder.taskNameTextView.setText(currentTask.getNombre());
        holder.taskCheckbox.setChecked(true);

        // NUEVO: Mostrar el ícono si la ruta de la imagen existe
        if (currentTask.getImagePath() != null && !currentTask.getImagePath().isEmpty()) {
            holder.ivIconoFoto.setVisibility(View.VISIBLE);
        } else {
            holder.ivIconoFoto.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Tarea> nuevasTareas) {
        this.tasks = nuevasTareas;
        notifyDataSetChanged();
    }

    public Tarea getTaskAt(int position) {
        if (tasks != null && position >= 0 && position < tasks.size()) {
            return tasks.get(position);
        }
        return null;
    }

    // --- ViewHolder ---
    public static class TareasViewHolder extends RecyclerView.ViewHolder {

        public TextView taskNameTextView;
        public CheckBox taskCheckbox;
        public ImageView ivIconoFoto; // NUEVO

        public TareasViewHolder(@NonNull View itemView, OnTaskListener listener) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.taskName);
            taskCheckbox = itemView.findViewById(R.id.taskCheckbox);
            ivIconoFoto = itemView.findViewById(R.id.ivTieneFotoIcon); // NUEVO

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onTaskClick(position);
                }
            });

            taskCheckbox.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    if (!taskCheckbox.isChecked()) {
                        listener.onTaskDeleteClick(position);
                    }
                }
            });
        }
    }
}