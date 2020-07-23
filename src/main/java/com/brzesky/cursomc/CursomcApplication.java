package com.brzesky.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.brzesky.cursomc.domain.Categoria;
import com.brzesky.cursomc.domain.Cidade;
import com.brzesky.cursomc.domain.Cliente;
import com.brzesky.cursomc.domain.Endereco;
import com.brzesky.cursomc.domain.Estado;
import com.brzesky.cursomc.domain.Pagamento;
import com.brzesky.cursomc.domain.PagamentoComBoleto;
import com.brzesky.cursomc.domain.PagamentoComCartao;
import com.brzesky.cursomc.domain.Pedido;
import com.brzesky.cursomc.domain.Produto;
import com.brzesky.cursomc.domain.enums.EstadoPagamento;
import com.brzesky.cursomc.domain.enums.TipoCliente;
import com.brzesky.cursomc.repositories.CategoriaRepository;
import com.brzesky.cursomc.repositories.CidadeRepository;
import com.brzesky.cursomc.repositories.ClienteRepository;
import com.brzesky.cursomc.repositories.EnderecoRepository;
import com.brzesky.cursomc.repositories.EstadoRepository;
import com.brzesky.cursomc.repositories.PagamentoRepository;
import com.brzesky.cursomc.repositories.PedidoRepository;
import com.brzesky.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner 
{
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args){SpringApplication.run(CursomcApplication.class, args);}

	@Override
	public void run(String... args) throws Exception 
	{
		Categoria catUm = new Categoria(null, "Informática");
		Categoria catDois = new Categoria(null, "Escritório");
		
		Produto pUm = new Produto(null, "Computador", 2000.00);
		Produto pDois = new Produto(null, "Impressora", 800.00);
		Produto pTres = new Produto(null, "Mouse", 80.00);
		
		catUm.getProdutos().addAll(Arrays.asList(pUm, pDois, pTres));
		catDois.getProdutos().addAll(Arrays.asList(pDois));
		
		pUm.getCategorias().addAll(Arrays.asList(catUm));
		pDois.getCategorias().addAll(Arrays.asList(catUm, catDois));
		pTres.getCategorias().addAll(Arrays.asList(catUm));
		
		categoriaRepository.saveAll(Arrays.asList(catUm, catDois));
		produtoRepository.saveAll(Arrays.asList(pUm, pDois, pTres));
		
		Estado est1 = new Estado(null , "Minas Gerais");
		Estado est2 = new Estado(null , "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail", "4365365567834", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("564756745", "576457654576"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apt 303", "Jardim", "367367777578", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida matos", "105", "Sala 800", "Centro", "5767657932378", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		 clienteRepository.saveAll(Arrays.asList(cli1));
		 enderecoRepository.saveAll(Arrays.asList(e1, e2));
		 
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		 
		 Pedido ped1 = new Pedido(null, sdf.parse("30/09/2018 10:55"), cli1, e1);
		 Pedido ped2 = new Pedido(null, sdf.parse("10/11/2018 22:55"), cli1, e2);
		 
		 Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		 ped1.setPagamento(pagto1);
				 
		 Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/02/2020 00:30"), null);
		 ped2.setPagamento(pagto2);
		 
		 cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		 
		 pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		 pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		 
		 
	}
}













