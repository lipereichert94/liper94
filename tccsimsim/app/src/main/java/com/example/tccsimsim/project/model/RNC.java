package com.example.tccsimsim.project.model;

public class RNC {
    private int id;
    private String dt_inspecao;
    private String descricao;
    private String dt_verificacao;
    private String situacao;
    private String url_imagem;
    private Estabelecimento estabelecimento;

    public RNC(int id, String dt_inspecao, String descricao, String dt_verificacao, String situacao, String url_imagem, Estabelecimento estabelecimento) {
        this.id = id;
        this.dt_inspecao = dt_inspecao;
        this.descricao = descricao;
        this.dt_verificacao = dt_verificacao;
        this.situacao = situacao;
        this.url_imagem = url_imagem;
        this.estabelecimento = estabelecimento;
    }

    public RNC() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDt_inspecao() {
        return dt_inspecao;
    }

    public void setDt_inspecao(String dt_inspecao) {
        this.dt_inspecao = dt_inspecao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDt_verificacao() {
        return dt_verificacao;
    }

    public void setDt_verificacao(String dt_verificacao) {
        this.dt_verificacao = dt_verificacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getUrl_imagem() {
        return url_imagem;
    }

    public void setUrl_imagem(String url_imagem) {
        this.url_imagem = url_imagem;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
