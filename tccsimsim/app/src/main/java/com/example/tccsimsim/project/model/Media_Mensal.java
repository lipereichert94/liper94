package com.example.tccsimsim.project.model;

public class Media_Mensal {
    private int id;
    private Produto produto;
    private String dt_media_mensal;
    private int quantidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getDt_media_mensal() {
        return dt_media_mensal;
    }

    public void setDt_media_mensal(String dt_media_mensal) {
        this.dt_media_mensal = dt_media_mensal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Media_Mensal(int id, Produto produto, String dt_media_mensal, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.dt_media_mensal = dt_media_mensal;
        this.quantidade = quantidade;
    }

    public Media_Mensal() {
    }
}
