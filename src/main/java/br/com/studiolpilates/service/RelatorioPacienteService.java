package br.com.studiolpilates.service;

import br.com.studiolpilates.model.Paciente;
import br.com.studiolpilates.model.filter.RelatorioPacienteFilter;
import br.com.studiolpilates.repository.PacienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatorioPacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<Paciente> buscaPacientes(RelatorioPacienteFilter filter) {
        return this.pacienteRepository.findByDataCadastroBetweenDataInicioAndDataFim(filter.getDataInicial(),
                filter.getDataFinal());
    }
}
