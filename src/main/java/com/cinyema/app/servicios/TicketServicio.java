package com.cinyema.app.servicios;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.repositorios.PeliculaRepositorio;
import com.cinyema.app.repositorios.TicketRepositorio;

@Service
public class TicketServicio implements ServicioBase<Ticket> {
	
	@Autowired
	private TicketRepositorio repositorioTicket;
	
	@Autowired
	private PeliculaRepositorio repositorioPelicula;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Ticket registrar(Ticket ticket) throws Error, Exception {
		validar(ticket);
		if(validarFechaCompra(ticket) == true) {
			return repositorioTicket.save(ticket);
		}else {
			throw new Error("No puede comprar un ticket con fecha anterior al dia de hoy");
		}
	}
	
	@Transactional
	public Ticket registrarVacio() {
		return new Ticket();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminar(Long id) {
		repositorioTicket.deleteById(id);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Ticket editar(Ticket ticket) throws Exception {
		validar(ticket);
		return repositorioTicket.save(ticket);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Ticket> listar() {
		List<Ticket> listaTickets = repositorioTicket.findAll();
		return listaTickets;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Ticket obtenerPorId(Long id) throws Exception {
		Optional<Ticket> result = repositorioTicket.findById(id);
	    if(result.isEmpty()) {
	    	throw new Exception("No se encontro");
	    }else {
		Ticket ticket = result.get();
		return ticket;
	    }
	}
	
	public Boolean validarFechaCompra(Ticket ticket) throws Exception{
		Date d1 = new Date();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(d1);
		Date date1 = sdf.parse(date);
		if(date1.before(ticket.getFecha())) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public List<Ticket> listarTicketxPelicula(Pelicula pelicula) throws Exception{
		Optional<Pelicula> result = repositorioPelicula.findById(pelicula.getIdPelicula());
		if(result.isEmpty()) {
			throw new Exception("No se encontró la película");
		}else {
			List<Ticket> listaTickets = repositorioTicket.listarTicketsxPelicula(pelicula.getIdPelicula());
			
			return listaTickets;
		}		
	}
	
	
	public String contarTicketxPelicula(Pelicula pelicula) throws Exception{
		Optional<Pelicula> result = repositorioPelicula.findById(pelicula.getIdPelicula());
		if(result.isEmpty()) {
			throw new Exception("No se encontró la película");
		}else {
			List<Ticket> listaTickets = repositorioTicket.listarTicketsxPelicula(pelicula.getIdPelicula());
			String numeroTickets = Integer.toString(listaTickets.size());
			return numeroTickets;
		}
	}
	
	private void validar(Ticket ticket) throws Error {

        if (ticket.getPelicula() == null ) {
            throw new Error("No se encuentra a que película pertenece el ticket");
        }
        if (ticket.getUsuario() == null ) {
            throw new Error("No se encuentra a que usuario pertenece el ticket");
        }

        if (ticket.getFecha() == null ) {
            throw new Error("Debe indicar la fecha");
        }

        if (ticket.getLugar() == null || ticket.getLugar().trim().isBlank()) {
            throw new Error("Debe ingresar el lugar");
        }

        if (ticket.getPrecio() == null ) {
            throw new Error("El campo 'precio' no puede estar vacío");
        }
        
    }		
}