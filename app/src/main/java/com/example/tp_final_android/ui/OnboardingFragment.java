package com.example.tp_final_android.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment; // <-- IMPORT FALTANTE
import androidx.navigation.fragment.NavHostFragment;

import com.example.tp_final_android.R;

public class OnboardingFragment extends Fragment {

    public static final String PREFS_NAME = "AppPrefs";
    public static final String KEY_ONBOARDING_COMPLETADO = "hasSeenOnboarding";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnComenzar).setOnClickListener(v -> {
            SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_ONBOARDING_COMPLETADO, true);
            editor.apply();

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_global_go_to_listasFragment);
        });
    }
}