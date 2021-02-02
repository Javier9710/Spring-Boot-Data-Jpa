package com.ufps.springboot.app.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.ufps.springboot.app.models.entities.Cliente;
import com.ufps.springboot.app.models.service.IClienteService;
import com.ufps.springboot.app.models.service.IUploadFileService;
import com.ufps.springboot.app.util.paginador.PageRender;
import com.ufps.springboot.app.view.xml.ClienteList;





@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	private final Log logger=LogFactory.getLog(getClass());

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadsService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Secured("ROLE_USER")
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<org.springframework.core.io.Resource> verfoto(@PathVariable String filename) {

		org.springframework.core.io.Resource recurso = null;
		try {
			recurso =  uploadsService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok().header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + recurso.getFilename() + "\"").body(recurso);

	}

	//@Secured("ROLE_USER")
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = clienteService.fetchByIdFactura(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la bd");
			return "redirect:/listar";

		}

		model.put("cliente", cliente);
		model.put("titulo", "Detalle Cliente: " + cliente.getNombre());

		return "ver";

	}
	
	@RequestMapping(value = {"/listar-rest"}, method = RequestMethod.GET)
	public @ResponseBody ClienteList listarRest() {
		
		return new ClienteList(clienteService.findAll());
	}

	@RequestMapping(value = {"/listar","/"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, 
			Model model, 
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {

		if (authentication!=null) {
			logger.info("hola Usuario Autenticado".concat(authentication.getName()));
			
		}
		
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		
		if (auth!=null) {
			logger.info("hola Usuario Autenticado: ".concat(auth.getName()).concat(" Utilizando Metodo Estatico"));
			
		}
		
		if (this.hasRole("ROLE_ADMIN")) {
			logger.info("hola Usuario Autenticado: ".concat(auth.getName()).concat(" Tienes acceso"));
		}else {
			logger.info("hola Usuario Autenticado: ".concat(auth.getName()).concat(" No tienes Acceso"));
			
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
		if (securityContext.isUserInRole("ADMIN")) {
			
			logger.info("Usando SecurityContextHolderAwareRequestWrapper: ".concat(auth.getName()));
		}else {
			
			logger.info("Usando SecurityContextHolderAwareRequestWrapper: ".concat(auth.getName()).concat(" No tiene acceso"));
		}
		
		if (request.isUserInRole("ROLE_ADMIN")) {
			
			logger.info("Usando HttpServletRequest: ".concat(auth.getName()));
		}else {
			
			logger.info("Usando HttpServletRequest: ".concat(auth.getName()).concat(" No tiene acceso"));
		}
		
		
		
		Pageable pageR = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.findAll(pageR);

		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de cliente");

		return "form";
	}

	//@Secured("ROLE_ADMIN")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_XXX')")
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El Cliente no Existe en la Base de Datos");
				return "redirect:/listar";

			}

		} else {
			return "redirect:/listar";

		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");

		return "form";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario del Cliente");
			return "form";
		}

		if (!foto.isEmpty()) {
			// Path direcctorio = Paths.get("src//main//resources//static/uploads");
			// String rootPath = "C://Temp//uploads";

			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {

				uploadsService.delete(cliente.getFoto());
			}

			String uniqueFile = null;
			try {
				uniqueFile = uploadsService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flash.addFlashAttribute("info", "Ha subido correctamente: '" + foto.getOriginalFilename() + "'");

			cliente.setFoto(uniqueFile);

		}

		String mensaje = (cliente.getId() != null) ? "Cliente Editado con exito" : "Cliente Creado con exito";

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);

			clienteService.delete(id);
			flash.addFlashAttribute("success", "Se ha Eliminado con Exito");

			if (uploadsService.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info", "foto: " + cliente.getFoto() + " Eliminada con exito");

			}

		}
		return "redirect:/listar";
	}
	
	
	private boolean hasRole(String rol) {
		
		SecurityContext context= SecurityContextHolder.getContext();
		if (context==null) {
			return false;
			
		}
		Authentication auth = context.getAuthentication();
		if (auth==null) {
			return false;
			
		}
		Collection<?  extends GrantedAuthority> authorities = auth.getAuthorities();
		
		for (GrantedAuthority authority : authorities) {
			if (rol.equals(authority.getAuthority())) {
				logger.info("hola Usuario Autenticado: ".concat(auth.getName()).concat(" Tu rol es: ".concat(authority.getAuthority())));
				return true;
				
			}
		}
		return false;
	}
	
	
}
