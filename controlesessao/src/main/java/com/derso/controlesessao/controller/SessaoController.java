package com.derso.controlesessao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.derso.controlesessao.persistencia.SessaoServico;
import com.derso.controlesessao.persistencia.TransicaoInvalidaException;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {
	
	@Autowired
	private SessaoServico sessaoServico;
	
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
		boolean executou = sessaoServico.atualizarEstado(uuidSessao, requisicao.novoEstado());
		return executou ? "ok" : "falhou";
	}
	
	@ExceptionHandler(TransicaoInvalidaException.class)
	public Map<String, String> erroTransicaoInvalida(TransicaoInvalidaException erro) {
		Map<String, String> resposta = new HashMap<>();
		resposta.put("erro", erro.getMessage());
		resposta.put("estadoAtual", erro.getEstadoAtual().toString());
		return resposta;
	}

}
