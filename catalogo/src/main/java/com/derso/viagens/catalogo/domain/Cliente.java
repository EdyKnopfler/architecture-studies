package com.derso.viagens.catalogo.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente extends Usuario {
	
	@Column(length = 11, nullable = false)
	private String cpf;
	
	@Column(nullable = false)
	private LocalDate dataNascimento;
	
	@Embedded
	private Endereco endereco;
	
	public void setCpf(String cpf) {
		this.cpf = Formatador.somenteDigitos(cpf);
	}

	@Override
	public String[] getPapeis() {
		return new String[] {"CLIENTE"};
	}

}
