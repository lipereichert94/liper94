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
import com.example.tccsimsim.project.adapter.AtestadoSaudeAdapter;
import com.example.tccsimsim.project.adapter.LicencaAmbientalAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.Licenca_Ambiental;

import java.util.ArrayList;

public class Lista_Licenca_Ambiental extends Fragment {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<Licenca_Ambiental> listalicencaambiental;
    private Button cadastra_licenca_abiental;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_listar_licenca_ambiental, container, false);

        cadastra_licenca_abiental = (Button)minhaView.findViewById(R.id.button__listaLicencaAmbiental_CadastraLicencaAmbiental);
        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaLicencasAmbientais);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        listalicencaambiental = bd.getAllLicencaAmbiental();

        recyclerView.setAdapter(new LicencaAmbientalAdapter(listalicencaambiental, R.layout.list_item_licenca_ambiental, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Cadastro_Licenca_Ambiental().newInstance(listalicencaambiental.get(position).getId(),listalicencaambiental.get(position).getEstabelecimento().getId()));
                        ft.commit();

                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })

        );

        cadastra_licenca_abiental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Cadastro_Licenca_Ambiental());
                ft.commit();
            }
        });

        return minhaView;


    }

}
