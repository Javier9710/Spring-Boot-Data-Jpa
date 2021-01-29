package com.ufps.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ufps.springboot.app.models.entities.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);

}
