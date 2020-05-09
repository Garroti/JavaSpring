package com.SpringJava.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringJava.domain.Categoria;
import com.SpringJava.repositories.CategoriaRepository;
import com.SpringJava.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repoCategoria;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repoCategoria.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

}
