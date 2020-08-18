package com.brzesky.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brzesky.cursomc.domain.Cidade;
import com.brzesky.cursomc.domain.Cliente;
import com.brzesky.cursomc.domain.Endereco;
import com.brzesky.cursomc.domain.enums.TipoCliente;
import com.brzesky.cursomc.dto.ClienteDTO;
import com.brzesky.cursomc.dto.ClienteNewDTO;
import com.brzesky.cursomc.repositories.ClienteRepository;
import com.brzesky.cursomc.repositories.EnderecoRepository;
import com.brzesky.cursomc.services.exceptions.DataIntegrityException;
import com.brzesky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService 
{
	@Autowired
	private ClienteRepository repositorio;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id)
	{
		Optional<Cliente> obj = repositorio.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
	}
	
	@Transactional
	public Cliente  insert(Cliente obj)
	{
		obj.setId(null);
		
		obj = repositorio.save(obj);
		
		enderecoRepository.saveAll(obj.getEnderecos());
		
		return repositorio.save(obj);
	}

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
	
	public Cliente fromDTO(ClienteNewDTO objDto) 
	{
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
	
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if (objDto.getTelefone2()!=null) 
		{
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj)
	{
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
