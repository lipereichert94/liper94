package com.example.tccsimsim.project.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.adapter.EstabelecimentoAdapter;
import com.example.tccsimsim.project.adapter.ProdutoAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Produto;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Lista_Escolher_Produto extends Fragment {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<Produto> listaproduto;
    private int id_media_mensal = 0;
    //private int id_atestado_saude = 0;
    private String origem = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_escolher_produto, container, false);

        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaEscolhaProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        listaproduto = bd.getAllProduto();
        readBundle(getArguments());
        recyclerView.setAdapter(new ProdutoAdapter(listaproduto, R.layout.list_item_produto, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                            // do whatever
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        if(origem.equals("media_mensal")){
                            ft.replace(R.id.conteudo_fragmento, new Cadastro_Media_Mensal().newInstance(id_media_mensal, listaproduto.get(position).getId()));
                        }else if (origem.equals("analise_laboratorial")) {
                            ft.replace(R.id.conteudo_fragmento, new Cadastro_Analise_Laboratorial().newInstance(id_media_mensal, listaproduto.get(position).getId()));
                        }
                        else if (origem.equals("licenca_ambiental")) {
                           // ft.replace(R.id.conteudo_fragmento, new Cadastro_Licenca_Ambiental().newInstance(id_produto, listaestabelecimento.get(position).getId()));
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

    public static Lista_Escolher_Produto newInstance(int id_media_mensal, String origem, int id_atestado_saude) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_media_mensal", id_media_mensal);
        bundle.putString("origem", origem);
        bundle.putInt("id_atestado_saude", id_atestado_saude);
        Lista_Escolher_Produto fragment = new Lista_Escolher_Produto();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id_media_mensal = bundle.getInt("id_media_mensal");
            origem = bundle.getString("origem");
           // id_atestado_saude = bundle.getInt("id_atestado_saude");
        }
    }
}
