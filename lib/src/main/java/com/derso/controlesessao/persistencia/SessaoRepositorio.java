package com.derso.controlesessao.persistencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessaoRepositorio extends JpaRepository<Sessao, String> {
	
	@Modifying
	@Query("UPDATE Sessao s SET s.estado = :novoEstado WHERE s.uuid = :uuid")
	void atualizarEstado(@Param("uuid") String uuid, @Param("novoEstado") EstadoSessao novoEstado);
	
	@Query("""
		SELECT s FROM Sessao s 
		WHERE s.estado = 'CORRENDO' 
		AND s.expiracao < CURRENT_TIMESTAMP 
	""")
	List<Sessao> sessoesExpiradasCorrendo();

	@Modifying
	@Query("DELETE FROM Sessao s WHERE s.estado NOT IN ('CORRENDO', 'PAUSADA')")
	void removerInvalidos();

}
