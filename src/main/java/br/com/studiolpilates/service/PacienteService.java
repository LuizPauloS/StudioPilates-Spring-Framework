package br.com.studiolpilates.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import br.com.studiolpilates.model.Paciente;
import br.com.studiolpilates.model.Status;
import br.com.studiolpilates.model.filter.PacienteFilter;
import br.com.studiolpilates.repository.PacienteRepository;
import br.com.studiolpilates.threads.ThreadEnviaEmail;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository pacienteRepository;

	public void cadastraPaciente(Paciente paciente) {
		paciente.setDataCadastro(new Date());
		if (!paciente.getEmail().isEmpty() || paciente.getEmail() != null) {
			ThreadEnviaEmail enviarEmailConfirmacao = new ThreadEnviaEmail(paciente);
			Thread thread = new Thread(enviarEmailConfirmacao, "EmailConfirmacao");
			thread.start();
		}
		try {
			this.pacienteRepository.save(paciente);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido!");
		}
	}

	public String alteraStatusPaciente(Long id) {
		Paciente paciente = this.pacienteRepository.findOne(id);
		paciente.setStatus(Status.ATIVO);
		this.pacienteRepository.save(paciente);
		return Status.ATIVO.getDescricaoStatus().toUpperCase();
	}

	public List<Paciente> buscaPeloNomePaciente(PacienteFilter pacienteFilter) {
		String nome = pacienteFilter.getNome() == null ? "%" : pacienteFilter.getNome();
		return this.pacienteRepository.findByNomeContaining(nome);
	}

	public List<Paciente> buscaTodosPacientes() {
		return this.pacienteRepository.findAll();
	}

	public Paciente buscaPacientePeloId(Long id) {
		return this.pacienteRepository.findOne(id);
	}

	public void excluiPaciente(Long id) {
		this.pacienteRepository.delete(id);
	}
	
}
