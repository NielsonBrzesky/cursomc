package com.brzesky.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brzesky.cursomc.domain.Categoria;
import com.brzesky.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource 
{
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) 
	{
		Categoria obj = service.buscar(id);
		
		return ResponseEntity.ok().body(obj);
		
//		Categoria catUm = new Categoria(1, "Informática");
//		Categoria catDois = new Categoria(2, "Escritório");
//		
//		List<Categoria> lista = new ArrayList<>();
//		
//		lista.add(catUm);
//		lista.add(catDois);
//		
//		return lista;
//		 return "O REST ESTÁ FUNCIONANDO!!!";
	}
}
