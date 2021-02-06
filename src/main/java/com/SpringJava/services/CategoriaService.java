package com.SpringJava.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.SpringJava.domain.Categoria;
import com.SpringJava.repositories.CategoriaRepository;
import com.SpringJava.services.exceptions.DataIntegrityException;
import com.SpringJava.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repoCategoria;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repoCategoria.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repoCategoria.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repoCategoria.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repoCategoria.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	}

}
