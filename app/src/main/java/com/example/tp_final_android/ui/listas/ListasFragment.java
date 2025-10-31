package com.example.tp_final_android.ui.listas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_final_android.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

/**
 * Fragmento principal que muestra la lista de Check-Lists (Figma 1).
 * Implementa el patrón MVVM usando ListViewModel.java.
 * Usa un RecyclerView y comparte el ViewModel con CrearListaFragment.
 */
public class ListasFragment extends Fragment implements ListasAdapter.OnListListener {

    private ListViewModel viewModel;
    private SwitchMaterial themeSwitch;
    private RecyclerView recyclerViewListas;
    private ListasAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Inicialización del ViewModel (Compartido con la Activity)
        // Usamos requireActivity() para que CrearListaFragment pueda acceder a la misma instancia.
        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        // 2. Inicialización de Vistas
        themeSwitch = view.findViewById(R.id.themeSwitch);
        recyclerViewListas = view.findViewById(R.id.recyclerViewListas);

        // 3. Listener para el botón "Crear Lista" (ACTUALIZADO)
        view.findViewById(R.id.btnCreateList).setOnClickListener(v -> {
            // Navegar a la pantalla CrearListaFragment usando la acción del nav_graph
            NavHostFragment.findNavController(ListasFragment.this)
                    .navigate(R.id.action_listasFragment_to_crearListaFragment);
        });

        // 4. Listener para el Switch de tema claro/oscuro
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleDarkMode();
        });

        // 5. Configurar el RecyclerView
        setupRecyclerView();

        // 6. Observadores de LiveData
        viewModel.getListas().observe(getViewLifecycleOwner(), listas -> {
            adapter.setListas(listas);
        });

        viewModel.isDarkModeEnabled().observe(getViewLifecycleOwner(), isDark -> {
            themeSwitch.setChecked(isDark);
            int mode = isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
            AppCompatDelegate.setDefaultNightMode(mode);
        });
    }

    /**
     * Configura el RecyclerView, su LayoutManager y su Adaptador.
     */
    private void setupRecyclerView() {
        adapter = new ListasAdapter(this);
        recyclerViewListas.setAdapter(adapter);
        recyclerViewListas.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * Método de la interfaz OnListListener: Clic en un ítem de la lista.
     * Envía el título de la lista a TareasFragment.
     * @param position La posición del ítem en el que se hizo clic.
     */
    @Override
    public void onListClick(int position) {
        List<String> currentList = viewModel.getListas().getValue();
        if (currentList != null && position < currentList.size()) {
            String listTitle = currentList.get(position);
            Log.d("ListasFragment", "Navegando a Tareas para: " + listTitle);

            // Crear Bundle para pasar el título
            Bundle bundle = new Bundle();
            bundle.putString("listTitle", listTitle);

            // Navegar con el bundle
            NavHostFragment.findNavController(ListasFragment.this)
                    .navigate(R.id.action_listasFragment_to_tareasFragment, bundle);
        }
    }

    /**
     * Método de la interfaz OnListListener: Clic en el CheckBox (para borrar).
     * @param position La posición del ítem a borrar.
     */
    @Override
    public void onDeleteClick(int position) {
        List<String> currentList = viewModel.getListas().getValue();
        if (currentList != null && position < currentList.size()) {
            Log.d("ListasFragment", "Eliminando lista: " + currentList.get(position));
            // Llamar al ViewModel para que borre la lista
            viewModel.deleteList(position);
        }
    }
}

