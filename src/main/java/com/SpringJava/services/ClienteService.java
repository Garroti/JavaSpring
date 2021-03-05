package com.SpringJava.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.SpringJava.DTO.ClienteDTO;
import com.SpringJava.DTO.ClienteNewDTO;
import com.SpringJava.domain.Cidade;
import com.SpringJava.domain.Cliente;
import com.SpringJava.domain.Endereco;
import com.SpringJava.domain.enums.TipoCliente;
import com.SpringJava.repositories.ClienteRepository;
import com.SpringJava.repositories.EnderecoRepository;
import com.SpringJava.services.exceptions.DataIntegrityException;
import com.SpringJava.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repoCliente;
	
	@Autowired
	private EnderecoRepository repoEndereco;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repoCliente.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repoCliente.save(obj);
		repoEndereco.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repoCliente.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repoCliente.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente com pedidos relacionados");
		}
	}

	public List<Cliente> findAll() {
		return repoCliente.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repoCliente.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO obj) {
		Cliente cli = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(), TipoCliente.toEnum(obj.getTipo()));
		Cidade cid = new Cidade(obj.getCidadeId(), null, null);
		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), obj.getBairro(), obj.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(obj.getTelefone1());
		if (obj.getTelefone2() != null) {
			cli.getTelefones().add(obj.getTelefone2());
		}
		if (obj.getTelefone3() != null) {
			cli.getTelefones().add(obj.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
