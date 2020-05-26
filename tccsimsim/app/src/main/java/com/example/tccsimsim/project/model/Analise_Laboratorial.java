package com.example.tccsimsim.project.model;

public class Analise_Laboratorial {
    private int id;
    private String dt_coleta;
    private String situacao_coleta;
    private String notificacao;
    private String dt_nova_coleta;
    private String situacao_nova_coleta;
    private Produto produto;

    public Analise_Laboratorial() {
    }

    public Analise_Laboratorial(int id, String dt_coleta, String situacao_coleta, String notificacao, String dt_nova_coleta, String situacao_nova_coleta, Produto produto) {
        this.id = id;
        this.dt_coleta = dt_coleta;
        this.situacao_coleta = situacao_coleta;
        this.notificacao = notificacao;
        this.dt_nova_coleta = dt_nova_coleta;
        this.situacao_nova_coleta = situacao_nova_coleta;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDt_coleta() {
        return dt_coleta;
    }

    public void setDt_coleta(String dt_coleta) {
        this.dt_coleta = dt_coleta;
    }

    public String getSituacao_coleta() {
        return situacao_coleta;
    }

    public void setSituacao_coleta(String situacao_coleta) {
        this.situacao_coleta = situacao_coleta;
    }

    public String getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(String notificacao) {
        this.notificacao = notificacao;
    }

    public String getDt_nova_coleta() {
        return dt_nova_coleta;
    }

    public void setDt_nova_coleta(String dt_nova_coleta) {
        this.dt_nova_coleta = dt_nova_coleta;
    }

    public String getSituacao_nova_coleta() {
        return situacao_nova_coleta;
    }

    public void setSituacao_nova_coleta(String situacao_nova_coleta) {
        this.situacao_nova_coleta = situacao_nova_coleta;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
