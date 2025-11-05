package com.example.tp_final_android.ui.listas;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment; // <-- IMPORT FALTANTE
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp_final_android.R;
import com.example.tp_final_android.model.Lista;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

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

        // CAMBIO: Usar la Fábrica para obtener el ViewModel
        Application application = requireActivity().getApplication();
        ListasViewModelFactory factory = new ListasViewModelFactory(application);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(ListViewModel.class);

        // --- Encontrar Vistas ---
        themeSwitch = view.findViewById(R.id.themeSwitch);
        recyclerViewListas = view.findViewById(R.id.recyclerViewListas);

        // --- Listeners de Botones ---

        // Botón "Crear Lista"
        view.findViewById(R.id.btnCreateList).setOnClickListener(v -> {
            NavHostFragment.findNavController(ListasFragment.this)
                    .navigate(R.id.action_listasFragment_to_crearListaFragment);
        });

        // Botón "Actualizar API"
        view.findViewById(R.id.btnRefreshListas).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Actualizando listas desde la API...", Toast.LENGTH_SHORT).show();
            viewModel.refreshListas();
        });

        // Switch de Tema
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleDarkMode();
        });

        // --- Configurar RecyclerView y Observadores ---
        setupRecyclerView();

        viewModel.getListas().observe(getViewLifecycleOwner(), listas -> {
            adapter.setListas(listas);
        });

        viewModel.isDarkModeEnabled().observe(getViewLifecycleOwner(), isDark -> {
            themeSwitch.setChecked(isDark);
            int mode = isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
            AppCompatDelegate.setDefaultNightMode(mode);
        });
    }

    private void setupRecyclerView() {
        adapter = new ListasAdapter(this);
        recyclerViewListas.setAdapter(adapter);
        recyclerViewListas.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onListClick(int position) {
        List<Lista> currentList = viewModel.getListas().getValue();
        if (currentList != null && position < currentList.size()) {
            Lista listaSeleccionada = currentList.get(position);
            String listTitle = listaSeleccionada.getNombre();
            int listId = listaSeleccionada.getId();

            Log.d("ListasFragment", "Navegando a Tareas para: " + listTitle + " (ID: " + listId + ")");

            Bundle bundle = new Bundle();
            bundle.putString("listTitle", listTitle);
            bundle.putInt("listId", listId);

            NavHostFragment.findNavController(ListasFragment.this)
                    .navigate(R.id.action_listasFragment_to_tareasFragment, bundle);
        }
    }

    @Override
    public void onDeleteClick(int position) {
        Log.d("ListasFragment", "Eliminando lista en posición: " + position);
        viewModel.deleteList(position);
    }
}