package br.com.studiolpilates.model;

public enum FormaPagamento {

    CREDITO("Cartão de Crédito"),
    DEBITO("Cartão de Débito"),
    DINHEIRO("Dinheiro"),
    CHEQUE("Cheque");

    private String descricao;

    private FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
