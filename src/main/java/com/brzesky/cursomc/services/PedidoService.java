package com.brzesky.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brzesky.cursomc.domain.Pedido;
import com.brzesky.cursomc.repositories.PedidoRepository;
import com.brzesky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService 
{
	@Autowired
	private PedidoRepository repositorio;
	
	public Pedido buscar(Integer id)
	{
		Optional<Pedido> obj = repositorio.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
	}
}
