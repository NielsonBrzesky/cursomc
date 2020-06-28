package com.brzesky.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brzesky.cursomc.domain.Categoria;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource 
{
	@RequestMapping(method = RequestMethod.GET)
	 public List<Categoria> listar() 
	 {
		Categoria catUm = new Categoria(1, "Informática");
		Categoria catDois = new Categoria(2, "Escritório");
		
		List<Categoria> lista = new ArrayList<>();
		
		lista.add(catUm);
		lista.add(catDois);
		
		return lista;
//		 return "O REST ESTÁ FUNCIONANDO!!!";
	 }
}
