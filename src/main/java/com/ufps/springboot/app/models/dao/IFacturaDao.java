package com.ufps.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ufps.springboot.app.models.entities.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
	
	@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto p where f.id=?1")
	public Factura fetchByIdClienteItemFacturaProducto(Long id);

}
