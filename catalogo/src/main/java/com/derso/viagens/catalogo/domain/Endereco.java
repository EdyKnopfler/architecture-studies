package com.derso.viagens.catalogo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/*
 * Esta classe abre espaço para outras tarefas como pesquisa de CEP e geocoding,
 * porém usar de serviço externo aqui é um pouco difícil de sustentar.
 */

// Componente de entidade (não cria a própria tabela)
@Embeddable
public class Endereco {
	
	@Column(length = 8, nullable = false)
	@NotEmpty(message = "O campo CEP é obrigatório")
	@Size(max = 8)
	private String cep;
	
	@Column(length = 150, nullable = false)
	@NotEmpty(message = "O campo Logradouro é obrigatório")
	@Size(max = 150)
	private String logradouro;
	
	@NotNull(message = "O campo Número é obrigatório")
	@Min(1)
	private Integer numero;
	
	@Column(length = 100, nullable = false)
	@Size(max = 100)
	private String complemento;
	
	@Column(length = 50, nullable = false)
	@NotEmpty(message = "O campo Cidade é obrigatório")
	@Size(max = 50)
	private String cidade;
	
	@Column(length = 2, nullable = false)
	@NotEmpty(message = "O campo UF é obrigatório")
	@Size(max = 2)
	private String uf;
	
	public void setCep(String cep) {
		this.cep = Formatador.somenteDigitos(cep);
	}

}
