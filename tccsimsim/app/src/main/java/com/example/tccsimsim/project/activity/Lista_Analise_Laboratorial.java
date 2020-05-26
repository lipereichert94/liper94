package com.example.tccsimsim.project.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.adapter.AIAdapter;
import com.example.tccsimsim.project.adapter.AnaliseLaboratorialAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.AI;
import com.example.tccsimsim.project.model.Analise_Laboratorial;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.util.ArrayList;

public class Lista_Analise_Laboratorial extends Fragment {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<Analise_Laboratorial> listaanaliselaboratorial;
    private Button cadastra_Analise_Laboratorial;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_listar_analise_laboratorial, container, false);

        cadastra_Analise_Laboratorial = (Button)minhaView.findViewById(R.id.button__listarAnalise_Laboratorial_CadastraAnalise_Laboratorial);
        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaAnaliseLaboratorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView permissao_usuario = (TextView) headerView.findViewById(R.id.permissaousuariologado);
        try {
            listaanaliselaboratorial = bd.getAllAnaliseLaboratorial();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(new AnaliseLaboratorialAdapter(listaanaliselaboratorial, R.layout.list_item_analise_laboratorial, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(permissao_usuario.getText().toString().equals("rw")) {
                            // do whatever
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Cadastro_Analise_Laboratorial().newInstance(listaanaliselaboratorial.get(position).getId(),listaanaliselaboratorial.get(position).getProduto().getId()));
                        ft.commit();
                        }else{
                            Toast.makeText(getActivity(), "Você não possui permissão para alterar dados, favor contatar o administrador do sistema!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })

        );

        cadastra_Analise_Laboratorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissao_usuario.getText().toString().equals("rw")) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.conteudo_fragmento, new Cadastro_Analise_Laboratorial());
                    ft.commit();
                }else{
                        Toast.makeText(getActivity(), "Você não possui permissão para alterar dados, favor contatar o administrador do sistema!",
                                Toast.LENGTH_LONG).show();
                    }
            }
        });

        return minhaView;


    }

}
