package com.derso.viagens.catalogo.domain;

import jakarta.persistence.Entity;

@Entity
public class Administrador extends Usuario {

	@Override
	public String[] getPapeis() {
		return new String[] {"ADMIN"};
	}

}
