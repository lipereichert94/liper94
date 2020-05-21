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
import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.RNC;

import java.util.List;

public class RNCAdapter extends RecyclerView.Adapter<RNCAdapter.DatumViewHolder> {
    private List<RNC> rnc;
    private int rowLayout;
    private Context context;
    public static class DatumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout rnclayout;
        TextView nome_estabelecimento,dt_inspecao,descricao,dt_verificacao,situacao;
        ImageView imagem;
        public DatumViewHolder(View v) {
            super(v);
            rnclayout = (LinearLayout) v.findViewById(R.id.rnc_layout);
            nome_estabelecimento = (TextView) v.findViewById(R.id.estabelecimento_rnc_lista);
            dt_inspecao = (TextView)v.findViewById(R.id.dt_inspecao_rnc_lista);
            descricao = (TextView)v.findViewById(R.id.dt_descricao_rnc_lista);
            dt_verificacao = (TextView)v.findViewById(R.id.dt_verificacao_rnc_lista);
            situacao = (TextView)v.findViewById(R.id.situacao_rnc_lista);
            imagem = (ImageView) v.findViewById(R.id.imagem_rnc_lista);

        }
    }
    public RNCAdapter(List<RNC> rnc, int rowLayout, Context context) {
        this.rnc = rnc ;
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

        holder.nome_estabelecimento.setText(""+rnc.get(position).getEstabelecimento().getNome());
        holder.dt_inspecao.setText(rnc.get(position).getDt_inspecao());
        holder.descricao.setText(rnc.get(position).getDescricao());
        holder.dt_verificacao.setText(rnc.get(position).getDt_verificacao());
        holder.situacao.setText(rnc.get(position).getSituacao());
        holder.imagem.setImageBitmap(BitmapFactory.decodeFile(rnc.get(position).getUrl_imagem()));

    }

    @Override
    public int getItemCount() {
        return rnc.size();
    }



}
