package com.cinyema.app.servicios;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cinyema.app.entidades.Actor;
import com.cinyema.app.repositorios.ActorRepositorio;

@Service
public class ActorServicio implements ServicioBase<Actor> {

	@Autowired
	private ActorRepositorio actorRepositorio;

	@Override
	@Transactional
	public Actor registrar(Actor actor) throws Exception {
		validar(actor);
		return actorRepositorio.save(actor);
	}

	@Transactional
	public Actor registrarVacio() {
		return new Actor();
	}

	@Override
	@Transactional
	public Actor editar(Actor actor) throws Exception {
		validar(actor);
		return actorRepositorio.save(actor);
	}

	@Override
	@Transactional
	public List<Actor> listar() {
		return actorRepositorio.findAll();
	}

	@Override
	@Transactional
	public Actor obtenerPorId(Long id) throws Exception {
		Optional<Actor> result = actorRepositorio.findById(id);
		if (result.isEmpty()) {
			throw new Exception("No se encontró");
		} else {
			return result.get();
		}
	}
	

	@Transactional
	public Actor obtenerActorPorNombre(String nombreCompleto) throws Exception {
		Optional<Actor> result = actorRepositorio.buscarPorNombre(nombreCompleto);
		if (result.isEmpty()) {
			throw new Exception("No se encontró");
		} else {
			return result.get();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void eliminarPorId(Long id) throws Exception {
		actorRepositorio.deleteById(id);
		
	}

	public void validar(Actor actor) throws Exception {
		if (actor.getNombreCompleto() == null || actor.getNombreCompleto().isBlank()) {
			throw new Exception("Nombre completo inválido");
		}
		if (actor.getPais() == null) {
			throw new Exception("País inválido");
		}

	}

}
