package com.projeto.sistema_vendas.repositories;


import com.projeto.sistema_vendas.model.ItemEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemEntradaRepository extends JpaRepository<ItemEntrada, Long> {

    @Query("select e from ItemEntrada e where e.entrada.id = ?1")
    List<ItemEntrada>buscarPorEntrada(long id);

}
