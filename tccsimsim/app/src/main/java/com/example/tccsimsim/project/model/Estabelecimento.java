package com.example.tccsimsim.project.model;

public class Estabelecimento {

    private Integer id;
    private String nome;
    private String nome_fantasia;
    private String classificacao;
    private int cnpj;
    private int inscricao_estadual;
    private int inscricao_municipal;
    private String endereco;
    private String endereco_eletronico;
    private String dt_registro;
    private String fone;

    public Estabelecimento() {
    }

    public Estabelecimento(Integer id, String nome, String nome_fantasia, String classificacao, int cnpj, int inscricao_estadual, int inscricao_municipal, String endereco, String endereco_eletronico, String dt_registro, String fone) {
        this.id = id;
        this.nome = nome;
        this.nome_fantasia = nome_fantasia;
        this.classificacao = classificacao;
        this.cnpj = cnpj;
        this.inscricao_estadual = inscricao_estadual;
        this.inscricao_municipal = inscricao_municipal;
        this.endereco = endereco;
        this.endereco_eletronico = endereco_eletronico;
        this.dt_registro = dt_registro;
        this.fone = fone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    public int getInscricao_estadual() {
        return inscricao_estadual;
    }

    public void setInscricao_estadual(int inscricao_estadual) {
        this.inscricao_estadual = inscricao_estadual;
    }

    public int getInscricao_municipal() {
        return inscricao_municipal;
    }

    public void setInscricao_municipal(int inscricao_municipal) {
        this.inscricao_municipal = inscricao_municipal;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco_eletronico() {
        return endereco_eletronico;
    }

    public void setEndereco_eletronico(String endereco_eletronico) {
        this.endereco_eletronico = endereco_eletronico;
    }

    public String getDt_registro() {
        return dt_registro;
    }

    public void setDt_registro(String dt_registro) {
        this.dt_registro = dt_registro;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }
}