package com.example.tccsimsim.project.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.tccsimsim.R;


public class FragmentoSegundaTela extends Fragment
{
    View minhaView;
    private static final String TAG = FragmentoSegundaTela.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        minhaView = inflater.inflate(R.layout.layout_segunda_tela, container, false);
        return minhaView;
    }
}
