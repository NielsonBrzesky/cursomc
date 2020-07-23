package com.brzesky.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.brzesky.cursomc.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)//Mapeia a herança com tabelão ou com tabelas separadas, no caso aqui é uma tabela pra cada subclasse.
public abstract class Pagamento implements Serializable//A classe está como abstrata para garantir que não sejam instaciaods objetos do tipo pagamento. 
{											//Para que toda vez que for instaciado um objeto do tipo pagamento seja utilizado um new, e nunca diretamente.
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;//O id do pagamento vai se o mesmo do pedido correspondente.
	private Integer estado;
	
	@JsonBackReference
	@OneToOne
	@JoinColumn(name = "pedido_id")
	@MapsId//Garante que o id do pedido seja o mesmo do pagamento.
	private Pedido pedido;
	
	public Pagamento(){}

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) 
	{
		super();
		this.id = id;
		this.estado = estado.getCod();
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
