package com.cinyema.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Funcion;

@Repository
public interface FuncionRepositorio extends JpaRepository<Funcion, Long>{

}