package com.derso.viagens.catalogo.config.validators;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class MaiorDeIdadeTest {
	
	private MaiorDeIdadeValidator validador = new MaiorDeIdadeValidator();
	
	@Test
	public void dataNulaEhValidaDeixamosParaOArrobaNotNull() {
		assertTrue(validador.isValid(null, null));
	}
	
	@Test
	public void alguemMaiorDeIdade() {
		LocalDate dataNascimento = LocalDate.of(1955, 8, 4);
		assertTrue(validador.isValid(dataNascimento, null));
	}
	
	@Test
	public void alguemMenorDeIdade() {
		LocalDate dataNascimento = LocalDate.now().minusYears(10);
		assertFalse(validador.isValid(dataNascimento, null));
	}
	
	@Test
	public void alguemFazendo18AnosHoje() {
		LocalDate dataNascimento = LocalDate.now().minusYears(18);
		assertTrue(validador.isValid(dataNascimento, null));
	}
	
	@Test
	public void alguemQueVaiFazer18AnosAmanha() {
		LocalDate dataNascimento = LocalDate.now().minusYears(18).plusDays(1);
		assertFalse(validador.isValid(dataNascimento, null));
	}

}
