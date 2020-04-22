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
import com.example.tccsimsim.project.adapter.LicencaAmbientalAdapter;
import com.example.tccsimsim.project.adapter.MediaMensalAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Licenca_Ambiental;
import com.example.tccsimsim.project.model.Media_Mensal;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
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
        try {
            listamediamensal = bd.getAllMediaMensal();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView permissao_usuario = (TextView) headerView.findViewById(R.id.permissaousuariologado);
        recyclerView.setAdapter(new MediaMensalAdapter(listamediamensal, R.layout.list_item_media_mensal, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(permissao_usuario.getText().toString().equals("rw")) {

                            // do whatever
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.conteudo_fragmento, new Cadastro_Media_Mensal().newInstance(listamediamensal.get(position).getId(),listamediamensal.get(position).getProduto().getId()));
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
