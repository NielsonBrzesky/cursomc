package com.brzesky.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.brzesky.cursomc.domain.Cliente;

public class ClienteDTO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Integer id;	
	
	@NotEmpty(message="Preenchimento obrigatório.")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres.")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatório.")
	@Email(message = "Email Inválido.")
	private String email;
	
	public void ClientDTO(){}
	
	public ClienteDTO(Cliente obj)
	{
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public ClienteDTO(Integer id, String nome, String email) 
	{
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
