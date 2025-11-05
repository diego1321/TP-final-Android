package com.example.tp_final_android.ui.tareas;

import android.Manifest;
import android.app.Application; // Importar
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// --- CORRECCIÓN: Usar '.' en lugar de ':' ---
import android.widget.Button;
<<<<<<< HEAD
import android.widget.EditText; // Importar EditText
import android.widget.TextView; // Importar TextView
=======
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> e3e3358 (ultimo commit de funcionalidad)

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
<<<<<<< HEAD
import androidx.fragment.app.Fragment;
=======
import androidx.fragment.app.Fragment; // <-- IMPORT FALTANTE
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tp_final_android.R;

<<<<<<< HEAD
/**
 * Fragmento para Crear o Editar una Tarea.
 * Reutiliza el mismo layout para ambas acciones.
 * Determina el modo (Crear vs Editar) basado en los argumentos recibidos.
 */
=======
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

>>>>>>> e3e3358 (ultimo commit de funcionalidad)
public class CrearTareaFragment extends Fragment {

    private TareasViewModel viewModel;
    private EditText etNombreTarea;
<<<<<<< HEAD
    private TextView tvTituloCrearTarea; // Para cambiar el título (opcional)

    // Variables para guardar los argumentos
    private int currentTaskPosition = -1; // Default -1 (modo Crear)
    private String existingTaskText = null;
=======
    private TextView tvTituloCrearTarea;
    private Button btnAnadirFoto;
    private ImageView ivFotoPreview;

    // --- Variables para la Cámara ---
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private String currentImagePath = null;

    // --- Variables para argumentos ---
    private int currentTaskId = -1;
    private String existingTaskText = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Inicializar el Launcher para PEDIR PERMISO
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        launchCamera();
                    } else {
                        Toast.makeText(getContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // 2. Inicializar el Launcher para OBTENER LA FOTO
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        if (extras != null) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            if (imageBitmap != null) {
                                ivFotoPreview.setImageBitmap(imageBitmap);
                                ivFotoPreview.setVisibility(View.VISIBLE);
                                currentImagePath = saveImageToInternalStorage(imageBitmap);
                                Log.d("CrearTareaFragment", "Imagen guardada en: " + currentImagePath);
                            }
                        }
                    }
                }
        );
    }
>>>>>>> e3e3358 (ultimo commit de funcionalidad)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crear_tarea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

<<<<<<< HEAD
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
=======
        // 1. Obtener el ViewModel COMPARTIDO (usando la Factory)
        Application application = requireActivity().getApplication();
        TareasViewModelFactory factory = new TareasViewModelFactory(application);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(TareasViewModel.class);

        // --- Encontrar Vistas ---
        etNombreTarea = view.findViewById(R.id.etNombreTarea);
        tvTituloCrearTarea = view.findViewById(R.id.tvAgregarTareaTitulo);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);
        Button btnDeshacer = view.findViewById(R.id.btnDeshacer);
        btnAnadirFoto = view.findViewById(R.id.btnAnadirFoto);
        ivFotoPreview = view.findViewById(R.id.ivFotoPreview);

        // --- Recibir Argumentos ---
        if (getArguments() != null) {
            currentTaskId = getArguments().getInt("taskId", -1);
            existingTaskText = getArguments().getString("taskText");
            currentImagePath = getArguments().getString("taskImagePath");
        }

        // --- Configurar Modo (Crear vs Editar) ---
        if (currentTaskId != -1) {
            tvTituloCrearTarea.setText("Modificar Tarea");
            etNombreTarea.setText(existingTaskText);

            if (currentImagePath != null) {
                Bitmap img = loadImageFromStorage(currentImagePath);
                if (img != null) {
                    ivFotoPreview.setImageBitmap(img);
                    ivFotoPreview.setVisibility(View.VISIBLE);
                }
            }
        } else {
            tvTituloCrearTarea.setText("Agregar Tarea");
        }

        // --- Configurar Listeners de Botones ---

        btnAnadirFoto.setOnClickListener(v -> {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        });

        btnGuardar.setOnClickListener(v -> {
            String taskName = etNombreTarea.getText().toString().trim();
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
            if (taskName.isEmpty()) {
                etNombreTarea.setError("El nombre no puede estar vacío");
                return;
            }

<<<<<<< HEAD
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
=======
            if (currentTaskId == -1) {
                viewModel.addTask(taskName, currentImagePath);
            } else {
                viewModel.updateTask(currentTaskId, taskName, currentImagePath);
            }

            NavHostFragment.findNavController(CrearTareaFragment.this).popBackStack();
        });

>>>>>>> e3e3358 (ultimo commit de funcionalidad)
        btnDeshacer.setOnClickListener(v -> {
            NavHostFragment.findNavController(CrearTareaFragment.this).popBackStack();
        });
    }
<<<<<<< HEAD
}

=======

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    private String saveImageToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getContext().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String fileName = "IMG_" + UUID.randomUUID().toString() + ".jpg";
        File mypath = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 85, fos);
        } catch (Exception e) {
            Log.e("CrearTareaFragment", "Error al guardar imagen", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    private Bitmap loadImageFromStorage(String path) {
        try {
            File f = new File(path);
            return BitmapFactory.decodeFile(f.getAbsolutePath());
        } catch (Exception e) {
            Log.e("CrearTareaFragment", "Error al cargar imagen", e);
            return null;
        }
    }
}
>>>>>>> e3e3358 (ultimo commit de funcionalidad)
