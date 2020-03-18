package com.example.firebasecursods.database;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasecursods.R;

import java.util.List;

public class RecyclerView_ListaGerente extends RecyclerView.Adapter<RecyclerView_ListaGerente.ViewHolder> {

    private Context context;
    private List<Gerente> gerentes;
    private ClickGerente clickGerente;


    public RecyclerView_ListaGerente(Context context, List<Gerente> gerentes, ClickGerente clickGerente){

        this.context = context;
        this.gerentes = gerentes;
        this.clickGerente = clickGerente;


    }

    @NonNull
    @Override
    public RecyclerView_ListaGerente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.database_lista_gerente_conteudo_recycleview,parent,false);

        ViewHolder holder = new ViewHolder(view);

        view.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_ListaGerente.ViewHolder holder, int position) {
        final Gerente gerente = gerentes.get(position);


        holder.textView_Nome.setText(gerente.getNome());



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickGerente.ClickGerente(gerente);


            }
        });
    }

    @Override
    public int getItemCount() {

        return gerentes.size();
    }

    public interface ClickGerente{


        void ClickGerente(Gerente gerente);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        CardView cardView;
        TextView textView_Nome;

        public ViewHolder(View itemView) {
            super(itemView);


            cardView = (CardView)itemView.findViewById(R.id.cardView_ListaGerente_Item_CardView);
            textView_Nome = (TextView)itemView.findViewById(R.id.textView_ListaGerente_Item_Nome);

        }


    }
}
