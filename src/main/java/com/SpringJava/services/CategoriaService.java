package com.SpringJava.services;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.SpringJava.DTO.CategoriaDTO;
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
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repoCategoria.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repoCategoria.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll() {
		return repoCategoria.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repoCategoria.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO obj) {
		return new Categoria(obj.getId(), obj.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}

}
