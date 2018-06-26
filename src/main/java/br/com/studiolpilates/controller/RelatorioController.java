package br.com.studiolpilates.controller;

import br.com.studiolpilates.model.Paciente;
import br.com.studiolpilates.model.Pagamento;
import br.com.studiolpilates.model.filter.RelatorioPacienteFilter;
import br.com.studiolpilates.model.filter.RelatorioPagamentoFilter;
import br.com.studiolpilates.repository.PacienteRepository;
import br.com.studiolpilates.repository.PagamentoRepository;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    private static final String REPORT_PACIENTES = "relatorios/RelatorioPacientes";
    private static final String REPORT_PAGAMENTOS = "relatorios/RelatorioPagamentos";
    private static final String REPORT_PAGAMENTO_EFETUADOS = "relatorio_pagtos_efetuados";
    private static final String REPORT_PAGAMENTO_PENDENTES = "relatorio_pagtos_pendentes";

    @RequestMapping("/pacientes")
    public ModelAndView getRelatorioPacientes() {
        ModelAndView mv = new ModelAndView("relatorios/RelatorioPacientes");
        mv.addObject("relatorioPacienteFilter", new RelatorioPacienteFilter());
        return mv;
    }

    @RequestMapping("/efetuados")
    public ModelAndView getRelatorioPagamentosEfetuados() {
        ModelAndView mv = new ModelAndView("relatorios/RelatorioPagamentos");
        mv.addObject("relatorioPagamentoFilter", new RelatorioPagamentoFilter());
        return mv;
    }
    
    @RequestMapping("/pendentes")
    public ModelAndView getRelatorioPagamentosPendentes() {
        ModelAndView mv = new ModelAndView("relatorios/RelatorioPagamentosPendentes");
        mv.addObject("relatorioPagamentoFilter", new RelatorioPagamentoFilter());
        return mv;
    }

    @RequestMapping(value = "/pacientes-cadastrados", method = RequestMethod.POST)
    public ModelAndView generateReportPacientes(@ModelAttribute RelatorioPacienteFilter relatorioPacienteFilter, RedirectAttributes attributes) throws ParseException {
        List<Paciente> data = pacienteRepository.findByDataCadastroBetweenDataInicioAndDataFim(relatorioPacienteFilter.getDataInicial(), relatorioPacienteFilter
                .getDataFinal());
        ModelAndView m = new ModelAndView();
        if (!data.isEmpty()) {
            m.addObject("dataSource", data);
            m.addObject("data_inicio", relatorioPacienteFilter.getDataInicial());
            m.addObject("data_fim", relatorioPacienteFilter.getDataFinal());
            m.addObject("format", "pdf");
            m.setViewName("relatorio_pacientes");
            return m;
        }
        //attributes.addFlashAttribute("mensagem", "Nenhum registro encontrado!");
        m.setViewName(REPORT_PACIENTES);
        return m;
    }

    @RequestMapping(value = "/pagamentos-efetuados", method = RequestMethod.POST)
    public ModelAndView generateReportPagamentosEfetuados(@ModelAttribute RelatorioPagamentoFilter relatorioPagamentoFilter) throws ParseException {
        List<Pagamento> pagamentos = pagamentoRepository.findByDataCadastroBetweenDataInicioAndDataFimEfetuados(relatorioPagamentoFilter.getDataInicial(), relatorioPagamentoFilter
                .getDataFinal());
        return preencheModelAndViewReport(pagamentos, relatorioPagamentoFilter, "relatorio_pagtos_efetuados");
    }

    @RequestMapping(value = {"/pagamentos-pendentes"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public ModelAndView generateReportPagamentosPendentes(@ModelAttribute RelatorioPagamentoFilter relatorioPagamentoFilter) throws ParseException {
        List<Pagamento> pagamentos = pagamentoRepository.findByDataCadastroBetweenDataInicioAndDataFimPendentes(relatorioPagamentoFilter.getDataInicial(), relatorioPagamentoFilter
                .getDataFinal());
        return preencheModelAndViewReport(pagamentos, relatorioPagamentoFilter, "relatorio_pagtos_pendentes");
    }
    
    private ModelAndView preencheModelAndViewReport(List<Pagamento> pagamentos, 
            RelatorioPagamentoFilter relatorioPagamentoFilter, String relatorio) {
        ModelAndView m = new ModelAndView();
        if (!pagamentos.isEmpty()) {
            List<RelatorioPagamentoFilter> data = getListReportPagamentos(pagamentos);
            m.addObject("dataSource", data);
            m.addObject("data_inicio", relatorioPagamentoFilter.getDataInicial());
            m.addObject("data_fim", relatorioPagamentoFilter.getDataFinal());
            m.addObject("format", "pdf");
            m.setViewName(relatorio);
            return m;
        }
        return m;
    }

    private List<RelatorioPagamentoFilter> getListReportPagamentos(List<Pagamento> pagamentos) {
        List<RelatorioPagamentoFilter> modelRelatorioPagamentos = new ArrayList();
        for (Pagamento p : pagamentos) {
            RelatorioPagamentoFilter relatorio = new RelatorioPagamentoFilter();
            relatorio.setDataRecebimento(p.getDataRecebimento());
            relatorio.setFormaPagamento(p.getFormaPagamento().getDescricao());
            relatorio.setNomeConvenio(p.getNomeConvenio());
            relatorio.setNomePaciente(p.getPaciente().getNome());
            relatorio.setProcedimento(p.getProcedimento());
            relatorio.setStatusRepasse(p.getStatusRepasse().getDescricaoStatusRepasse());
            relatorio.setValorLucro(p.getValorLucro());
            relatorio.setValorRecebido(p.getValorRecebido());
            relatorio.setValorRepasse(p.getValorRepasse());
            modelRelatorioPagamentos.add(relatorio);
        }
        return modelRelatorioPagamentos;
    }
}
