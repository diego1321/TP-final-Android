package com.example.tp_final_android.ui.listas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.tp_final_android.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragmento para la pantalla de "Crear Lista" (Tercera pantalla del Figma).
 */
public class CrearListaFragment extends Fragment {

    private ListViewModel viewModel;
    private TextInputEditText etNombreLista;
    private Button btnGuardar;
    private Button btnDeshacer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout que creamos en el paso 1
        return inflater.inflate(R.layout.fragment_crear_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Inicializar Vistas
        etNombreLista = view.findViewById(R.id.etNombreLista);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnDeshacer = view.findViewById(R.id.btnDeshacer);

        // 2. Obtener el ViewModel COMPARTIDO
        // Usamos requireActivity() para que sea el mismo ViewModel que usa ListasFragment
        // (Esto requiere que ListasFragment también use requireActivity(), lo haremos luego)
        viewModel = new ViewModelProvider(requireActivity()).get(ListViewModel.class);

        // 3. Listener del botón Guardar
        btnGuardar.setOnClickListener(v -> {
            String listName = etNombreLista.getText().toString().trim();

            // Validar que el texto no esté vacío
            if (!listName.isEmpty()) {
                // Llamar al método del ViewModel para añadir la lista
                viewModel.addList(listName);

                // Volver a la pantalla anterior
                NavHostFragment.findNavController(CrearListaFragment.this).popBackStack();
            } else {
                // Opcional: Mostrar un error si el campo está vacío
                etNombreLista.setError("El nombre no puede estar vacío");
            }
        });

        // 4. Listener del botón Deshacer
        btnDeshacer.setOnClickListener(v -> {
            // Simplemente volver a la pantalla anterior
            NavHostFragment.findNavController(CrearListaFragment.this).popBackStack();
        });
    }
}
