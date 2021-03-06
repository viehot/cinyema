package com.cinyema.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Ticket;

@Repository
public interface TicketRepositorio extends JpaRepository<Ticket, Long> {

	@Query("SELECT t FROM Ticket t WHERE t.pelicula.idPelicula = :idPelicula")
	public List<Ticket> listarTicketsxPelicula(@Param("idPelicula") Long idPelicula);
	
	
}
