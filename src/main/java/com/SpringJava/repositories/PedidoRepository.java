package com.SpringJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringJava.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
