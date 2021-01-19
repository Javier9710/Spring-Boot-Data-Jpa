package com.ufps.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufps.springboot.app.models.entities.Cliente;
import com.ufps.springboot.app.models.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		
		Pageable pageR = PageRequest.of(page, 4);
		Page<Cliente> clientes = clienteService.findAll(pageR);
		
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clientes);
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
	public String editar(@PathVariable(value = "id") Long id,Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id>0) {
			cliente = clienteService.findOne(id);
			if (cliente==null) {
				flash.addFlashAttribute("error", "El Cliente no Existe en la Base de Datos");
				return "redirect:/listar";
				
			}
			
		}else {
			return "redirect:/listar";
			
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		
		return "form"; 
	}
	

	@PostMapping("form")
	public String guardar(@Valid  Cliente cliente, BindingResult result, Model model,RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario del Cliente");
			return "form";
		}
		
		String mensaje=(cliente.getId()!=null)? "Cliente Editado con exito" : "Cliente Creado con exito";

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:listar";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id,RedirectAttributes flash) {
		if (id>0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "Se ha Eliminado con Exito");
			
		}
		return "redirect:/listar";
	}
}
