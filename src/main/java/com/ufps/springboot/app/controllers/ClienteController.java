package com.ufps.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufps.springboot.app.models.entities.Cliente;
import com.ufps.springboot.app.models.service.IClienteService;
import com.ufps.springboot.app.util.paginador.PageRender;

import ch.qos.logback.classic.Logger;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;
	
	private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id")Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		if (cliente==null) {
			flash.addFlashAttribute("error", "El cliente no existe en la bd");
			return "redirect:/listar";
			
		}
		
		model.put("cliente", cliente);
		model.put("titulo", "Detalle Cliente: " + cliente.getNombre());
		
		return "ver";
		
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page", defaultValue = "0") int page, Model model) {
		
		Pageable pageR = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.findAll(pageR);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender );
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
	public String guardar(@Valid  Cliente cliente, BindingResult result, Model model,@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario del Cliente");
			return "form";
		}
		
		if (!foto.isEmpty()) {
			//Path direcctorio = Paths.get("src//main//resources//static/uploads");
			//String rootPath = "C://Temp//uploads";
			String uniqueFile = UUID.randomUUID().toString()+ "_" + foto.getOriginalFilename();
			
			Path rootPath = Paths.get("uploads").resolve(uniqueFile);
			
			Path rootAdsolut = rootPath.toAbsolutePath(); 
			
			log.info("rootPath: " + rootPath);
			log.info("rootAdsolut: " + rootAdsolut);
			
			try {
				Files.copy(foto.getInputStream(), rootAdsolut);
				flash.addFlashAttribute("info", "Ha subido correctamente: '"+ foto.getOriginalFilename()+"'");
				
				cliente.setFoto(uniqueFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
