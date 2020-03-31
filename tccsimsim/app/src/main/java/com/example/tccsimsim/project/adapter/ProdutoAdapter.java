package com.example.tccsimsim.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.model.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.DatumViewHolder> {
    private List<Produto> produtos;
    private int rowLayout;
    private Context context;
    public static class DatumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout produtolayout;
        TextView nome,nome_empresa;
        public DatumViewHolder(View v) {
            super(v);
            produtolayout = (LinearLayout) v.findViewById(R.id.produto_layout);
            nome = (TextView) v.findViewById(R.id.nome_produto_lista);
            nome_empresa = (TextView) v.findViewById(R.id.nome_estabelecimento_produto_lista);
        }
    }
    public ProdutoAdapter(List<Produto> produtos, int rowLayout, Context context) {
        this.produtos = produtos;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public DatumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new DatumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatumViewHolder holder, int position) {


        holder.nome.setText(produtos.get(position).getNome());
        holder.nome_empresa.setText(""+produtos.get(position).getEstabelecimento().getNome());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }



}
