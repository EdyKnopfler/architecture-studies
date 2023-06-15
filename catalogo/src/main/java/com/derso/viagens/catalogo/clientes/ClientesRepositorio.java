package com.derso.viagens.catalogo.clientes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derso.viagens.catalogo.domain.Cliente;

public interface ClientesRepositorio extends JpaRepository<Cliente, Long> {

}