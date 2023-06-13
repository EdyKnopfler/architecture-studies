package com.derso.viagens.catalogo.domain;

public class Formatador {
	
	public static String somenteDigitos(String valorCampo) {
		return valorCampo.replaceAll("\\D+","");
	}

}
