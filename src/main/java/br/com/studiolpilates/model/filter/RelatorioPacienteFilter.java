package br.com.studiolpilates.model.filter;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class RelatorioPacienteFilter {

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataInicial;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataFinal;

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

}
