package br.com.studiolpilates.model.filter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class RelatorioPagamentoFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Data inicial é obrigatório!")
    private Date dataInicial;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Data final é obrigatório!")
    private Date dataFinal;
    @NotEmpty(message = "Tipo do relatório é obrigatório!")
    private String statusRepasse;
    private Date dataRecebimento;
    private String nomePaciente;
    private String formaPagamento;
    private String procedimento;
    private String nomeConvenio;
    private BigDecimal valorLucro = BigDecimal.ZERO;
    private BigDecimal valorRepasse = BigDecimal.ZERO;
    private BigDecimal valorRecebido = BigDecimal.ZERO;

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

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
