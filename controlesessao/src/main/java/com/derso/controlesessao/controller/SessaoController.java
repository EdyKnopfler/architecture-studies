package com.derso.controlesessao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.derso.controlesessao.persistencia.SessaoServico;
import com.derso.controlesessao.trava.ServicoTrava;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {
	
	@Autowired
	private SessaoServico sessaoServico;
	
	@Autowired
	private ServicoTrava servicoTrava;
	
	@PostMapping("/nova")
	@Transactional
	public String novaSessao() {
		return sessaoServico.novaSessao().getUuid();
	}
	
	@PutMapping("/{uuid}/estado")
	@Transactional
	public String alterarSessao(
			@PathVariable("uuid") String uuidSessao,
			@RequestBody EstadoSessaoDTO requisicao
	) {
		boolean executou = servicoTrava.executarSobTrava(uuidSessao, () -> {
			sessaoServico.atualizarEstado(uuidSessao, requisicao.novoEstado());
		});
		return executou ? "ok" : "falhou";
	}

}
