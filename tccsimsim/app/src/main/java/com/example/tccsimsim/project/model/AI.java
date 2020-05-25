package com.example.tccsimsim.project.model;

public class AI {
    private int id;
    private String dt_ai;
    private String infracao_ai;
    private String penalidade_ai;
    private String situacao_ai;
    private Estabelecimento estabelecimento;

    public AI() {
    }

    public AI(int id, String dt_ai, String infracao_ai, String penalidade_ai, String situacao_ai, Estabelecimento estabelecimento) {
        this.id = id;
        this.dt_ai = dt_ai;
        this.infracao_ai = infracao_ai;
        this.penalidade_ai = penalidade_ai;
        this.situacao_ai = situacao_ai;
        this.estabelecimento = estabelecimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDt_ai() {
        return dt_ai;
    }

    public void setDt_ai(String dt_ai) {
        this.dt_ai = dt_ai;
    }

    public String getInfracao_ai() {
        return infracao_ai;
    }

    public void setInfracao_ai(String infracao_ai) {
        this.infracao_ai = infracao_ai;
    }

    public String getPenalidade_ai() {
        return penalidade_ai;
    }

    public void setPenalidade_ai(String penalidade_ai) {
        this.penalidade_ai = penalidade_ai;
    }

    public String getSituacao_ai() {
        return situacao_ai;
    }

    public void setSituacao_ai(String situacao_ai) {
        this.situacao_ai = situacao_ai;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }


}
