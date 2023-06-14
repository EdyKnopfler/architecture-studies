package com.derso.viagens.catalogo.autenticacao;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
 * CONFIGURAÇÕES DO SPRING SECURITY
 * 
 * Referência dos estudos:
 * https://github.com/EdyKnopfler/spring-boot-udemy-spring-security/blob/main/src/main/java/com/derso/security/config/SegurancaConfig.java
 * 
 * Ler o README.md do projeto!
 */

@Configuration
public class SegurancaConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.authorizeHttpRequests(
				autorizacao -> autorizacao
					.requestMatchers(antMatcher("/admin/**")).hasRole("ADMIN")
					.requestMatchers("/area-do-cliente").authenticated()
					.anyRequest().permitAll()
			)
			.formLogin(Customizer.withDefaults())
			.build();
	}
	
}
