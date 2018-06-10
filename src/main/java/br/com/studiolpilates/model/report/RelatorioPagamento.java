package br.com.studiolpilates.model.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RelatorioPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date dataRecebimento;
    private String nomePaciente;
    private String formaPagamento;
    private String procedimento;
    private String nomeConvenio;
    private String statusRepasse;
    private BigDecimal valorLucro = BigDecimal.ZERO;
    private BigDecimal valorRepasse = BigDecimal.ZERO;
    private BigDecimal valorRecebido = BigDecimal.ZERO;

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getNomeConvenio() {
        return nomeConvenio;
    }

    public void setNomeConvenio(String nomeConvenio) {
        this.nomeConvenio = nomeConvenio;
    }

    public String getStatusRepasse() {
        return statusRepasse;
    }

    public void setStatusRepasse(String statusRepasse) {
        this.statusRepasse = statusRepasse;
    }

    public BigDecimal getValorLucro() {
        return valorLucro;
    }

    public void setValorLucro(BigDecimal valorLucro) {
        this.valorLucro = valorLucro;
    }

    public BigDecimal getValorRepasse() {
        return valorRepasse;
    }

    public void setValorRepasse(BigDecimal valorRepasse) {
        this.valorRepasse = valorRepasse;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

}
