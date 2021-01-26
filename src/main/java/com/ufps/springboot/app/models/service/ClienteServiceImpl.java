package com.ufps.springboot.app.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ufps.springboot.app.models.dao.IClienteDao;
import com.ufps.springboot.app.models.dao.IFacturaDao;
import com.ufps.springboot.app.models.dao.IProductoDao;
import com.ufps.springboot.app.models.entities.Cliente;
import com.ufps.springboot.app.models.entities.Factura;
import com.ufps.springboot.app.models.entities.Producto;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	public IClienteDao clienteDao;

	@Autowired
	private IProductoDao productoDao;

	@Autowired
	private IFacturaDao facturaDao;

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);

	}

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Cliente findOne(Long id) {

		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);

	}

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {

		return clienteDao.findAll(pageable);
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		
		return productoDao.findById(id).orElse(null);
	}

}
