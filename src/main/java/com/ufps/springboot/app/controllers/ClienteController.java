package com.ufps.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ufps.springboot.app.models.dao.IClienteDao;
import com.ufps.springboot.app.models.entities.Cliente;

@Controller
@SessionAttributes("cliente")
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
	
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id,Map<String, Object> model) {
		Cliente cliente = null;
		if (id>0) {
			cliente = clienteDao.findOne(id);
			
		}else {
			return "redirect:/listar";
			
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		
		return "form"; 
	}
	

	@PostMapping("form")
	public String guardar(@Valid  Cliente cliente, BindingResult result, Model model, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario del Cliente");
			return "form";
		}

		clienteDao.save(cliente);
		status.setComplete();
		return "redirect:listar";
	}
}
