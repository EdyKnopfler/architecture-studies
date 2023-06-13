package com.derso.viagens.catalogo.domain;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.derso.viagens.catalogo.config.validators.MaiorDeIdade;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente extends Usuario {
	
	@Column(length = 11, nullable = false)
	@NotEmpty(message = "O campo CPF é obrigatório")
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotNull(message = "O campo Data de Nascimento é obrigatório")
	@MaiorDeIdade
	private LocalDate dataNascimento;
	
	@NotNull
	@Embedded
	@Valid
	private Endereco endereco;
	
	public void setCpf(String cpf) {
		this.cpf = Formatador.somenteDigitos(cpf);
	}

	@Override
	public String[] getPapeis() {
		return new String[] {"CLIENTE"};
	}

}
