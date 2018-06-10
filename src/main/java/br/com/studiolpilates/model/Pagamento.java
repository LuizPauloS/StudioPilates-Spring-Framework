package br.com.studiolpilates.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "pagamento")
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Convênio é obrigatório!")
    @Column(name = "nome_convenio", nullable = false)
    private String nomeConvenio;

    @DateTimeFormat(pattern = "dd/mm/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_recebimento")
    private Date dataRecebimento;

    @DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
    @DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
    @NumberFormat(pattern = "#,##0.00")
    @Column(name = "valor_recebido")
    private BigDecimal valorRecebido = BigDecimal.ZERO;

    @NumberFormat(pattern = "#,##0.00")
    @Column(name = "valor_repasse")
    private BigDecimal valorRepasse = BigDecimal.ZERO;

    @NumberFormat(pattern = "#,##0.00")
    @Column(name = "valor_lucro")
    private BigDecimal valorLucro = BigDecimal.ZERO;

    @NotNull(message = "Forma Pagamento é obrigatório!")
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;

    @NotNull(message = "Paciente é obrigatório!")
    @ManyToOne
    private Paciente paciente;

    @NotEmpty(message = "Procedimento é obrigatório!")
    @Column(name = "procedimento")
    private String procedimento;

    @Column(name = "descricao_procedimento")
    private String descricaoProcedimento;

    @Column(name = "observacao")
    private String observacao;

    @DecimalMin(value = "0", message = "Percentual deve ser no mínino igual a 0%!")
    @DecimalMax(value = "100", message = "Percentual deve ser no máxino igual a 100%!")
    @NotEmpty(message = "Percentual não pode ser vazio!")
    @Column(name = "percentual")
    private String percentual;

    @DateTimeFormat(pattern = "dd/mm/yyyy")
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    @NotNull(message = "Status do pagamento é obrigatório!")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusRepasse statusRepasse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeConvenio() {
        return nomeConvenio;
    }

    public void setNomeConvenio(String nomeConvenio) {
        this.nomeConvenio = nomeConvenio;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public BigDecimal getValorRepasse() {
        return valorRepasse;
    }

    public void setValorRepasse(BigDecimal valorRepasse) {
        this.valorRepasse = valorRepasse;
    }

    public BigDecimal getValorLucro() {
        return valorLucro;
    }

    public void setValorLucro(BigDecimal valorLucro) {
        this.valorLucro = valorLucro;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getDescricaoProcedimento() {
        return descricaoProcedimento;
    }

    public void setDescricaoProcedimento(String descricaoProcedimento) {
        this.descricaoProcedimento = descricaoProcedimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getPercentual() {
        return percentual;
    }

    public void setPercentual(String percentual) {
        this.percentual = percentual;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public StatusRepasse getStatusRepasse() {
        return statusRepasse;
    }

    public void setStatusRepasse(StatusRepasse statusRepasse) {
        this.statusRepasse = statusRepasse;
    }

    public boolean isEfetuado() {
        return StatusRepasse.EFETUADO.equals(this.statusRepasse);
    }

    public boolean isPendente() {
        return StatusRepasse.PENDENTE.equals(this.statusRepasse);
    }

    public boolean isNovoPagamento() {
        return this.getId() == null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pagamento other = (Pagamento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pagamento{" + "id=" + id + ", nomeConvenio=" + nomeConvenio + ", dataRecebimento=" + dataRecebimento
                + ", valorRecebido=" + valorRecebido + ", valorRepasse=" + valorRepasse + ", valorLucro=" + valorLucro
                + ", formaPagamento=" + formaPagamento + ", paciente=" + paciente + ", procedimento=" + procedimento
                + ", descricaoProcedimento=" + descricaoProcedimento + ", observacao=" + observacao + ", percentual="
                + percentual + ", dataCadastro=" + dataCadastro + ", statusRepasse=" + statusRepasse + '}';
    }

}
