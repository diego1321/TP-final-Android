package com.example.tp_final_android.ui.tareas;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.tp_final_android.MyApplication;

/**
 * Fábrica para crear TareasViewModel.
 * Obtiene el TareasRepository desde MyApplication.
 */
public class TareasViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public TareasViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TareasViewModel.class)) {
            // Obtiene el repositorio desde la clase Application
            TareasRepository repository = ((MyApplication) application).tareasRepository;
            // Pasa el repositorio al constructor del ViewModel
            return (T) new TareasViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}