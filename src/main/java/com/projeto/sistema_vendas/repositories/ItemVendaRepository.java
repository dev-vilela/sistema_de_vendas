package com.projeto.sistema_vendas.repositories;



import com.projeto.sistema_vendas.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    @Query("select e from ItemVenda e where e.venda.id = ?1")
    List<ItemVenda>buscarPorVenda(long id);

}
