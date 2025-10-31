package com.example.tp_final_android.ui.tareas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

// Asegúrate de que el R sea el de tu paquete (ej: com.example.tp_final_android.R)
import com.example.tp_final_android.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragmento para la pantalla de "Crear Tarea" (la tercera pantalla del Figma).
 */
public class CrearTareaFragment extends Fragment {

    private TextInputEditText etNombreTarea;
    private Button btnGuardar;
    private Button btnDeshacer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout que creamos en el paso anterior
        return inflater.inflate(R.layout.fragment_crear_tarea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Enlazar vistas
        etNombreTarea = view.findViewById(R.id.etNombreTarea);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnDeshacer = view.findViewById(R.id.btnDeshacer);

        // 2. Configurar listener para "Guardar"
        btnGuardar.setOnClickListener(v -> {
            String nombreTarea = etNombreTarea.getText().toString();

            if (!nombreTarea.isEmpty()) {
                Log.d("CrearTareaFragment", "Tarea a guardar: " + nombreTarea);
                // TODO: Aquí llamarías al ViewModel para guardar la tarea en la BD (MVVM)

                // Después de guardar, navegar hacia atrás
                NavHostFragment.findNavController(CrearTareaFragment.this).popBackStack();
            } else {
                // Opcional: Mostrar error si el campo está vacío
                etNombreTarea.setError("El nombre no puede estar vacío");
            }
        });

        // 3. Configurar listener para "Deshacer" (Cancelar)
        btnDeshacer.setOnClickListener(v -> {
            // Simplemente navegar hacia atrás sin guardar
            NavHostFragment.findNavController(CrearTareaFragment.this).popBackStack();
        });
    }
}
