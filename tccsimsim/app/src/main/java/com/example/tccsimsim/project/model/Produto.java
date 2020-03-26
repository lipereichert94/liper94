package com.example.tccsimsim.project.model;

public class Produto {
    private Integer id;
    private String nome;
    private Integer id_estabelecimento;

    public Produto(Integer id, String nome, Integer id_estabelecimento) {
        this.id = id;
        this.nome = nome;
        this.id_estabelecimento = id_estabelecimento;
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

    public Integer getId_estabelecimento() {
        return id_estabelecimento;
    }

    public void setId_estabelecimento(Integer id_estabelecimento) {
        this.id_estabelecimento = id_estabelecimento;
    }

    public Produto() {
    }
}
