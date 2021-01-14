package com.ufps.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ufps.springboot.app.models.dao.IClienteDao;
import com.ufps.springboot.app.models.entities.Cliente;

@Controller
public class ClienteController {
	
	@Autowired
	@Qualifier("clienteDaoJpa")
	private IClienteDao clienteDao;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}
	
	@GetMapping("form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de cliente");
		
		
		return "form";
	}
	
	
	@PostMapping("form")
	public String guardar(Cliente cliente) {
		clienteDao.save(cliente);
		
		
		return "redirect:listar";
	}
}
