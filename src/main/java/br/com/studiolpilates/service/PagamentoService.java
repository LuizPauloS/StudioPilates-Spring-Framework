package br.com.studiolpilates.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.studiolpilates.model.Pagamento;
import br.com.studiolpilates.model.StatusRepasse;
import br.com.studiolpilates.model.filter.PagamentoFilter;
import br.com.studiolpilates.repository.PagamentoRepository;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	private BigDecimal totalLucro = BigDecimal.ZERO;
    private BigDecimal totalRepasse = BigDecimal.ZERO;
    private BigDecimal percentual = BigDecimal.ZERO;
    private BigDecimal percentual2 = BigDecimal.TEN.multiply(BigDecimal.TEN);
	
	public void cadastrarPagamento(Pagamento pagamento){
		Pagamento pagamentoAtualizado = calcularValores(pagamento);
		this.pagamentoRepository.save(pagamentoAtualizado);
	}
	
	public Pagamento buscarPagamentoId(Long id){
		return this.pagamentoRepository.findOne(id);
	}
	
	public List<Pagamento> buscarTodosPagamentos(){
		return this.pagamentoRepository.findAll();
	}
	
	public List<Pagamento> buscarPagamentoPeloProcedimento(PagamentoFilter pagamentoFilter){
		String procedimento = pagamentoFilter.getProcedimento() == null ? "%" : pagamentoFilter.getProcedimento();
		return this.pagamentoRepository.findByProcedimentoContaining(procedimento);
	}
	
	public void deletarPagamento(Long id){
		this.pagamentoRepository.delete(id);
	}
	
	public String realizarPagamento(Long id){
		Pagamento pagamento = this.pagamentoRepository.findOne(id);
		pagamento.setDataRecebimento(new Date());
		pagamento.setStatusRepasse(StatusRepasse.EFETUADO);
		this.pagamentoRepository.save(pagamento);
		return StatusRepasse.EFETUADO.getDescricaoStatusRepasse().toUpperCase();
	}
	
	private Pagamento limparValores(Pagamento pagamento) {
		pagamento.setValorLucro(BigDecimal.ZERO);
		pagamento.setValorRepasse(BigDecimal.ZERO);
		return pagamento;
	}

	private Pagamento calcularValores(Pagamento pagamento){
		if(pagamento.getId() != null){
			limparValores(pagamento);
		}
		String percentualInformado = pagamento.getPercentual();
        if (!"0".equals(pagamento.getPercentual())) {
            percentual = new BigDecimal(percentualInformado);
            //total recebe o valor do recebido e multiplica pela porcentagem
            totalRepasse = totalRepasse.add(pagamento.getValorRecebido().multiply(percentual.divide(percentual2)));
            pagamento.setValorRepasse(totalRepasse);
            totalLucro = totalLucro.add(pagamento.getValorRecebido().subtract(pagamento.getValorRepasse()));
            pagamento.setValorLucro(totalLucro);
        } else {
            percentual = BigDecimal.ZERO;
            totalLucro = totalLucro.add(pagamento.getValorRecebido());
            pagamento.setValorLucro(totalLucro);
            pagamento.setValorRepasse(percentual);
        }
		return pagamento;
	}
}
