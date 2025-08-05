package com.projeto.sistema_vendas.repositories;

import com.projeto.sistema_vendas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
