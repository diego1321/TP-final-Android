package com.example.tp_final_android;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**

 MainActivity: Actúa como la Activity host para el Navigation Component.

 La lógica de la aplicación se gestiona en Fragments, ViewModels y Repositorios (MVVM) en Java.

 NOTA: Esta versión ha sido convertida a Java para cumplir con el requisito de lenguaje

 ("La aplicación deberá ser desarrollada íntegramente en Java").
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// Implementación futura: aquí se gestionaría el flujo de Onboarding inicial
// al momento de la instalación inicial (Requisito PDF).

        super.onCreate(savedInstanceState);
        // Establece el layout que contiene el FragmentContainerView (activity_main.xml)
        setContentView(R.layout.activity_main);


    }
}