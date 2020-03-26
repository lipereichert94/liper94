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
import com.example.tccsimsim.project.adapter.UsuarioAdapter;
import com.example.tccsimsim.project.banco.BDSQLiteHelper;
import com.example.tccsimsim.project.model.Estabelecimento;
import com.example.tccsimsim.project.model.Usuario;

import java.util.ArrayList;

public class Lista_Estabelecimento extends Fragment {


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
        listaestabelecimento = bd.getAllEstabelecimentos();

        recyclerView.setAdapter(new EstabelecimentoAdapter(listaestabelecimento, R.layout.list_item_estabelecimento, getActivity().getApplicationContext()));
        recyclerView.addOnItemTouchListener(

                new RecyclerItemClickListener(getActivity(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.d("----->", "POSICAO"+position);

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                      // ft.replace(R.id.conteudo_fragmento, new FragmentoPrimeiraTela().newInstance(position));
                         ft.replace(R.id.conteudo_fragmento, new Cadastro_Estabelecimento().newInstance(listaestabelecimento.get(position).getId()));
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

        cadastra_estabelecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.conteudo_fragmento, new Cadastro_Estabelecimento());
                ft.commit();
            }
        });

        return minhaView;


    }

}
