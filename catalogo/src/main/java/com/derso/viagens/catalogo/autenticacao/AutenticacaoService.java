package com.derso.viagens.catalogo.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.derso.viagens.catalogo.domain.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

// Ref.:
// https://github.com/EdyKnopfler/spring-boot-udemy-spring-security/blob/main/src/main/java/com/derso/security/usuarios/UsuarioServiceImpl.java
@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repositorio;
	
	// Quando um cliente se cadastra, deve ser logado automaticamente 
	public void fazerLoginAutomatico(Usuario usuario, HttpServletRequest request) {
		UserDetails userDetails = usuario.toUserDetails();
		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		
		HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	}
	
	public String usuarioLogado() {
		Authentication usuario = 
				SecurityContextHolder.getContext().getAuthentication();
		
		if (!(usuario instanceof AnonymousAuthenticationToken)) {
			return usuario.getName();
		}
		
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = repositorio
			.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException(
					"Usuário não encontrado: " + email));
		
		return usuario.toUserDetails();
	}

}
