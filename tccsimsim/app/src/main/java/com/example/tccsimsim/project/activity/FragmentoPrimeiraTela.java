package com.example.tccsimsim.project.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tccsimsim.R;

public class FragmentoPrimeiraTela extends Fragment {


    View minhaView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_primeira_tela, container, false);
        return minhaView;


    }

}
