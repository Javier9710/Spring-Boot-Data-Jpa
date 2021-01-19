package com.ufps.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ufps.springboot.app.models.entities.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

}
