package com.example.tp_final_android.ui.listas;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.tp_final_android.MyApplication;

/**
 * Fábrica para crear ListViewModel.
 * Obtiene el ListasRepository desde MyApplication.
 */
public class ListasViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;

    public ListasViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListViewModel.class)) {
            // Obtiene el repositorio desde la clase Application
            ListasRepository repository = ((MyApplication) application).listasRepository;
            // Pasa el repositorio al constructor del ViewModel
            return (T) new ListViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}