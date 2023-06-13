package com.derso.viagens.catalogo.config.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = MaiorDeIdadeValidator.class)
public @interface MaiorDeIdade {

	String message() default "O Cliente deve ser maior de idade";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}