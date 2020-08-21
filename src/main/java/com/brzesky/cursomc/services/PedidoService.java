package com.brzesky.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brzesky.cursomc.domain.ItemPedido;
import com.brzesky.cursomc.domain.PagamentoComBoleto;
import com.brzesky.cursomc.domain.Pedido;
import com.brzesky.cursomc.domain.enums.EstadoPagamento;
import com.brzesky.cursomc.repositories.ItemPedidoRepository;
import com.brzesky.cursomc.repositories.PagamentoRepository;
import com.brzesky.cursomc.repositories.PedidoRepository;
import com.brzesky.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService 
{
	@Autowired
	private PedidoRepository repositorio;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	public Pedido find(Integer id)
	{
		Optional<Pedido> obj = repositorio.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
	}
	
	@Transactional
	public Pedido insert(Pedido obj) 
	{
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
	
		if (obj.getPagamento() instanceof PagamentoComBoleto) 
		{
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repositorio.save(obj);
		
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) 
		{
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		
		return obj;
	}
}
