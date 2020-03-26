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
import com.example.tccsimsim.project.model.Estabelecimento;

import java.util.List;

public class EstabelecimentoAdapter extends RecyclerView.Adapter<EstabelecimentoAdapter.DatumViewHolder> {
    private List<Estabelecimento> estabelecimentos;
    private int rowLayout;
    private Context context;
    public static class DatumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout estabelecimentoLayout;
        TextView nome;
        public DatumViewHolder(View v) {
            super(v);
            estabelecimentoLayout = (LinearLayout) v.findViewById(R.id.estabelecimento_layout);
            nome = (TextView) v.findViewById(R.id.nome_estabelecimento_lista);
        }
    }
    public EstabelecimentoAdapter(List<Estabelecimento> estabelecimentos, int rowLayout, Context context) {
        this.estabelecimentos = estabelecimentos;
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
        holder.nome.setText(estabelecimentos.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return estabelecimentos.size();
    }



}
