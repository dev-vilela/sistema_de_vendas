package com.projeto.sistema_vendas.repositories;


import com.projeto.sistema_vendas.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
