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
import com.example.tccsimsim.project.adapter.AtestadoSaudeAdapter;
import com.example.tccsimsim.project.adapter.RNCAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.RNC;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.util.ArrayList;

public class Lista_RNC extends Fragment {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<RNC> listarnc;
    private Button cadastra_rnc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_listar_rnc, container, false);

        cadastra_rnc = (Button)minhaView.findViewById(R.id.button__listarRNC_CadastraRNC);
        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaRNC);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView permissao_usuario = (TextView) headerView.findViewById(R.id.permissaousuariologado);
        try {
            listarnc = bd.getAllRNC();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(new RNCAdapter(listarnc, R.layout.list_item_rnc, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(permissao_usuario.getText().toString().equals("rw")) {
                            // do whatever
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Cadastro_RNC().newInstance(listarnc.get(position).getId(),listarnc.get(position).getEstabelecimento().getId()));
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

        cadastra_rnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(permissao_usuario.getText().toString().equals("rw")) {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.conteudo_fragmento, new Cadastro_RNC());
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
