package com.ufps.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ufps.springboot.app.models.entities.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long> {

}
