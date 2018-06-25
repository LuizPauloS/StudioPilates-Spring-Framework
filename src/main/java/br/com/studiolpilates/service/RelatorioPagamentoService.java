package br.com.studiolpilates.service;

import br.com.studiolpilates.model.Pagamento;
import br.com.studiolpilates.model.filter.RelatorioPagamentoFilter;
import br.com.studiolpilates.repository.PagamentoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class RelatorioPagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public List<Pagamento> buscaTodosPagamentos(RelatorioPagamentoFilter filter) {
        return this.pagamentoRepository.findByPagamentos(filter.getStatusRepasse().toUpperCase(),
                filter.getDataInicial(), filter.getDataFinal());
    }

    public List<Pagamento> buscaPagamentosPaciente(RelatorioPagamentoFilter filter) {
        return this.pagamentoRepository.findByPagamentosPorPaciente(filter.getStatusRepasse().toUpperCase(), 
                Long.valueOf(filter.getNomePaciente()), filter.getDataInicial(), filter.getDataFinal());
    }

    public List<Pagamento> buscaPagamentosConvenio(RelatorioPagamentoFilter filter) {
        return this.pagamentoRepository.findByPagamentosPorConvenio(filter.getStatusRepasse().toUpperCase(), 
                filter.getNomeConvenio(), filter.getDataInicial(), filter.getDataFinal());
    }
    
    public List<Pagamento> buscaTodosPagamentosConvenioEPaciente(RelatorioPagamentoFilter filter) {
        return this.pagamentoRepository.findByPagamentosPorConvenioPaciente(filter.getStatusRepasse().toUpperCase(),
                Long.valueOf(filter.getNomePaciente()), filter.getNomeConvenio(), filter.getDataInicial(), 
                filter.getDataFinal());
    }

    public ModelAndView preencheModelAndViewReport(List<Pagamento> pagamentos, ModelAndView m,
            RelatorioPagamentoFilter relatorioPagamentoFilter, String relatorio) {
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
        List<RelatorioPagamentoFilter> modelRelatorioPagamentos = new ArrayList<>();
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
