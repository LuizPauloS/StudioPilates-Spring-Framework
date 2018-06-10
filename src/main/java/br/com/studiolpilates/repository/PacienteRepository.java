package br.com.studiolpilates.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.studiolpilates.model.Paciente;
import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    List<Paciente> findByNomeContaining(String nome);

    @Query("SELECT p FROM Paciente p WHERE data_cadastro BETWEEN :dataInicial AND :dataFinal")
    List<Paciente> findByDataCadastroBetweenDataInicioAndDataFim(@Param("dataInicial") Date dataInicial,
            @Param("dataFinal") Date dataFinal);
}
