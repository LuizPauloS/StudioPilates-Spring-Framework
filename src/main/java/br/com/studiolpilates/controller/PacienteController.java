package br.com.studiolpilates.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.studiolpilates.model.Paciente;
import br.com.studiolpilates.model.Sexo;
import br.com.studiolpilates.model.Status;
import br.com.studiolpilates.model.filter.PacienteFilter;
import br.com.studiolpilates.service.PacienteService;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	private PacienteService pacienteService;

	private static final String NEW_PACIENTES = "pacientes/CadastroPacientes";
	private static final String FIND_ALL_PACIENTES = "pacientes/PesquisaPacientes";
	private static final String REDIRECT_NEW_PACIENTES = "redirect:/pacientes/novo";
	private static final String REDIRECT_LIST_PACIENTES= "redirect:/pacientes";

	/**
	 * Direciona para a tela de cadastro de pacientes
	 * @return
	 */
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(NEW_PACIENTES);
		mv.addObject(new Paciente());
		return mv;
	}

	/**
	 * Adiciona e edita pacientes
	 * @param paciente
	 * @param errors
	 * @param attributes
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String adicionarPaciente(@Validated Paciente paciente, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return NEW_PACIENTES;
		}
		try {
			if (paciente.getId() != null) {
				this.pacienteService.cadastraPaciente(paciente);
				attributes.addFlashAttribute("mensagem", "Registro do Paciente " + paciente.getNome() + " alterado com sucesso!");
			} else if (paciente.getId() == null) {
				this.pacienteService.cadastraPaciente(paciente);
				attributes.addFlashAttribute("mensagem", "Paciente " + paciente.getNome() + " adicionado com sucesso!");
			}
			return REDIRECT_NEW_PACIENTES;
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataNascimento", null, e.getMessage());
			return NEW_PACIENTES;
		}
	}

	/**
	 * Metodo que pesquisa todos os pacientes cadastrados para mostrar na tabela
	 * e filtra pelo nome caso seja informado no campo de pesquisa
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView pesquisarPacientes(@ModelAttribute("pacienteFilter")PacienteFilter pacienteFilter) {
		List<Paciente> todosPacientes = this.pacienteService.buscaPeloNomePaciente(pacienteFilter);
		
		ModelAndView mv = new ModelAndView(FIND_ALL_PACIENTES);
		mv.addObject("pacientes", todosPacientes);
		return mv;
	}
	
	
	/**
	 * Metodo que busca paciente pelo id para edicao
	 * @param id
	 * @return
	 */
	@RequestMapping("/paciente/{id}")
	public ModelAndView editarPacientes(@PathVariable Long id) {
		Paciente paciente = this.pacienteService.buscaPacientePeloId(id);
		ModelAndView mv = new ModelAndView(NEW_PACIENTES);
		mv.addObject(paciente);
		return mv;
	}

	/**
	 * Metodo que deleta paciente pelo id
	 * @param id
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String detetarPaciente(@PathVariable Long id) {
		this.pacienteService.excluiPaciente(id);
		return REDIRECT_LIST_PACIENTES;
	}

	/**
	 * Metodo que altera o status atual do paciente para ATIVO via ajax
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "paciente/{id}/ativar", method = RequestMethod.PUT)
	public @ResponseBody String ativarPaciente(@PathVariable Long id) {
		return this.pacienteService.alteraStatusPaciente(id);
	}

	/**
	 * Lista de todos os sexos existentes no enum para for each na view
	 * @return
	 */
	@ModelAttribute("listaTodosSexos")
	public List<Sexo> listaTodosSexos() {
		return Arrays.asList(Sexo.values());
	}

	/**
	 * Lista de todos os status existentes no enum para for each na view
	 * @return
	 */
	@ModelAttribute("listaTodosStatus")
	public List<Status> listaTodosStatus() {
		return Arrays.asList(Status.values());
	}
}
