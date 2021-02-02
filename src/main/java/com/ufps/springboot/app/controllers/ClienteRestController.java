package com.ufps.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ufps.springboot.app.models.service.IClienteService;
import com.ufps.springboot.app.view.xml.ClienteList;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@RequestMapping(value = {"/listar"}, method = RequestMethod.GET)
	public ClienteList listar() {
		
		return new ClienteList(clienteService.findAll());
	}


}
