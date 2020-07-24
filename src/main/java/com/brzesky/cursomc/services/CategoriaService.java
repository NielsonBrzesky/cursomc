package com.brzesky.cursomc.services;

import java.util.Optional;

import org.hibernate.ObjectDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brzesky.cursomc.domain.Categoria;
import com.brzesky.cursomc.repositories.CategoriaRepository;
import com.brzesky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService 
{
	@Autowired
	private CategoriaRepository repositorio;
	
	public Categoria buscar(Integer id)
	{
		Optional<Categoria> obj = repositorio.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
	}
	
	public Categoria  insert(Categoria obj)
	{
		obj.setId(null);
		return repositorio.save(obj);
	}
}
