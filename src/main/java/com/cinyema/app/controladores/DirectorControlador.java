package com.cinyema.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cinyema.app.entidades.Director;
import com.cinyema.app.enumeraciones.Pais;
import com.cinyema.app.servicios.DirectorServicio;

@Controller
@RequestMapping("/director")
public class DirectorControlador {

	@Autowired
	DirectorServicio directorServicio;

	@SuppressWarnings("finally")
	@GetMapping("")
	public String mostrarDirectores(ModelMap modelo) throws Exception {

		try {
			List<Director> listaDirector = directorServicio.listarDirectores();
			modelo.addAttribute("listar", "Lista Directores");
			modelo.addAttribute("director", "director");
			modelo.addAttribute("directores", listaDirector);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return "admin/vistas/director";
		}

	}
	
	@GetMapping("/registrar")
	public String guardar() {
		return "admin/vistas/director";
	}
	
	@SuppressWarnings("finally")
	@PostMapping("/registrar")
	public String guardarDirector(ModelMap modelo, @RequestParam("nombre") String nombre, @RequestParam Pais pais) throws Exception {
		
		try {
			Director director = directorServicio.crearDirector(nombre, pais);
			modelo.addAttribute("registrar", "Registro Directores");
			modelo.addAttribute("director", director);
			modelo.put("exito", "Ingreso exitoso");
			return "admin/vistas/director";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Error al ingresar los datos del director");
			return "admin/vistas/director";

		} finally {
			return "admin/vistas/director";
		}
	}

	/*
	@SuppressWarnings("finally")
	@GetMapping("/modificar/{id}")
	public String modificar(@PathVariable Long id, ModelMap modelo) {

		try {
			Director director = directorServicio.verificarDirectorPorId(id);
			modelo.addAttribute("director", director);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			modelo.put("error", "Falta algun dato");
			return "modificar_director";
		} finally {
			return "redirect:/lista_directores";
		}

	}

	@SuppressWarnings("finally")
	@PostMapping("/modificar/{id}")
	public String modificarDirector(ModelMap modelo, @PathVariable Long id, @RequestParam String nombre,
			@RequestParam Pais pais) throws Exception {

		try {
			directorServicio.modificarDirector(id, nombre, pais);
			modelo.put("exito", "Modificacon exitosa");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			modelo.put("error", "Falta algun dato al ingresar un Director");
			return "modificar_directores";
		} finally {
			return "redirect:/lista_directores";
		}
		
	}
	*/
	

}
