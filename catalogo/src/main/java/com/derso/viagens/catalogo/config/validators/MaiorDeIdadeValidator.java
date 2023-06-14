package com.derso.viagens.catalogo.config.validators;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaiorDeIdadeValidator implements ConstraintValidator<MaiorDeIdade, TemporalAccessor> {

	@Override
	public boolean isValid(TemporalAccessor dataNascimento, ConstraintValidatorContext context) {
		if (dataNascimento == null) {
			return true;
		}
		
		LocalDate hoje = LocalDate.now();
		int anoHoje = hoje.get(ChronoField.YEAR);
		int diaHoje = hoje.get(ChronoField.DAY_OF_YEAR);
		int anoValor = dataNascimento.get(ChronoField.YEAR);
		int diaValor = dataNascimento.get(ChronoField.DAY_OF_YEAR);
		int anosPassados = anoHoje - anoValor;
		return anosPassados > 18 || anosPassados == 18 && diaHoje >= diaValor;
	}

}
