package com.example.tccsimsim.project.activity;

import android.os.Bundle;
import android.util.Log;
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
import com.example.tccsimsim.project.Utils.RecyclerItemClickListener;
import com.example.tccsimsim.project.adapter.EstabelecimentoAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.util.ArrayList;

public class Lista_Estabelecimento extends Fragment  {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<Estabelecimento> listaestabelecimento;
    private Button cadastra_estabelecimento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_listar_estabelecimentos, container, false);

        cadastra_estabelecimento = (Button)minhaView.findViewById(R.id.button__listaestabelecimento_Cadastraestabelecimento);
        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaEstabelecimentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        try {
            listaestabelecimento = bd.getAllEstabelecimentos();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView permissao_usuario = (TextView) headerView.findViewById(R.id.permissaousuariologado);

        recyclerView.setAdapter(new EstabelecimentoAdapter(listaestabelecimento, R.layout.list_item_estabelecimento, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(permissao_usuario.getText().toString().equals("rw")) {
                            // do whatever
                            Log.d("----->", "permissao é:"+permissao_usuario.getText().toString());

                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.conteudo_fragmento, new Cadastro_Estabelecimento().newInstance(listaestabelecimento.get(position).getId()));
                            ft.commit();
                        }else{
                                Toast.makeText(getActivity(), "Você não permissão para alterar dados, favor contatar o administrador do sistema!",
                                        Toast.LENGTH_LONG).show();
                            }

                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })

        );

        cadastra_estabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissao_usuario.getText().toString().equals("rw")) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.conteudo_fragmento, new Cadastro_Estabelecimento()).addToBackStack(null);

                    ft.commit();
                }else{
                    Toast.makeText(getActivity(), "Você não permissão para alterar dados, favor contatar o administrador do sistema!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return minhaView;


    }

    }

