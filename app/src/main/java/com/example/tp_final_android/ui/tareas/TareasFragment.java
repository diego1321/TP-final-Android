package com.example.tp_final_android.ui.tareas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp_final_android.R;

/**
 * Fragmento para la pantalla de Tareas (la segunda pantalla del Figma).
 * Ahora muestra un RecyclerView con la lista de tareas.
 */
public class TareasFragment extends Fragment implements TareasAdapter.OnTaskListener {

    private TareasViewModel viewModel;
    private TareasAdapter adapter;
    private RecyclerView recyclerViewTareas;
    private TextView tvListaTitulo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout (fragment_tareas.xml, que ahora tiene el botón Volver)
        return inflater.inflate(R.layout.fragment_tareas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Encontrar Vistas
        tvListaTitulo = view.findViewById(R.id.tvListaTitulo);
        recyclerViewTareas = view.findViewById(R.id.recyclerViewTareas);

        // 2. Inicializar ViewModel (específico de este Fragment)
        viewModel = new ViewModelProvider(this).get(TareasViewModel.class);

        // 3. Configurar el RecyclerView
        setupRecyclerView();

        // 4. Configurar el listener para "Agregar Tarea"
        view.findViewById(R.id.btnAgregarTarea).setOnClickListener(v -> {
            // Navegar a la Pantalla 3
            NavHostFragment.findNavController(TareasFragment.this)
                    .navigate(R.id.action_tareasFragment_to_crearTareaFragment);
        });

        // 5. MODIFICADO: Configurar el listener para el nuevo botón "Volver"
        view.findViewById(R.id.btnVolver).setOnClickListener(v -> {
            // Usar popBackStack() para volver a la pantalla anterior (ListasFragment)
            NavHostFragment.findNavController(TareasFragment.this).popBackStack();
        });

        // 6. Observar la lista de tareas del ViewModel
        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            // Actualizar el adaptador cuando los datos cambien
            adapter.setTasks(tasks);
        });

        // 7. Recibir y mostrar el Título de la Lista (lógica que ya teníamos)
        if (getArguments() != null) {
            String listTitle = getArguments().getString("listTitle", "Lista");
            tvListaTitulo.setText(listTitle);
        }
    }

    /**
     * Configura el RecyclerView, su LayoutManager y su Adaptador.
     */
    private void setupRecyclerView() {
        adapter = new TareasAdapter(this); // 'this' es el OnTaskListener
        recyclerViewTareas.setAdapter(adapter);
        recyclerViewTareas.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // --- Implementación de OnTaskListener ---

    /**
     * Se llama cuando el usuario toca un ítem (para "Cambiar Tarea").
     * @param position La posición del ítem.
     */
    @Override
    public void onTaskClick(int position) {
        String taskName = viewModel.getTasks().getValue().get(position);
        Log.d("TareasFragment", "Clic en CAMBIAR TAREA para: " + taskName);
        // TODO: Implementar navegación a una pantalla de "Editar Tarea"
    }

    /**
     * Se llama cuando el usuario destilda el CheckBox (para "Eliminar Tarea").
     * @param position La posición del ítem.
     */
    @Override
    public void onTaskDeleteClick(int position) {
        String taskName = viewModel.getTasks().getValue().get(position);
        Log.d("TareasFragment", "Clic en ELIMINAR TAREA para: " + taskName);
        // Llamar al ViewModel para que borre la tarea
        viewModel.deleteTask(position);
    }
}

