package br.com.studiolpilates.model;

public enum Sexo {

    FEMININO("Feminino"),
    MASCULINO("Masculino");

    private String descricaoSexo;

    Sexo(String descricaoSexo) {
        this.descricaoSexo = descricaoSexo;
    }

    public String getDescricaoSexo() {
        return descricaoSexo;
    }

}
