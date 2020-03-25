package com.example.tccsimsim.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tccsimsim.R;
import com.example.tccsimsim.project.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.DatumViewHolder> {
    private List<Usuario> user;
    private int rowLayout;
    private Context context;
    public static class DatumViewHolder extends RecyclerView.ViewHolder {
        LinearLayout userLayout;
        TextView nome;
        TextView login;
        public DatumViewHolder(View v) {
            super(v);
            userLayout = (LinearLayout) v.findViewById(R.id.user_layout);
            nome = (TextView) v.findViewById(R.id.nome_usuario_lista);
            login = (TextView) v.findViewById(R.id.login_usuario_lista);
        }
    }
    public UsuarioAdapter(List<Usuario> users, int rowLayout, Context context) {
        this.user = users;
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
        holder.nome.setText(user.get(position).getNome());
        holder.login.setText(user.get(position).getLogin());
    }

    @Override
    public int getItemCount() {
        return user.size();
    }



}
