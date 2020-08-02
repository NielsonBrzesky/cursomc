package com.brzesky.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.brzesky.cursomc.domain.Categoria;
import com.brzesky.cursomc.dto.CategoriaDTO;
import com.brzesky.cursomc.repositories.CategoriaRepository;
import com.brzesky.cursomc.services.exceptions.DataIntegrityException;
import com.brzesky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService 
{
	@Autowired
	private CategoriaRepository repositorio;
	
	public Categoria find(Integer id)
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

	public Categoria update(Categoria obj)
	{
		find(obj.getId());
		return repositorio.save(obj);
	}

	public void delete(Integer id)
	{
		find(id);//Chama o metodo find para verificar se o id existe se não ele entre nas classes de tratamento de excessão.
		try 
		{
			repositorio.deleteById(id);			
		}
		catch (DataIntegrityViolationException e) 
		{
			throw new DataIntegrityException("Não é possivel excluir um valor com vinculos em outras tabelas!");
		}
	}
	
	public List<Categoria> findAll() 
	{
		return repositorio.findAll();
	}
	
	//Metodo de Paginação.
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
		
	}
	
	public Categoria fromDTO(CategoriaDTO objDto)
	{
		return new Categoria(objDto.getId(), objDto.getNome());
	}

}












