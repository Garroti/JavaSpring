package com.SpringJava.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringJava.domain.Cliente;
import com.SpringJava.repositories.ClienteRepository;
import com.SpringJava.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repoCategoria;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repoCategoria.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

}
