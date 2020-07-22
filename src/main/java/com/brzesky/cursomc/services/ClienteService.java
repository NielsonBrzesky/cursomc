package com.brzesky.cursomc.services;

import java.util.Optional;

import org.hibernate.ObjectDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brzesky.cursomc.domain.Cliente;
import com.brzesky.cursomc.repositories.ClienteRepository;
import com.brzesky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService 
{
	@Autowired
	private ClienteRepository repositorio;
	
	public Cliente buscar(Integer id)
	{
		Optional<Cliente> obj = repositorio.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
	}
}
