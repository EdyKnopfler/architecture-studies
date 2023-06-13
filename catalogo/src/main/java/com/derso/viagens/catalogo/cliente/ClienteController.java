package com.derso.viagens.catalogo.cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * CONTROLLER PADRÃO
 * 
 * Métodos devolvem um nome de template!
 */

@Controller
public class ClienteController {
	
	@GetMapping("/")
	public String home() {
		return "home";  // home.html
	}
	
	@GetMapping("/area-do-cliente")
	public String areaDoCliente() {
		return "area-do-cliente";
	}

}
