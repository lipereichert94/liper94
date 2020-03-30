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
import com.example.tccsimsim.project.model.Atestado_Saude;
import com.example.tccsimsim.project.model.Produto;

import java.util.List;

public class AtestadoSaudeAdapter extends RecyclerView.Adapter<AtestadoSaudeAdapter.DatumViewHolder> {
    private List<Atestado_Saude> atestadosdesaude;
    private int rowLayout;
    private Context context;
    public static class DatumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout atestadosaudelayout;
        TextView nome_estabelecimento,dt_registro,dt_validade;
        public DatumViewHolder(View v) {
            super(v);
            atestadosaudelayout = (LinearLayout) v.findViewById(R.id.atestado_saude_layout);
            nome_estabelecimento = (TextView) v.findViewById(R.id.estabelecimento_atestado_saude_lista);
            dt_registro = (TextView)v.findViewById(R.id.dt_registro_atestado_saude_lista);
            dt_validade = (TextView)v.findViewById(R.id.dt_validade_atestado_saude_lista);

        }
    }
    public AtestadoSaudeAdapter(List<Atestado_Saude> atestadosdesaude, int rowLayout, Context context) {
        this.atestadosdesaude = atestadosdesaude ;
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

        holder.nome_estabelecimento.setText(""+atestadosdesaude.get(position).getEstabelecimento().getNome());
        holder.dt_registro.setText(atestadosdesaude.get(position).getDt_registro().toString());
        holder.dt_validade.setText(atestadosdesaude.get(position).getDt_validade().toString());
    }

    @Override
    public int getItemCount() {
        return atestadosdesaude.size();
    }



}
