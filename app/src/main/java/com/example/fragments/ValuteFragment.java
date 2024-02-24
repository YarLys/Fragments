package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ValuteFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        ArrayList<Valute> valutes = new ArrayList<>();
        valutes.add(new Valute("rubl", "100", null));
        valutes.add(new Valute("rubl", "100", null));
        valutes.add(new Valute("rubl", "100", null));
        valutes.add(new Valute("rubl", "100", null));
        valutes.add(new Valute("rubl", "100", null));
        valutes.add(new Valute("rubl", "100", null));
        valutes.add(new Valute("rubl", "100", null));
        valutes.add(new Valute("rubl", "100", null));
        RecyclerView recyclerView = view.findViewById(R.id.RV);
        ValuteAdapter valuteAdapter = new ValuteAdapter(valutes);
        recyclerView.setAdapter(valuteAdapter);
        return view;
    }
}
