package com.derso.viagens.catalogo.clientes;

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
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

/*
 * CONTROLLER PADRÃO
 * 
 * Métodos devolvem um nome de template!
 */

@Controller
public class ClientesController {
	
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
			String usuarioLogado = autenticacao.usuarioLogado();
			
			if (usuarioLogado != null) {
				Cliente cliente = repositorio.findByEmail(usuarioLogado).get();
				model.addAttribute("cliente", ClienteDTO.criarDe(cliente));
			} else {
				model.addAttribute("cliente", new ClienteDTO());
			}
		}
		return "clientes/form_cliente";
	}
	
	/*
	 * O BindingResult captura os erros de validação, permitindo tratá-los.
	 * Ex.: https://cursos.alura.com.br/forum/topico-bean-validation-valid-e-bindingresult-162657
	 */
	@PostMapping("/cadastro-de-cliente")
	@Transactional
	public String salvarCliente(
			@Valid ClienteDTO clienteDto,
			BindingResult result,
			RedirectAttributes redirect,
			HttpServletRequest request) {
		
		if (result.hasErrors()) {
			redirect.addFlashAttribute("cliente", clienteDto);
			redirect.addFlashAttribute("erros", result.getAllErrors());
			return "redirect:/cadastro-de-cliente";
		}
		
		Cliente cliente = clienteDto.criar();
		String usuarioLogado = autenticacao.usuarioLogado();
		
		if (usuarioLogado != null) {
			// Tem forma mais eficiente de resolver isto, que era criando um derivado
			// de UserDetails que já incluísse o id do usuário.
			// (fica para depois... :D)
			
			Cliente existente = repositorio.findByEmail(usuarioLogado).get();
			
			// Ainda há questões a serem tratadas, como a necessidade de recadastrar a senha
			// durante a edição (o database não guarda a senha "raw").
			// Como modelo de CRUD comum, está bom. Neste caso específico, teríamos que tratar
			// de forma separada a criação e a alteração.
			if (clienteDto.getSenha().trim().equals("")) {
				cliente.setSenhaCriptografada(existente.getSenhaCriptografada());
			}
			
			cliente.setId(existente.getId());
		}
		
		repositorio.save(cliente);
		autenticacao.fazerLoginAutomatico(cliente, request);
		return "redirect:/area-do-cliente";
	}
	
	@GetMapping("/area-do-cliente")
	public String areaDoCliente() {
		return "clientes/area_do_cliente";
	}

}
