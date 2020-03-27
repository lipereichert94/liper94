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
import com.example.tccsimsim.project.adapter.ProdutoAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Produto;

import java.util.ArrayList;

public class Lista_Produto extends Fragment {


    View minhaView;
    private BDSQLiteHelper bd;
    ArrayList<Produto> listaproduto;
    private Button cadastra_produto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        minhaView = inflater.inflate(R.layout.layout_listar_produtos, container, false);

        cadastra_produto = (Button)minhaView.findViewById(R.id.button__listaproduto_Cadastraproduto);
        final RecyclerView recyclerView = (RecyclerView) minhaView.findViewById(R.id.recyclerView_ListaProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bd = new BDSQLiteHelper(getActivity());
        listaproduto = bd.getAllProduto();

        recyclerView.setAdapter(new ProdutoAdapter(listaproduto, R.layout.list_item_produto, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.d("----->", "POSICAO"+position);

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                      // ft.replace(R.id.conteudo_fragmento, new FragmentoPrimeiraTela().newInstance(position));
                        Log.d("----->", "No frame lista produto passou id produto" + listaproduto.get(position).getId());
                        Log.d("----->", "No frame lista produto passou o id estabelecimetno" + listaproduto.get(position).getId_estabelecimento());
                        ft.replace(R.id.conteudo_fragmento, new Cadastro_Produto().newInstance(listaproduto.get(position).getId(),listaproduto.get(position).getId_estabelecimento()));
                         ft.commit();

                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        Log.d("----->", "TESTE"+position);
                    //    DayTrade dt = listaDT.get(position);
                      //  Intent intent = new Intent(getActivity().getBaseContext(), FullScreenDT.class);
                     //   intent.putExtra("parametro", dt.getFoto());
                     //   startActivity(intent);

                    }
                })

        );

        cadastra_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Cadastro_Produto());
                ft.commit();
            }
        });

        return minhaView;


    }

}
