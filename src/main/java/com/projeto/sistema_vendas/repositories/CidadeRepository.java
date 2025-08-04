package com.projeto.sistema_vendas.repositories;

import com.projeto.sistema_vendas.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
