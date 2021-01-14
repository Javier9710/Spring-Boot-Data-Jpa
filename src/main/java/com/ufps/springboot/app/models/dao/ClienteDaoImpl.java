package com.ufps.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.ufps.springboot.app.models.entities.Cliente;

@Repository("clienteDaoJpa")
public class ClienteDaoImpl implements IClienteDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		em.persist(cliente);
		
	}

}
