package com.SpringJava.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.SpringJava.domain.Categoria;
import com.SpringJava.domain.Produto;
import com.SpringJava.repositories.CategoriaRepository;
import com.SpringJava.repositories.ProdutoRepository;
import com.SpringJava.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository repoProduto;
	
	@Autowired
	CategoriaRepository repoCategoria;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repoProduto.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = repoCategoria.findAllById(ids);
		return repoProduto.search(nome, categorias, pageRequest);
	}

}
