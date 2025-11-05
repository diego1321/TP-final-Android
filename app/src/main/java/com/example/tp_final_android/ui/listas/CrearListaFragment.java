package com.example.tp_final_android.ui.listas;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment; // <-- IMPORT FALTANTE
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.tp_final_android.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragmento para la pantalla de "Crear Lista".
 */
public class CrearListaFragment extends Fragment {

    private ListViewModel viewModel;
    private TextInputEditText etNombreLista;
    private Button btnGuardar;
    private Button btnDeshacer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crear_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Inicializar Vistas
        etNombreLista = view.findViewById(R.id.etNombreLista);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnDeshacer = view.findViewById(R.id.btnDeshacer);

        // 2. Obtener el ViewModel COMPARTIDO (usando la Factory)
        Application application = requireActivity().getApplication();
        ListasViewModelFactory factory = new ListasViewModelFactory(application);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(ListViewModel.class);

        // 3. Listener del botón Guardar
        btnGuardar.setOnClickListener(v -> {
            String listName = etNombreLista.getText().toString().trim();

            if (!listName.isEmpty()) {
                viewModel.addList(listName);
                NavHostFragment.findNavController(CrearListaFragment.this).popBackStack();
            } else {
                etNombreLista.setError("El nombre no puede estar vacío");
            }
        });

        // 4. Listener del botón Deshacer
        btnDeshacer.setOnClickListener(v -> {
            NavHostFragment.findNavController(CrearListaFragment.this).popBackStack();
        });
    }
}