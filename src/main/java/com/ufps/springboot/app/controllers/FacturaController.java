package com.ufps.springboot.app.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufps.springboot.app.models.entities.Cliente;
import com.ufps.springboot.app.models.entities.Factura;
import com.ufps.springboot.app.models.entities.ItemFactura;
import com.ufps.springboot.app.models.entities.Producto;
import com.ufps.springboot.app.models.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {
	
	@Autowired
	private IClienteService clienteService;
	
	private final Logger log=LoggerFactory.getLogger(getClass());

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(clienteId);
		if (cliente==null) {
			flash.addFlashAttribute("error", "El cliente no Existe en la base de datos");
			return "redirect:/listar";
			
		}
		
		Factura factura = new Factura();
		factura.setCliente(cliente);
		
		model.addAttribute("factura",factura);
		model.addAttribute("titulo","Crear Factura");
		
		return "factura/form";
	}
	
	@GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"} )
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		return clienteService.findByNombre(term);
		
		
	}
	
	@PostMapping("/form")
	public String guardar(Factura factura, 
			@RequestParam(name = "item_id[]", required = false) Long[] itemId, 
			@RequestParam(name = "cantidad[]",required = false) Integer[] cantidad,
			RedirectAttributes flash,
			SessionStatus status) {
		
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItem(linea);
			
			log.info("ID: " + itemId[i].toString() + ", Cantidad: " + cantidad[i].toString());
		
		}
		
		clienteService.saveFactura(factura);
		status.setComplete();
		
		flash.addFlashAttribute("success", "Factura Creada con exito!");
		
		return "redirect:/ver/"+ factura.getCliente().getId();
		
		
	}
}
