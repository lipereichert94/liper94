package com.example.tccsimsim.project.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.tccsimsim.project.adapter.UsuarioAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Usuario;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Lista_Usuario extends Fragment {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<Usuario> listauser;
    private Button cadastra_usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_listar_usuarios, container, false);

        cadastra_usuario = (Button)minhaView.findViewById(R.id.button__listausuario_CadastrarUsuario);
        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        listauser = bd.getAllUsuarios();

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView permissao_usuario = (TextView) headerView.findViewById(R.id.permissaousuariologado);

        recyclerView.setAdapter(new UsuarioAdapter(listauser, R.layout.list_item_user, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(permissao_usuario.getText().toString().equals("rw")) {
                            // do whatever
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                         ft.replace(R.id.conteudo_fragmento, new Cadastro_Usuario().newInstance(listauser.get(position).getId()));
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

        cadastra_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Cadastro_Usuario());
                ft.commit();
            }
        });

        return minhaView;


    }

}
