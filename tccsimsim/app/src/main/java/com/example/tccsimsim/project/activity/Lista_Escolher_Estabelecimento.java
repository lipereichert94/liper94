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
import com.example.tccsimsim.project.adapter.EstabelecimentoAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;

import java.util.ArrayList;

public class Lista_Escolher_Estabelecimento extends Fragment {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<Estabelecimento> listaestabelecimento;
    private int id_produto = 0;
    private int id_atestado_saude = 0;
    private int id_rnc = 0;
    private String origem = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_escolher_estabelecimento, container, false);

        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaEscolhaEstabelecimentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        listaestabelecimento = bd.getAllEstabelecimentos();
        readBundle(getArguments());

        recyclerView.setAdapter(new EstabelecimentoAdapter(listaestabelecimento, R.layout.list_item_estabelecimento, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        if(origem.equals("atestado_saude")){
                            ft.replace(R.id.conteudo_fragmento, new Cadastro_Atestado_Saude().newInstance(id_atestado_saude, listaestabelecimento.get(position).getId()));
                        }else if (origem.equals("cadastro_produto")) {
                            ft.replace(R.id.conteudo_fragmento, new Cadastro_Produto().newInstance(id_produto, listaestabelecimento.get(position).getId()));
                        }
                        else if (origem.equals("licenca_ambiental")) {
                            ft.replace(R.id.conteudo_fragmento, new Cadastro_Licenca_Ambiental().newInstance(id_produto, listaestabelecimento.get(position).getId()));
                        }
                        else if (origem.equals("RNC")) {
                            ft.replace(R.id.conteudo_fragmento, new Cadastro_RNC().newInstance(id_produto, listaestabelecimento.get(position).getId()));
                        }
                        ft.commit();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })

        );


        return minhaView;


    }

    public static Lista_Escolher_Estabelecimento newInstance(int id_produto, String origem,int id_atestado_saude) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_produto", id_produto);
        bundle.putString("origem", origem);
        bundle.putInt("id_atestado_saude", id_atestado_saude);
        Lista_Escolher_Estabelecimento fragment = new Lista_Escolher_Estabelecimento();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id_produto = bundle.getInt("id_produto");
            origem = bundle.getString("origem");
            id_atestado_saude = bundle.getInt("id_atestado_saude");
            id_rnc = bundle.getInt("RNC");
        }
    }
}
