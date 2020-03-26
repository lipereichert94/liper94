package com.example.tccsimsim.project.model;

public class Estabelecimento {

    private Integer id;
    private String nome;

    public Estabelecimento(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Estabelecimento() {

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
}
