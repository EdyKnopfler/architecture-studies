package com.derso.viagens.catalogo.autenticacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derso.viagens.catalogo.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);

}
