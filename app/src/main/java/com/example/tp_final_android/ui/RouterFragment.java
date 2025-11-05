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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tp_final_android.R;

public class RouterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new View(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireActivity().getSharedPreferences(OnboardingFragment.PREFS_NAME, Context.MODE_PRIVATE);
        boolean hasSeenOnboarding = prefs.getBoolean(OnboardingFragment.KEY_ONBOARDING_COMPLETADO, false);

        NavController navController = NavHostFragment.findNavController(this);

        if (hasSeenOnboarding) {
            navController.navigate(R.id.action_routerFragment_to_listasFragment);
        } else {
            navController.navigate(R.id.action_routerFragment_to_onboardingFragment);
        }
    }
}