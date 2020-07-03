package com.brzesky.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.brzesky.cursomc.domain.Categoria;
import com.brzesky.cursomc.domain.Cidade;
import com.brzesky.cursomc.domain.Estado;
import com.brzesky.cursomc.domain.Produto;
import com.brzesky.cursomc.repositories.CategoriaRepository;
import com.brzesky.cursomc.repositories.CidadeRepository;
import com.brzesky.cursomc.repositories.EstadoRepository;
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
	}
}













