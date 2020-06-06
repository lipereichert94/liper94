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
        TextView nome,nome_fantasia,classificacao,cnpj,inscricao_estadual,inscricao_municipal,endereco,endereco_eletronico,dt_registro,telefone;
        public DatumViewHolder(View v) {
            super(v);
            estabelecimentoLayout = (LinearLayout) v.findViewById(R.id.estabelecimento_layout);
            nome = (TextView) v.findViewById(R.id.nome_estabelecimento_lista);
            nome_fantasia = (TextView) v.findViewById(R.id.nome_fantasia_estabelecimento_lista);
            classificacao = (TextView)v.findViewById(R.id.classificacao_estabelecimento_lista);
            cnpj = (TextView) v.findViewById(R.id.cnpj_estabelecimento_lista);
            inscricao_estadual = (TextView) v.findViewById(R.id.inscricao_estadual_estabelecimento_lista);
            inscricao_municipal = (TextView) v.findViewById(R.id.inscricao_municipal_estabelecimento_lista);
            endereco = (TextView) v.findViewById(R.id.endereco_estabelecimento_lista);
            endereco_eletronico =(TextView) v.findViewById(R.id.endereco_eletronico_estabelecimento_lista);
            dt_registro = (TextView) v.findViewById(R.id.dt_registro_estabelecimento_lista);
            telefone = (TextView) v.findViewById(R.id.fone_estabelecimento_lista);
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
        holder.nome.setText(""+estabelecimentos.get(position).getNome());
        holder.nome_fantasia.setText(""+estabelecimentos.get(position).getNome_fantasia());
        holder.classificacao.setText(""+estabelecimentos.get(position).getClassificacao());
        holder.cnpj.setText(""+estabelecimentos.get(position).getCnpj());
        holder.inscricao_estadual.setText(""+estabelecimentos.get(position).getInscricao_estadual());
        holder.inscricao_municipal.setText(""+estabelecimentos.get(position).getInscricao_municipal());
        holder.endereco.setText(""+estabelecimentos.get(position).getEndereco());
        holder.endereco_eletronico.setText(""+estabelecimentos.get(position).getEndereco_eletronico());
        holder.dt_registro.setText(""+estabelecimentos.get(position).getDt_registro());
        holder.telefone.setText(""+estabelecimentos.get(position).getFone());

    }

    @Override
    public int getItemCount() {
        return estabelecimentos.size();
    }



}
