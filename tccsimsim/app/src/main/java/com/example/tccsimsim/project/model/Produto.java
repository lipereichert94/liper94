package com.example.tccsimsim.project.model;

public class Produto {
    private Integer id;
    private String nome;
    private Estabelecimento estabelecimento;

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

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Produto(Integer id, String nome, Estabelecimento estabelecimento) {
        this.id = id;
        this.nome = nome;
        this.estabelecimento = estabelecimento;
    }

    public Produto() {
    }
}
