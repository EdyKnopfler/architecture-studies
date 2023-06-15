package com.derso.viagens.catalogo.admin.administradores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.derso.viagens.catalogo.domain.Administrador;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class AdministradoresController {
	
	@Autowired
	private AdministradoresRepository repositorio;
	
	@GetMapping("/admin/administradores")
	public String listagem(Model model) {
		model.addAttribute("administradores", repositorio.findAll());
		return "administradores/listagem";
	}
	
	@GetMapping("/admin/novo-admin")
	public String novoAdministrador(Model model) {
		Administrador retornadoDoRedirect = 
				(Administrador) model.getAttribute("administrador");
		
		if (retornadoDoRedirect == null) {
			model.addAttribute(new Administrador());
		}
		
		return "administradores/formulario";
	}
	
	@GetMapping("/admin/editar-admin/{id}")
	public String editarAdministrador(Model model, @PathVariable long id) {
		Administrador retornadoDoRedirect = 
				(Administrador) model.getAttribute("administrador");
		
		if (retornadoDoRedirect == null) {
			Administrador administrador = repositorio.findById(id).orElseThrow(
					() -> new ResponseStatusException(
							HttpStatus.NOT_FOUND, "Administrador não encontrado: " + id));
			model.addAttribute(administrador);
		}
		
		return "administradores/formulario";
	}
	
	@PostMapping("/admin/salvar")
	@Transactional
	public String salvarAdministrador(
			@Valid Administrador administrador,
			BindingResult result,
			RedirectAttributes redirect) {
		
		// TODO existe uma questão com validação em campos @Transient
		// Vamos implementar um DTO para carregar a senha sem criptografia, aí
		// a entidade Usuario carregará somente a senha criptografada.
		// Ref.: https://github.com/spring-projects/spring-data-rest/issues/1368
		if (result.hasErrors()) {
			redirect.addFlashAttribute("administrador", administrador);
			redirect.addFlashAttribute("erros", result.getAllErrors());
			return administrador.getId() != null
					? "redirect:/admin/editar-admin/" + administrador.getId()
					: "redirect:/admin/novo-admin";
		}
		
		repositorio.save(administrador);
		return "redirect:/admin/administradores";
	}
	
	@DeleteMapping("/admin/delete-admin/{id}")
	@Transactional
	public String apagarAdministrador(@PathVariable long id) {
		repositorio.deleteById(id);
		return "redirect:/admin/administradores";
	}
	
	

}
