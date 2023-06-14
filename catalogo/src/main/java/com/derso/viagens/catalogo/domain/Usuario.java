package com.derso.viagens.catalogo.domain;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/*
 * Herança com JPA
 * 
 * Estratégia escolhida: Joined Table (cada classe tem sua tabela)
 * 
 * https://www.baeldung.com/hibernate-inheritance
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Usuario {
	
	/*
	 Quanto menos "anêmico" o modelo, melhor na minha opinião.
	 
	 Povo fica hypando O.O. e fazendo classes de domínio só com getters e setters
	 e classes de serviço que manipulam esses objetos.
	 
	 Toda lógica que é relativa a usuário deveria estar na classe Usuário, simples assim.
	 Ex.: a senha do usuário deve ser encriptada com o algoritmo BCrypt. É AQUI que eu vou
	 fazer e quem vier com "padrões de projeto" vai tomar cascudo. 
	*/
	private static final PasswordEncoder encoder = 
			new BCryptPasswordEncoder(12);
	
	// https://cursos.alura.com.br/forum/topico-porque-usar-generatedvalue-strategy-generationtype-identity-para-mysql-103403
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	@NotEmpty(message = "O campo Nome é obrigatório")
	@Size(max = 100, message = "Comprimento máximo do nome: 100")
	private String nome;
	
	@Column(length = 100, nullable = false)
	@NotEmpty(message = "O campo E-mail é obrigatório")
	@Size(max = 100, message = "Comprimento máximo do e-mail: 100")
	private String email;
	
	@Transient
	@NotEmpty(message = "O campo Senha é obrigatório")
	@Size(max = 50, message = "Comprimento máximo da senha: 50")
	private String senha;
	
	@Column(length = 60, nullable = false)
	private String senhaCriptografada;
	
	public void setSenha(String senha) {
		// REGRA DE NEGÓCIO aqui
		// Criptografia da senha
		this.senha = senha;
		this.senhaCriptografada = encoder.encode(senha);
	}
	
	public UserDetails toUserDetails() {
		// Isto é um acoplamento com o framework? É.
		// Mas não gosto de quebrar demais as coisas e ficar com milhões de pedacinhos granulados.
		// Faria a separação se esta classe crescesse demais e ficasse complexa.
		return User.builder()
				.username(email)
				.password("{bcrypt}" + senhaCriptografada)
				.roles(getPapeis())
				.build();
	}

	public abstract String[] getPapeis();
	
}
