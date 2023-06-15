package com.derso.viagens.catalogo.clientes;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.derso.viagens.catalogo.autenticacao.UsuarioDTO;
import com.derso.viagens.catalogo.config.validators.MaiorDeIdade;
import com.derso.viagens.catalogo.domain.Cliente;
import com.derso.viagens.catalogo.domain.Endereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO extends UsuarioDTO {

	@NotEmpty(message = "O campo CPF é obrigatório")
	@CPF(message = "CPF inválido")
	private String cpf;
	
	@NotNull(message = "O campo Data de Nascimento é obrigatório")
	@MaiorDeIdade
	private LocalDate dataNascimento;
	
	@NotNull
	@Valid
	private Endereco endereco;

	public Cliente criar() {
		Cliente c = new Cliente();
		UsuarioDTO.preencher(this, c);
		c.setCpf(cpf);
		c.setDataNascimento(dataNascimento);
		c.setEndereco(endereco);
		return c;
	}

}
