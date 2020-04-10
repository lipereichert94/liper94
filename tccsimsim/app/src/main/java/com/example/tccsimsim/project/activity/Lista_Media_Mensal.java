package com.example.tccsimsim.project.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.adapter.LicencaAmbientalAdapter;
import com.example.tccsimsim.project.adapter.MediaMensalAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Licenca_Ambiental;
import com.example.tccsimsim.project.model.Media_Mensal;

import java.util.ArrayList;

public class Lista_Media_Mensal extends Fragment {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<Media_Mensal> listamediamensal;
    private Button cadastramediamensal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_listar_media_mensal, container, false);

        cadastramediamensal = (Button)minhaView.findViewById(R.id.button__listaMediaMensal_CadastraMediaMensal);
        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaMediasMensais);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        listamediamensal = bd.getAllMediaMensal();

        recyclerView.setAdapter(new MediaMensalAdapter(listamediamensal, R.layout.list_item_media_mensal, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Cadastro_Media_Mensal().newInstance(listamediamensal.get(position).getId(),listamediamensal.get(position).getProduto().getId()));
                        ft.commit();

                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })

        );

        cadastramediamensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Cadastro_Media_Mensal());
                ft.commit();
            }
        });

        return minhaView;


    }

}
