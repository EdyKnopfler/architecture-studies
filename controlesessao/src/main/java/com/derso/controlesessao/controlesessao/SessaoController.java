package com.derso.controlesessao.controlesessao;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {
	
	@Autowired
	public ServicoTrava servicoTrava;
	
	@PostMapping("/nova")
	public String novaSessao() {
		UUID uuid = UUID.randomUUID();
		
		// TODO persistir no MySQL
		
		return uuid.toString();
	}
	
	@PutMapping("/{uuid}/estado")
	public String alterarSessao(@PathVariable("uuid") String uuidSessao) {
		boolean executou = servicoTrava.executarSobTrava(uuidSessao, () -> {
			// TODO alterar no MySQL -- por enquanto simulando algo demorado
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return executou ? "ok" : "falhou";
	}

}
