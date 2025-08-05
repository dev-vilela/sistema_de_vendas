package com.projeto.sistema_vendas.repositories;

import com.projeto.sistema_vendas.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
