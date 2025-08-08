package com.projeto.sistema_vendas.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Entrada implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String obs;
    private Double valorTotal = 0.00;
    private Double QtdTotal = 0.00;
    private Date dataEntrada = new Date();

    @ManyToOne //FK
    private Fornecedor fornecedor;
    @ManyToOne
    private Funcionario funcionario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getQtdTotal() {
        return QtdTotal;
    }

    public void setQtdTotal(Double qtdTotal) {
        QtdTotal = qtdTotal;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
