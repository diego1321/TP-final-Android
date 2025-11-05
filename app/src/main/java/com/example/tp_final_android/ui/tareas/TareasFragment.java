package com.example.tp_final_android.ui.tareas;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
<<<<<<< HEAD
// import android.widget.ImageButton; // Ya no se usa
=======
>>>>>>> e3e3358 (ultimo commit de funcionalidad)

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment; // <-- IMPORT FALTANTE
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
<<<<<<< HEAD

import com.example.tp_final_android.R;

/**
 * Fragmento para la pantalla de Tareas.
 * Muestra una lista de tareas (con RecyclerView) y permite CRUD.
 * Comparte el TareasViewModel con CrearTareaFragment.
 */
public class TareasFragment extends Fragment implements TareasAdapter.OnTaskListener { // 1. Implementar la interfaz
=======

import com.example.tp_final_android.R;
import com.example.tp_final_android.model.Tarea;

public class TareasFragment extends Fragment implements TareasAdapter.OnTaskListener {
>>>>>>> e3e3358 (ultimo commit de funcionalidad)

    private TareasViewModel viewModel;
    private TareasAdapter adapter;
    private RecyclerView recyclerViewTareas;
    private TextView tvListaTitulo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tareas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

<<<<<<< HEAD
        // --- Configuración del ViewModel ---
        // 2. Usar 'requireActivity()' para OBTENER EL VIEWMODEL COMPARTIDO
        viewModel = new ViewModelProvider(requireActivity()).get(TareasViewModel.class);
=======
        // --- Configuración del ViewModel (usando la Factory) ---
        Application application = requireActivity().getApplication();
        TareasViewModelFactory factory = new TareasViewModelFactory(application);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(TareasViewModel.class);
>>>>>>> e3e3358 (ultimo commit de funcionalidad)

        // --- Encontrar Vistas ---
        tvListaTitulo = view.findViewById(R.id.tvListaTitulo);
        recyclerViewTareas = view.findViewById(R.id.recyclerViewTareas);

        // --- Configurar Botones de Navegación ---
<<<<<<< HEAD

        // Botón "Agregar Tarea"
        view.findViewById(R.id.btnAgregarTarea).setOnClickListener(v -> {
            // Navega a CrearTareaFragment (sin argumentos, usará los default)
=======
        view.findViewById(R.id.btnAgregarTarea).setOnClickListener(v -> {
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
            NavHostFragment.findNavController(TareasFragment.this)
                    .navigate(R.id.action_tareasFragment_to_crearTareaFragment);
        });

<<<<<<< HEAD
        // Botón "Volver"
        view.findViewById(R.id.btnVolver).setOnClickListener(v -> {
            // 3. Usar popBackStack() para volver a la pantalla anterior (ListasFragment)
=======
        view.findViewById(R.id.btnVolver).setOnClickListener(v -> {
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
            NavHostFragment.findNavController(TareasFragment.this).popBackStack();
        });


<<<<<<< HEAD
        // --- Recibir Título de la Lista ---
        // 4. Recibir el título de la lista (pasado desde ListasFragment)
        if (getArguments() != null) {
            String listTitle = getArguments().getString("listTitle");
            if (listTitle != null) {
                tvListaTitulo.setText(listTitle);
            }
=======
        // --- Recibir Título e ID de la Lista ---
        if (getArguments() != null) {
            String listTitle = getArguments().getString("listTitle");
            int listId = getArguments().getInt("listId");
            tvListaTitulo.setText(listTitle);
            viewModel.loadTasksForList(listId);
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
        }

        // --- Configurar RecyclerView ---
        setupRecyclerView();

        // --- Observar LiveData ---
<<<<<<< HEAD
        // 5. Observar la lista de tareas del ViewModel
        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            // 6. Actualizar el adaptador cuando los datos cambien
=======
        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
            adapter.setTasks(tasks);
        });
    }

    private void setupRecyclerView() {
<<<<<<< HEAD
        // 7. Inicializar el adaptador pasándole 'this' (el Fragment) como listener
        adapter = new TareasAdapter(this);
        // 8. Asignar el adaptador al RecyclerView
=======
        adapter = new TareasAdapter(this);
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
        recyclerViewTareas.setAdapter(adapter);
        // 9. Asignar un LayoutManager
        recyclerViewTareas.setLayoutManager(new LinearLayoutManager(getContext()));
    }

<<<<<<< HEAD
    // --- Implementación de la interfaz OnTaskListener ---

    /**
     * Se llama desde el TareasAdapter cuando se destilda el CheckBox (BORRAR).
     * @param position La posición de la tarea a borrar.
     */
    @Override
    public void onTaskDeleteClick(int position) {
        // 10. Llamar al ViewModel para borrar la tarea
        viewModel.deleteTask(position);
    }

    /**
     * Se llama desde el TareasAdapter cuando se hace clic en el texto (MODIFICAR).
     * @param position La posición de la tarea a modificar.
     */
    @Override
    public void onTaskClick(int position) {
        // 11. Implementar navegación a "Modificar Tarea"

        // Obtener el texto actual de la tarea desde el ViewModel
        String currentTaskText = viewModel.getTask(position);

        if (currentTaskText != null) {
            Log.d("TareasFragment", "Navegando a EDITAR Tarea: " + currentTaskText);

            // Crear el Bundle para pasar los datos
            Bundle bundle = new Bundle();
            bundle.putInt("taskPosition", position); // Enviar la posición
            bundle.putString("taskText", currentTaskText); // Enviar el texto actual

            // Navegar a CrearTareaFragment USANDO la acción y pasando el Bundle
            NavHostFragment.findNavController(TareasFragment.this)
                    .navigate(R.id.action_tareasFragment_to_crearTareaFragment, bundle);
        }
    }
}
=======
    @Override
    public void onTaskDeleteClick(int position) {
        viewModel.deleteTask(position);
    }
>>>>>>> e3e3358 (ultimo commit de funcionalidad)

    @Override
    public void onTaskClick(int position) {
        Tarea tareaParaEditar = adapter.getTaskAt(position);

        if (tareaParaEditar != null) {
            Log.d("TareasFragment", "Editando Tarea: " + tareaParaEditar.getNombre());

            Bundle bundle = new Bundle();
            bundle.putInt("taskId", tareaParaEditar.getId());
            bundle.putString("taskText", tareaParaEditar.getNombre());
            bundle.putString("taskImagePath", tareaParaEditar.getImagePath());

            NavHostFragment.findNavController(TareasFragment.this)
                    .navigate(R.id.action_tareasFragment_to_crearTareaFragment, bundle);
        }
    }
}