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
import com.example.tccsimsim.project.model.Licenca_Ambiental;
import com.example.tccsimsim.project.model.Media_Mensal;

import java.util.List;

public class MediaMensalAdapter extends RecyclerView.Adapter<MediaMensalAdapter.DatumViewHolder> {
    private List<Media_Mensal> mediasmensais;
    private int rowLayout;
    private Context context;
    public static class DatumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mediasmensais;
        TextView nome_estabelecimento,dt_media_mensal,nome_produto,quantidade_media_mensal;
        public DatumViewHolder(View v) {
            super(v);
            mediasmensais = (LinearLayout) v.findViewById(R.id.media_mensal_layout);
            nome_estabelecimento = (TextView) v.findViewById(R.id.estabelecimento_media_mensal_lista);
            dt_media_mensal = (TextView)v.findViewById(R.id.dt_media_mensal_lista);
            nome_produto = (TextView)v.findViewById(R.id.produto_media_mensal_lista);
            quantidade_media_mensal = (TextView)v.findViewById(R.id.quantidade_media_mensal_lista);

        }
    }
    public MediaMensalAdapter(List<Media_Mensal> mediasmensais, int rowLayout, Context context) {
        this.mediasmensais = mediasmensais ;
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

        holder.nome_estabelecimento.setText(""+mediasmensais.get(position).getProduto().getEstabelecimento().getNome());
        holder.nome_produto.setText(""+mediasmensais.get(position).getProduto().getNome());
        holder.dt_media_mensal.setText(""+mediasmensais.get(position).getDt_media_mensal());
        holder.quantidade_media_mensal.setText(""+mediasmensais.get(position).getQuantidade());

    }

    @Override
    public int getItemCount() {
        return mediasmensais.size();
    }



}
