package com.cinyema.app.servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.repositorios.TicketRepositorio;

@Service
public class TicketServicio {
	
	@Autowired
	private TicketRepositorio repTic;
	
	@Transactional
	public Ticket crearTicket(Pelicula pel, Usuario usu, String fecha1, String lugar, Double precio) throws Exception {
		
		Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
		
		validar(pel, usu, fecha, lugar, precio);
		
		Ticket tic = new Ticket();
		
		tic.setPelicula(pel);
		tic.setUsuario(usu);
		tic.setFecha(fecha);
		tic.setLugar(lugar);
		tic.setPrecio(precio);
		
		return repTic.save(tic);
	}
	
	@Transactional
	public void eliminarTicket(Long id) {
		
		repTic.deleteById(id);
	}
	
	@Transactional
	public void modificarTicket(Long id, Pelicula pel, Usuario usu, Date fecha, String lugar, Double precio) {
		
		validar(pel, usu, fecha, lugar, precio);
		
		Ticket tic = repTic.findById(id).get();
		
		tic.setPelicula(pel);
		tic.setUsuario(usu);
		tic.setFecha(fecha);
		tic.setLugar(lugar);
		tic.setPrecio(precio);
		
		repTic.save(tic);
	}
	
	@Transactional
	public List<Ticket> listarTicket() {
		List<Ticket> listaTickets = repTic.findAll();
		return listaTickets;
	}
	
	@Transactional
	public Ticket buscarxId(Long id) {
		Ticket tic = repTic.getById(id);
		return tic;
	}
	
	private void validar(Pelicula pel, Usuario usu, Date fecha, String lugar, Double precio) throws Error {

        if (pel == null ) {
            throw new Error("No se encuentra a que película pertenece el ticket");
        }
        if (usu == null ) {
            throw new Error("No se encuentra a que usuario pertenece el ticket");
        }

        if (fecha == null ) {
            throw new Error("Debe indicar la fecha");
        }

        if (lugar == null || lugar.trim().isEmpty()) {
            throw new Error("Debe ingresar el lugar");
        }

        if (precio == null ) {
            throw new Error("El campo 'precio' no puede estar vacío");
        }

        
    }
}