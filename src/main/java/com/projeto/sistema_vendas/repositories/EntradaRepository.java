package com.projeto.sistema_vendas.repositories;

import com.projeto.sistema_vendas.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
}
