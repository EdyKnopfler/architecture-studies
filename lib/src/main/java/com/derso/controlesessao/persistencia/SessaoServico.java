package com.derso.controlesessao.persistencia;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.derso.controlesessao.trava.ServicoTrava;

@Service
public class SessaoServico {
	
	private Map<EstadoSessao, Set<EstadoSessao>> transicoesValidas = new HashMap<>();
	
	@Autowired
	private SessaoRepositorio sessaoRepositorio;
	
	@Autowired
	private ServicoTrava servicoTrava;

	public SessaoServico() {
		criarTransicoesValidas(
				EstadoSessao.CORRENDO, 
				EstadoSessao.PAUSADA, EstadoSessao.TEMPO_ESGOTADO, EstadoSessao.CANCELADA);
		criarTransicoesValidas(
				EstadoSessao.PAUSADA, 
				EstadoSessao.CORRENDO, EstadoSessao.TEMPO_ESGOTADO);
		criarTransicoesValidas(EstadoSessao.TEMPO_ESGOTADO, new EstadoSessao[] {});
		criarTransicoesValidas(EstadoSessao.CANCELADA, new EstadoSessao[] {});
	}
	
	private void criarTransicoesValidas(EstadoSessao origem, EstadoSessao ...possiveisDestinos) {
		transicoesValidas.put(origem, new HashSet<>(Arrays.asList(possiveisDestinos)));
	}
	
	public Sessao novaSessao() {
		Sessao sessao = new Sessao();
		sessaoRepositorio.save(sessao);
		return sessao;
	}

	@Transactional
	public boolean atualizarEstado(String uuidSessao, EstadoSessao novoEstado) {
		return servicoTrava.executarSobTrava(uuidSessao, () -> {
			Sessao atual = sessaoRepositorio.findById(uuidSessao).get();
			
			if (!transicoesValidas.get(atual.getEstado()).contains(novoEstado)) {
				throw new TransicaoInvalidaException(atual.getEstado());
			}
			
			sessaoRepositorio.atualizarEstado(uuidSessao, novoEstado);
		});
	}
	
}
