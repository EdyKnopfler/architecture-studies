package com.derso.controlesessao.persistencia;

@SuppressWarnings("serial")
public class TransicaoInvalidaException extends RuntimeException {
	
	private EstadoSessao estadoAtual;
	
	public TransicaoInvalidaException(EstadoSessao estadoAtual) {
		super("Transição inválida a partir do estado: " + estadoAtual);
		this.estadoAtual = estadoAtual;
	}
	
	public EstadoSessao getEstadoAtual() {
		return estadoAtual;
	}

}
