package com.SpringJava.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringJava.domain.Pedido;
import com.SpringJava.repositories.PedidoRepository;
import com.SpringJava.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repoPedido;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repoPedido.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
