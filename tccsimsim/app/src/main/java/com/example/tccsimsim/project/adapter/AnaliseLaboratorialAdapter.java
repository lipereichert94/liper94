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
import com.example.tccsimsim.project.model.AI;
import com.example.tccsimsim.project.model.Analise_Laboratorial;

import java.util.List;

public class AnaliseLaboratorialAdapter extends RecyclerView.Adapter<AnaliseLaboratorialAdapter.DatumViewHolder> {
    private List<Analise_Laboratorial> analise_laboratorial;
    private int rowLayout;
    private Context context;
    public static class DatumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout analiselaboratoriallayout;
        TextView nome_produto,dt_coleta,situacao_coleta,notificacao,dt_nova_coleta,situacao_nova_coleta;
        public DatumViewHolder(View v) {
            super(v);
            analiselaboratoriallayout = (LinearLayout) v.findViewById(R.id.analise_laboratorial_layout);
            nome_produto = (TextView) v.findViewById(R.id.produto_analise_laboratorial_lista);
            dt_coleta = (TextView)v.findViewById(R.id.dt_coleta_analise_laboratorial_lista);
            situacao_coleta = (TextView)v.findViewById(R.id.situacao_coleta_analise_laboratorial_lista);
            notificacao = (TextView)v.findViewById(R.id.notificacao_analise_laboratorial_lista);
            dt_nova_coleta = (TextView)v.findViewById(R.id.dt_nova_coleta_analise_laboratorial_lista);
            situacao_nova_coleta = (TextView)v.findViewById(R.id.situacao_nova_coleta_analise_laboratorial_lista);

        }
    }
    public AnaliseLaboratorialAdapter(List<Analise_Laboratorial> analise_laboratorial, int rowLayout, Context context) {
        this.analise_laboratorial = analise_laboratorial ;
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

        holder.nome_produto.setText(""+analise_laboratorial.get(position).getProduto().getNome());
        holder.dt_coleta.setText(analise_laboratorial.get(position).getDt_coleta());
        holder.situacao_coleta.setText(analise_laboratorial.get(position).getSituacao_coleta());
        holder.notificacao.setText(analise_laboratorial.get(position).getNotificacao());
        holder.dt_nova_coleta.setText(""+analise_laboratorial.get(position).getDt_nova_coleta());
        holder.situacao_nova_coleta.setText(analise_laboratorial.get(position).getSituacao_nova_coleta());

    }

    @Override
    public int getItemCount() {
        return analise_laboratorial.size();
    }



}
