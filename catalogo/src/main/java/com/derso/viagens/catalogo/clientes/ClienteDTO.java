package com.derso.viagens.catalogo.clientes;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascimento;
	
	@NotNull
	@Valid
	private Endereco endereco;

	public Cliente criar() {
		Cliente cliente = new Cliente();
		UsuarioDTO.preencherUsuario(cliente, this);
		cliente.setId(getId());
		cliente.setCpf(cpf);
		cliente.setDataNascimento(dataNascimento);
		cliente.setEndereco(endereco);
		return cliente;
	}

	public static ClienteDTO criarDe(Cliente cliente) {
		ClienteDTO dto = new ClienteDTO();
		UsuarioDTO.preencherDTO(dto, cliente);
		dto.setCpf(cliente.getCpf());
		dto.setDataNascimento(cliente.getDataNascimento());
		dto.setEndereco(cliente.getEndereco());
		return dto;
	}

}
