package br.com.studiolpilates.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.studiolpilates.model.Pagamento;
import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByProcedimentoContaining(String procedimento);

    @Query("SELECT p FROM Pagamento p WHERE status = 'EFETUADO' AND data_cadastro BETWEEN :dataInicial AND :dataFinal")
    public abstract List<Pagamento> findByDataCadastroBetweenDataInicioAndDataFimEfetuados(@Param("dataInicial") Date paramDate1, @Param("dataFinal") Date paramDate2);

    @Query("SELECT p FROM Pagamento p WHERE status = 'PENDENTE' AND data_cadastro BETWEEN :dataInicial AND :dataFinal")
    public abstract List<Pagamento> findByDataCadastroBetweenDataInicioAndDataFimPendentes(@Param("dataInicial") Date paramDate1, @Param("dataFinal") Date paramDate2);

    @Query("SELECT p FROM Pagamento p WHERE status = :status AND data_cadastro BETWEEN :dataInicial AND :dataFinal")
    List<Pagamento> findByPagamentos(@Param("status") String status,
            @Param("dataInicial") Date dataInicial, @Param("dataFinal") Date dataFinal);

    @Query("SELECT p FROM Pagamento p WHERE status = :status AND paciente = :id AND data_cadastro "
            + "BETWEEN :dataInicial AND :dataFinal")
    List<Pagamento> findByPagamentosPorPaciente(@Param("status") String status, @Param("id") Long id,
            @Param("dataInicial") Date dataInicial, @Param("dataFinal") Date dataFinal);

    @Query("SELECT p FROM Pagamento p WHERE status = :status AND nomeConvenio = :convenio AND data_cadastro "
            + "BETWEEN :dataInicial AND :dataFinal")
    List<Pagamento> findByPagamentosPorConvenio(@Param("status") String status, @Param("convenio") String convenio,
            @Param("dataInicial") Date dataInicial, @Param("dataFinal") Date dataFinal);

    @Query("SELECT p FROM Pagamento p WHERE status = :status AND paciente = :id AND nomeConvenio = :convenio "
            + "AND data_cadastro BETWEEN :dataInicial AND :dataFinal")
    List<Pagamento> findByPagamentosPorConvenioPaciente(@Param("status") String status, @Param("id") Long id,
            @Param("convenio") String convenio, @Param("dataInicial") Date dataInicial,
            @Param("dataFinal") Date dataFinal);
}
