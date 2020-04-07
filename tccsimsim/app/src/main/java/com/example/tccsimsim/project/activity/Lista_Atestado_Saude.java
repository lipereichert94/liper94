package com.example.tccsimsim.project.activity;

import android.os.Bundle;
import android.util.Log;
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
import com.example.tccsimsim.project.adapter.ProdutoAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.Produto;

import java.util.ArrayList;

public class Lista_Atestado_Saude extends Fragment {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<Atestado_Saude> listaatestadosaude;
    private Button cadastra_atestado_saude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_listar_atestado_saude, container, false);

        cadastra_atestado_saude = (Button)minhaView.findViewById(R.id.button__listaatestadodeSaude_CadastraAtestadodeSaude);
        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaAtestadosdeSaude);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        listaatestadosaude = bd.getAllAtestadoSaude();

        recyclerView.setAdapter(new AtestadoSaudeAdapter(listaatestadosaude, R.layout.list_item_atestado_saude, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Cadastro_Atestado_Saude().newInstance(listaatestadosaude.get(position).getId(),listaatestadosaude.get(position).getEstabelecimento().getId()));
                        ft.commit();

                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })

        );

        cadastra_atestado_saude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Cadastro_Atestado_Saude());
                ft.commit();
            }
        });

        return minhaView;


    }

}
