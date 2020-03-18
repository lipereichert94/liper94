package com.example.firebasecursods.database_lista__funcionario_offline;

import android.os.Parcel;
import android.os.Parcelable;

public class FuncionarioOffLine implements Parcelable{

    private String id;
    private String nome;
    private int idade;
    private String urlimagem;
    private String id_empresa;



    public FuncionarioOffLine(){


    }



    public FuncionarioOffLine(String id, String nome, int idade, String urlimagem) {
        this.nome = nome;
        this.id = id;
        this.urlimagem = urlimagem;
        this.idade = idade;
    }

    public FuncionarioOffLine(String nome, int idade, String urlimagem) {
        this.nome = nome;
        this.urlimagem = urlimagem;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlimagem() {
        return urlimagem;
    }

    public void setUrlimagem(String urlimagem) {
        this.urlimagem = urlimagem;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nome);
        dest.writeInt(this.idade);
        dest.writeString(this.urlimagem);
        dest.writeString(this.id_empresa);
    }

    protected FuncionarioOffLine(Parcel in) {
        this.id = in.readString();
        this.nome = in.readString();
        this.idade = in.readInt();
        this.urlimagem = in.readString();
        this.id_empresa = in.readString();
    }

    public static final Creator<FuncionarioOffLine> CREATOR = new Creator<FuncionarioOffLine>() {
        @Override
        public FuncionarioOffLine createFromParcel(Parcel source) {
            return new FuncionarioOffLine(source);
        }

        @Override
        public FuncionarioOffLine[] newArray(int size) {
            return new FuncionarioOffLine[size];
        }
    };
}
