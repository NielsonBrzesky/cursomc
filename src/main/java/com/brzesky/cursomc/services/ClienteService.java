package com.brzesky.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.brzesky.cursomc.domain.Cliente;
import com.brzesky.cursomc.dto.ClienteDTO;
import com.brzesky.cursomc.repositories.ClienteRepository;
import com.brzesky.cursomc.services.exceptions.DataIntegrityException;
import com.brzesky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService 
{
	@Autowired
	private ClienteRepository repositorio;
	
	public Cliente find(Integer id)
	{
		Optional<Cliente> obj = repositorio.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
	}
	
//	public Cliente  insert(Cliente obj)
//	{
//		obj.setId(null);
//		return repositorio.save(obj);
//	}

	public Cliente update(Cliente obj)
	{
		Cliente newObj = find(obj.getId());
		
		updateData(newObj, obj);
		
		return repositorio.save(newObj);
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
	
	public List<Cliente> findAll() 
	{
		return repositorio.findAll();
	}
	
	//Metodo de Paginação.
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
		
	}
	
	public Cliente fromDTO(ClienteDTO objDto)
	{
//		throw new UnsupportedOperationException();
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj)
	{
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
