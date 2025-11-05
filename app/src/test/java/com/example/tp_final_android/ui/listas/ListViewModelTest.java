package com.example.tp_final_android.ui.listas;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.example.tp_final_android.model.Lista;
import org.junit.Before; // <-- IMPORT FALTANTE
import org.junit.Rule; // <-- IMPORT FALTANTE
import org.junit.Test; // <-- IMPORT FALTANTE
import org.junit.runner.RunWith; // <-- IMPORT FALTANTE
import org.mockito.Mock; // <-- IMPORT FALTANTE
import org.mockito.junit.MockitoJUnitRunner; // <-- IMPORT FALTANTE
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.verify; // <-- IMPORT FALTANTE
import static org.mockito.Mockito.when; // <-- IMPORT FALTANTE

/**
 * Prueba Unitaria para ListViewModel.
 * Verifica que el ViewModel llama al Repositorio correctamente.
 */
@RunWith(MockitoJUnitRunner.class)
public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ListasRepository mockRepository;

    private ListViewModel viewModel;

    @Before
    public void setUp() {
        MutableLiveData<List<Lista>> fakeLiveData = new MutableLiveData<>();
        fakeLiveData.setValue(new ArrayList<>());

        when(mockRepository.getListas()).thenReturn(fakeLiveData);

        viewModel = new ListViewModel(mockRepository);
    }

    @Test
    public void testAddList_CallsRepository() {
        String listName = "Nueva Lista de Prueba";
        viewModel.addList(listName);
        verify(mockRepository).addList(listName);
    }

    @Test
    public void testRefreshListas_CallsRepository() {
        viewModel.refreshListas();
        verify(mockRepository).refreshListasFromServer();
    }
}