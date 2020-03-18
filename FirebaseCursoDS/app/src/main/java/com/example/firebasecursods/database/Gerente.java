package com.example.firebasecursods.database;

import android.os.Parcel;
import android.os.Parcelable;



public class Gerente implements Parcelable {

    private String id;
    private String nome;
    private int idade;

    public Gerente() {
    }

    public Gerente( String nome, int idade ) {
        this.nome = nome;
        this.idade = idade;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    }

    protected Gerente(Parcel in) {
        this.id = in.readString();
        this.nome = in.readString();
        this.idade = in.readInt();

    }

    public static final Parcelable.Creator<Gerente> CREATOR = new Parcelable.Creator<Gerente>() {
        @Override
        public Gerente createFromParcel(Parcel source) {
            return new Gerente(source);
        }

        @Override
        public Gerente[] newArray(int size) {
            return new Gerente[size];
        }
    };

}
