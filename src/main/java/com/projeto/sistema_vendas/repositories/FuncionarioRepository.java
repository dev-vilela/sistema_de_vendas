package com.projeto.sistema_vendas.repositories;

import com.projeto.sistema_vendas.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
