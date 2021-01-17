package com.SpringJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringJava.domain.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
