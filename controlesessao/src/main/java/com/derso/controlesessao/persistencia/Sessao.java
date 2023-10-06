package com.derso.controlesessao.persistencia;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Sessao {
	
	@Id
	@Column(length = 36)
	private String uuid;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Instant criacao;
	
	@Enumerated(EnumType.STRING)
	private EstadoSessao estado;
	
	public Sessao() {
		uuid = UUID.randomUUID().toString();
		criacao = Instant.now();
		estado = EstadoSessao.CORRENDO;
	}

}
