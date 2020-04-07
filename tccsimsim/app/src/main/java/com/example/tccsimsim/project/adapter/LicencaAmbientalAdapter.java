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
import com.example.tccsimsim.project.model.Licenca_Ambiental;

import java.util.List;

public class LicencaAmbientalAdapter extends RecyclerView.Adapter<LicencaAmbientalAdapter.DatumViewHolder> {
    private List<Licenca_Ambiental> licencasambientais;
    private int rowLayout;
    private Context context;
    public static class DatumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout licencasambientais;
        TextView nome_estabelecimento,dt_registro,dt_validade;
        public DatumViewHolder(View v) {
            super(v);
            licencasambientais = (LinearLayout) v.findViewById(R.id.licenca_ambiental_layout);
            nome_estabelecimento = (TextView) v.findViewById(R.id.estabelecimento_licenca_ambiental_lista);
            dt_registro = (TextView)v.findViewById(R.id.dt_registro_licenca_ambiental_lista);
            dt_validade = (TextView)v.findViewById(R.id.dt_validade_licenca_ambiental_lista);

        }
    }
    public LicencaAmbientalAdapter(List<Licenca_Ambiental> licencasambientais, int rowLayout, Context context) {
        this.licencasambientais = licencasambientais ;
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

        holder.nome_estabelecimento.setText(""+licencasambientais.get(position).getEstabelecimento().getNome());
        holder.dt_registro.setText(licencasambientais.get(position).getDt_registro());
        holder.dt_validade.setText(licencasambientais.get(position).getDt_validade());
    }

    @Override
    public int getItemCount() {
        return licencasambientais.size();
    }



}
