package br.com.studiolpilates.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.studiolpilates.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
	
	public List<Paciente> findByNomeContaining(String nome);
}
