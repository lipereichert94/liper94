package com.example.tccsimsim.project.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.model.AI;
import com.example.tccsimsim.project.model.RNC;

import java.util.List;

public class AIAdapter extends RecyclerView.Adapter<AIAdapter.DatumViewHolder> {
    private List<AI> ai;
    private int rowLayout;
    private Context context;
    public static class DatumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ailayout;
        TextView nome_estabelecimento,dt_ai,infracao,penalidade,situacao;
        public DatumViewHolder(View v) {
            super(v);
            ailayout = (LinearLayout) v.findViewById(R.id.ai_layout);
            nome_estabelecimento = (TextView) v.findViewById(R.id.estabelecimento_ai_lista);
            dt_ai = (TextView)v.findViewById(R.id.dt_ai_lista);
            infracao = (TextView)v.findViewById(R.id.infracao_ai_lista);
            penalidade = (TextView)v.findViewById(R.id.penalidade_ai_lista);
            situacao = (TextView)v.findViewById(R.id.situacao_ai_lista);
        }
    }
    public AIAdapter(List<AI> ai, int rowLayout, Context context) {
        this.ai = ai ;
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

        holder.nome_estabelecimento.setText(""+ai.get(position).getEstabelecimento().getNome());
        holder.dt_ai.setText(ai.get(position).getDt_ai());
        holder.infracao.setText(ai.get(position).getInfracao_ai());
        holder.penalidade.setText(ai.get(position).getPenalidade_ai());
        holder.situacao.setText(""+ai.get(position).getSituacao_ai());

    }

    @Override
    public int getItemCount() {
        return ai.size();
    }



}
