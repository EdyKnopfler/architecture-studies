package com.derso.viagens.catalogo.admin.administradores;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derso.viagens.catalogo.domain.Administrador;

public interface AdministradoresRepository extends JpaRepository<Administrador, Long> {

}
