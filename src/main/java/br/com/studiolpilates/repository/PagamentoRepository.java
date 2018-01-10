package br.com.studiolpilates.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.studiolpilates.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{
	public List<Pagamento> findByProcedimentoContaining(String procedimento);
}
