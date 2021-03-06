package com.cinyema.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Asiento;

@Repository
public interface AsientoRepositorio extends JpaRepository<Asiento, Long>{

	@Query("SELECT a FROM Asiento a where a.numeroDeAsiento = :numeroDeAsiento")
	public Asiento buscarPorNumeroDeAsiento(@Param("numeroDeAsiento") Integer numeroDeAsiento);
}
