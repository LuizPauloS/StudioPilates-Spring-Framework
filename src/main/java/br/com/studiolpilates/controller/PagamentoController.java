package br.com.studiolpilates.controller;

import java.util.Arrays;
import java.util.Date;
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
import br.com.studiolpilates.model.FormaPagamento;
import br.com.studiolpilates.model.Paciente;
import br.com.studiolpilates.model.Pagamento;
import br.com.studiolpilates.model.StatusRepasse;
import br.com.studiolpilates.model.filter.PagamentoFilter;
import br.com.studiolpilates.service.PacienteService;
import br.com.studiolpilates.service.PagamentoService;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private PacienteService pacienteService;

    private static final String NEW_PAGAMENTO = "pagamentos/CadastroPagamentos";
    private static final String FIND_ALL_PAGAMENTOS = "pagamentos/PesquisaPagamentos";
    private static final String REDIRECT_NEW_PAGAMENTO = "redirect:/pagamentos/novo";
    private static final String REDIRECT_LIST_PAGAMENTOS = "redirect:/pagamentos";

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView(NEW_PAGAMENTO);
        mv.addObject(new Pagamento());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String adicionaPagamento(@Validated Pagamento pagamento, Errors errors, 
            RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            return NEW_PAGAMENTO;
        }
        if (pagamento.getId() != null) {
            pagamento.setDataCadastro(new Date());
            this.pagamentoService.cadastrarPagamento(pagamento);
            attributes.addFlashAttribute("mensagem", "Pagamento do paciente " + 
                    pagamento.getPaciente().getNome() + " alterado com sucesso!");
        } else {
            pagamento.setDataCadastro(new Date());
            this.pagamentoService.cadastrarPagamento(pagamento);
            attributes.addFlashAttribute("mensagem", "Pagamento do paciente " + 
                    pagamento.getPaciente().getNome() + " adicionado com sucesso!");
        }
        return REDIRECT_NEW_PAGAMENTO;
    }

    //pesquisa pagamento pelo atributo do filtro
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView pesquisarPagamentos(@ModelAttribute("pagamentoFilter") 
            PagamentoFilter pagamentoFilter) {
        List<Pagamento> todosPagamentos = this.pagamentoService.buscarPagamentoPeloProcedimento(pagamentoFilter);
        ModelAndView mv = new ModelAndView(FIND_ALL_PAGAMENTOS);
        mv.addObject("pagamentos", todosPagamentos);
        return mv;
    }

    //metodo para deletar pagamento passando o id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String removerPagamento(@PathVariable Long id) {
        this.pagamentoService.deletarPagamento(id);
        return REDIRECT_LIST_PAGAMENTOS;
    }

    //metodo para alterar pagamento recuperando o mesmo pelo id
    @RequestMapping("/pagamento/{id}")
    public ModelAndView editarPagamento(@PathVariable Long id) {
        Pagamento pagamento = this.pagamentoService.buscarPagamentoId(id);
        ModelAndView mv = new ModelAndView(NEW_PAGAMENTO);
        mv.addObject(pagamento);
        return mv;
    }

    //altera status do pagamento para efetuado e seta data atual
    @RequestMapping(value = "pagamento/{id}/pagar", method = RequestMethod.PUT)
    public @ResponseBody String efetuarPagamento(@PathVariable Long id) {
        return this.pagamentoService.realizarPagamento(id);
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
