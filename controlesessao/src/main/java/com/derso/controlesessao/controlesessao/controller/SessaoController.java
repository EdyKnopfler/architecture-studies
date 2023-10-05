package com.derso.controlesessao.controlesessao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.derso.controlesessao.controlesessao.persistencia.Sessao;
import com.derso.controlesessao.controlesessao.persistencia.SessaoRepositorio;
import com.derso.controlesessao.controlesessao.trava.ServicoTrava;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {
	
	@Autowired
	private ServicoTrava servicoTrava;
	
	@Autowired
	private SessaoRepositorio sessaoRepositorio;
	
	@PostMapping("/nova")
	@Transactional
	public String novaSessao() {
		Sessao sessao = new Sessao();
		sessaoRepositorio.save(sessao);
		return sessao.getUuid();
	}
	
	@PutMapping("/{uuid}/estado")
	@Transactional
	public String alterarSessao(
			@PathVariable("uuid") String uuidSessao,
			@RequestBody EstadoSessaoDTO requisicao
	) {
		boolean executou = servicoTrava.executarSobTrava(uuidSessao, () -> {
			sessaoRepositorio.atualizarEstado(uuidSessao, requisicao.novoEstado());
		});
		return executou ? "ok" : "falhou";
	}

}
