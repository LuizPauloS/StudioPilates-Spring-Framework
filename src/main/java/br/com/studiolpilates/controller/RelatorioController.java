package br.com.studiolpilates.controller;

import br.com.studiolpilates.model.FormaPagamento;
import br.com.studiolpilates.model.Paciente;
import br.com.studiolpilates.model.Pagamento;
import br.com.studiolpilates.model.StatusRepasse;
import br.com.studiolpilates.model.filter.RelatorioPacienteFilter;
import br.com.studiolpilates.model.filter.RelatorioPagamentoFilter;
import br.com.studiolpilates.repository.PacienteRepository;
import br.com.studiolpilates.service.PacienteService;
import br.com.studiolpilates.service.RelatorioPacienteService;
import br.com.studiolpilates.service.RelatorioPagamentoService;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author luiz.silva
 */
@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioPacienteService relatorioPacienteService;

    @Autowired
    private RelatorioPagamentoService relatorioPagamentoService;

    @Autowired
    private PacienteService pacienteService;

    private static final String REPORT_PACIENTES = "relatorios/RelatorioPacientes";
    private static final String REPORT_PAGAMENTOS = "relatorios/RelatorioPagamentos";
    private static final String REPORT_PAGAMENTO_EFETUADOS = "relatorio_pagtos_efetuados";
    private static final String REPORT_PAGAMENTO_PENDENTES = "relatorio_pagtos_pendentes";

    @RequestMapping(value = "/pacientes", method = RequestMethod.GET)
    public ModelAndView getRelatorioPacientes() {
        ModelAndView mv = new ModelAndView(REPORT_PACIENTES);
        mv.addObject("relatorioPacienteFilter", new RelatorioPacienteFilter());
        return mv;
    }

    @RequestMapping(value = "/pagamentos", method = RequestMethod.GET)
    public ModelAndView getRelatorioPagamentos() {
        ModelAndView mv = new ModelAndView(REPORT_PAGAMENTOS);
        mv.addObject("relatorioPagamentoFilter", new RelatorioPagamentoFilter());
        mv.addObject("listaTodosPacientes", this.listaTodosPacientes());
        return mv;
    }

    @RequestMapping(value = "/pacientes", method = RequestMethod.POST)
    public ModelAndView generateReportPacientes(@ModelAttribute RelatorioPacienteFilter relatorioPacienteFilter,
            RedirectAttributes attributes) throws ParseException {
        List<Paciente> data = this.relatorioPacienteService.buscaPacientes(relatorioPacienteFilter);
        ModelAndView m = new ModelAndView();
        if (!data.isEmpty()) {
            m.addObject("dataSource", data);
            m.addObject("data_inicio", relatorioPacienteFilter.getDataInicial());
            m.addObject("data_fim", relatorioPacienteFilter.getDataFinal());
            m.addObject("format", "pdf");
            m.setViewName("relatorio_pacientes");
            return m;
        }
        attributes.addFlashAttribute("mensagem", "Nenhum registro encontrado!");
        m.addObject("mensagem", attributes);
        m.setViewName(REPORT_PACIENTES);
        return m;
    }

    @RequestMapping(value = "/pagamentos", method = RequestMethod.POST)
    public ModelAndView generateReportPagamentosEfetuados(@ModelAttribute @Validated RelatorioPagamentoFilter relatorioPagamentoFilter, Errors errors) {
        if (errors.hasErrors()) {
            ModelAndView m = new ModelAndView();
            m.setViewName(REPORT_PAGAMENTOS);
            return m;
        }
        List<Pagamento> pagamentos = null;
        String convenio = relatorioPagamentoFilter.getNomeConvenio();
        String paciente = relatorioPagamentoFilter.getNomePaciente();
        String tipoRelatorio = "EFETUADO".equals(relatorioPagamentoFilter.getStatusRepasse())
                ? REPORT_PAGAMENTO_EFETUADOS : REPORT_PAGAMENTO_PENDENTES;
        if (convenio != null && paciente == null) {
            pagamentos = this.relatorioPagamentoService.buscaPagamentosConvenio(relatorioPagamentoFilter);
        } else if (convenio == null && paciente != null) {
            pagamentos = this.relatorioPagamentoService.buscaPagamentosPaciente(relatorioPagamentoFilter);
        } else if (convenio != null && paciente != null) {
            pagamentos = this.relatorioPagamentoService.buscaTodosPagamentosConvenioEPaciente(relatorioPagamentoFilter);
        } else {
            pagamentos = this.relatorioPagamentoService.buscaTodosPagamentos(relatorioPagamentoFilter);
        }
        return this.relatorioPagamentoService.preencheModelAndViewReport(pagamentos,
                relatorioPagamentoFilter, tipoRelatorio);
    }

    @ModelAttribute("listaTodosPacientes")
    public List<Paciente> listaTodosPacientes() {
        return this.pacienteService.buscaTodosPacientes();
    }

    @ModelAttribute("listaFormasPagamento")
    public List<FormaPagamento> listaFormasPagamento() {
        return Arrays.asList(FormaPagamento.values());
    }

    @ModelAttribute("listaTodosStatusRepasse")
    public List<StatusRepasse> listaTodosStatusRepasse() {
        return Arrays.asList(StatusRepasse.values());
    }
}
