package com.example.tp_final_android.ui.tareas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// --- CORRECCIÓN: Usar '.' en lugar de ':' ---
import android.widget.Button;
import android.widget.EditText; // Importar EditText
import android.widget.TextView; // Importar TextView

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tp_final_android.R;

/**
 * Fragmento para Crear o Editar una Tarea.
 * Reutiliza el mismo layout para ambas acciones.
 * Determina el modo (Crear vs Editar) basado en los argumentos recibidos.
 */
public class CrearTareaFragment extends Fragment {

    private TareasViewModel viewModel;
    private EditText etNombreTarea;
    private TextView tvTituloCrearTarea; // Para cambiar el título (opcional)

    // Variables para guardar los argumentos
    private int currentTaskPosition = -1; // Default -1 (modo Crear)
    private String existingTaskText = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crear_tarea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Obtener el ViewModel COMPARTIDO
        viewModel = new ViewModelProvider(requireActivity()).get(TareasViewModel.class);

        // 2. Encontrar las Vistas
        etNombreTarea = view.findViewById(R.id.etNombreTarea);
        // El ID correcto en fragment_crear_tarea.xml es "tvAgregarTareaTitulo"
        tvTituloCrearTarea = view.findViewById(R.id.tvAgregarTareaTitulo);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);
        Button btnDeshacer = view.findViewById(R.id.btnDeshacer);

        // 3. Recibir los argumentos (para modo "Editar")
        if (getArguments() != null) {
            currentTaskPosition = getArguments().getInt("taskPosition", -1);
            // --- CORRECCIÓN: El key debe ser "taskText" (como se envió) ---
            existingTaskText = getArguments().getString("taskText");
        }

        // 4. Comprobar si estamos en modo "Editar"
        if (currentTaskPosition != -1 && existingTaskText != null) {
            // --- MODO EDITAR ---
            Log.d("CrearTareaFragment", "Modo Editar para: " + existingTaskText);
            // Poner el texto existente en el EditText
            etNombreTarea.setText(existingTaskText);
            // Cambiar el título
            if (tvTituloCrearTarea != null) {
                tvTituloCrearTarea.setText("Modificar Tarea");
            }
        } else {
            // --- MODO CREAR ---
            Log.d("CrearTareaFragment", "Modo Crear Tarea Nueva");
            if (tvTituloCrearTarea != null) {
                tvTituloCrearTarea.setText("Agregar Tarea");
            }
        }


        // 5. Configurar el botón "Guardar"
        btnGuardar.setOnClickListener(v -> {
            String taskName = etNombreTarea.getText().toString().trim();

            // Validar que el texto no esté vacío
            if (taskName.isEmpty()) {
                etNombreTarea.setError("El nombre no puede estar vacío");
                return;
            }

            // 6. Decidir si CREAR o ACTUALIZAR
            if (currentTaskPosition == -1) {
                // Modo Crear
                viewModel.addTask(taskName);
            } else {
                // Modo Editar
                viewModel.updateTask(currentTaskPosition, taskName);
            }

            // 7. Volver a la pantalla anterior
            NavHostFragment.findNavController(CrearTareaFragment.this).popBackStack();
        });

        // 8. Configurar el botón "Deshacer"
        btnDeshacer.setOnClickListener(v -> {
            NavHostFragment.findNavController(CrearTareaFragment.this).popBackStack();
        });
    }
}

