package com.derso.viagens.catalogo.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.derso.viagens.catalogo.autenticacao.AutenticacaoService;
import com.derso.viagens.catalogo.domain.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/*
 * CONTROLLER PADRÃO
 * 
 * Métodos devolvem um nome de template!
 */

@Controller
public class ClienteController {
	
	@Autowired
	private ClientesRepositorio repositorio;
	
	@Autowired
	private AutenticacaoService autenticacao;
	
	@GetMapping("/")
	public String home() {
		return "home";  // home.html
	}
	
	/*
	 * Ping-pong entre o formulário e o POST ----------------------------------------------
	 * 
	 * Se o objeto que chega no POST é inválido, deve redirecionar de volta passando os dados.
	 * Conseguimos isto através do RedirectAttributes.
	 * 
	 * No GET vemos se há um objeto no Model, significando que foi redirecionado do POST
	 */
	@GetMapping("/cadastro-de-cliente")
	public String cadastroCliente(Model model) {
		if (model.getAttribute("cliente") == null) {
			model.addAttribute("cliente", new Cliente());
		}
		return "cadastro_cliente";
	}
	
	/*
	 * O BindingResult captura os erros de validação, permitindo tratá-los.
	 * Ex.: https://cursos.alura.com.br/forum/topico-bean-validation-valid-e-bindingresult-162657
	 */
	@PostMapping("/cadastro-de-cliente")
	public String salvarCliente(
			@Valid Cliente cliente, BindingResult result, RedirectAttributes redirect,
			HttpServletRequest request) {
		
		if (result.hasErrors()) {
			redirect.addFlashAttribute("cliente", cliente);
			redirect.addFlashAttribute("erros", result.getAllErrors());
			return "redirect:/cadastro-de-cliente";
		}
		
		repositorio.save(cliente);
		autenticacao.fazerLoginAutomatico(cliente, request);
		return "redirect:/area-do-cliente";
	}
	
	@GetMapping("/area-do-cliente")
	public String areaDoCliente() {
		return "area-do-cliente";
	}

}
