package com.cinyema.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.entidades.Ticket;
import com.cinyema.app.entidades.Usuario;
import com.cinyema.app.servicios.PeliculaServicio;
import com.cinyema.app.servicios.TicketServicio;
import com.cinyema.app.servicios.UsuarioServicio;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/ticket")
public class TicketControlador {

	@Autowired
	private TicketServicio servicioTicket;

	@Autowired
	private PeliculaServicio servicioPelicula;

	@Autowired
	private UsuarioServicio servicioUsuario;

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("")
	public String listar(ModelMap modelo) {
		try {
			modelo.addAttribute("listar", "Lista Tickets");
			modelo.addAttribute("tickets", servicioTicket.listarTicket());
			return "vistas/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			return "vistas/admin/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/registrar")
	public String registrar(ModelMap modelo) {
		try {
			modelo.addAttribute("registrar", "Registrar Ticket");
			modelo.addAttribute("ticket", servicioTicket.crearTicketVac());
			modelo.addAttribute("peliculas", servicioPelicula.listarPeliculas());
			modelo.addAttribute("usuarios", servicioUsuario.buscarUsuarios());
			return "vistas/admin/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "vistas/admin/ticket";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, Ticket ticket) throws Exception {
		try {
			servicioTicket.crearTicket(ticket);
			return "redirect:/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("registrar", "Registrar Ticket");
			modelo.addAttribute("ticket", ticket);
			modelo.put("error", e.getMessage());
			return "vistas/admin/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/editar/{id}")
	public String editar(ModelMap modelo,@PathVariable long id) throws Exception {
		try {
			modelo.addAttribute("editar", "Editar Ticket");
			modelo.addAttribute("ticket", servicioTicket.buscarxId(id));
			modelo.addAttribute("peliculas", servicioPelicula.listarPeliculas());
			modelo.addAttribute("usuarios", servicioUsuario.buscarUsuarios());
			return "vistas/admin/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			return "vistas/admin/ticket";
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@PostMapping("editar/{id}")
	public String editar(ModelMap modelo, Ticket ticket) throws Exception {
		try {
			servicioTicket.modificarTicket(ticket);
			return "redirect:/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("editar", "Editar Ticket");
			modelo.addAttribute("ticket", ticket);
			modelo.put("error", e.getMessage());
			return "vistas/admin/ticket";
		}

	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/eliminar/{id}")
	public String eliminarrTicket(@PathVariable Long idTicket) {
		try {
			servicioTicket.eliminarTicket(idTicket);
			return "redirect:/ticket";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/ticket";
		}
	}
}
