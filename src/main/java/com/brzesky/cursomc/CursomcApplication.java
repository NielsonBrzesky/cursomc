package com.brzesky.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.brzesky.cursomc.domain.Categoria;
import com.brzesky.cursomc.domain.Produto;
import com.brzesky.cursomc.repositories.CategoriaRepository;
import com.brzesky.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner 
{
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
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
	}
}













