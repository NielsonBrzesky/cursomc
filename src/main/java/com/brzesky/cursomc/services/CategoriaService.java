package com.brzesky.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brzesky.cursomc.domain.Categoria;
import com.brzesky.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService 
{
	@Autowired
	private CategoriaRepository repositorio;
	
	public Categoria buscar(Integer id)
	{
		Optional<Categoria> obj = repositorio.findById(id);
		
		return obj.orElse(null);
	}
}
