package com.example.tccsimsim.project.model;


import java.util.Date;

public class Atestado_Saude  {
    private Integer id;
    private Date dt_registro;
    private Date dt_validade;
    private Estabelecimento estabelecimento;

    public Atestado_Saude(Integer id, Date dt_registro, Date dt_validade, Estabelecimento estabelecimento) {
        this.id = id;
        this.dt_registro = dt_registro;
        this.dt_validade = dt_validade;
        this.estabelecimento = estabelecimento;
    }

    public Atestado_Saude() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDt_registro() {
        return dt_registro;
    }

    public void setDt_registro(Date dt_registro) {
        this.dt_registro = dt_registro;
    }

    public Date getDt_validade() {
        return dt_validade;
    }

    public void setDt_validade(Date dt_validade) {
        this.dt_validade = dt_validade;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
