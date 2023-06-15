package com.derso.viagens.catalogo.autenticacao;

import com.derso.viagens.catalogo.domain.Usuario;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	
	private Long id;
	
	@NotEmpty(message = "O campo Nome é obrigatório")
	@Size(max = 100, message = "Comprimento máximo do nome: 100")
	private String nome;
	
	@NotEmpty(message = "O campo E-mail é obrigatório")
	@Size(max = 100, message = "Comprimento máximo do e-mail: 100")
	private String email;
	
	@NotEmpty(message = "O campo Senha é obrigatório")
	@Size(max = 50, message = "Comprimento máximo da senha: 50")
	private String senha;
	
	public static void preencher(UsuarioDTO dto, Usuario usuario) {
		usuario.setId(dto.getId());
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());
	}

}
