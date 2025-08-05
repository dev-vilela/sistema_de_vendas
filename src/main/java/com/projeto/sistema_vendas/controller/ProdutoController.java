package com.projeto.sistema_vendas.controller;

import com.projeto.sistema_vendas.model.Estado;
import com.projeto.sistema_vendas.model.Produto;
import com.projeto.sistema_vendas.repositories.EstadoRepository;
import com.projeto.sistema_vendas.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    //CRUD

    //CADASTRAR
    @GetMapping("/cadastroProduto")
    public ModelAndView cadastrar(Produto produto){
        ModelAndView mv = new ModelAndView("administrativo/produto/cadastro");
        mv.addObject("produto", produto);
        return mv;
    }

    //LISTAR
    @GetMapping("/listarProduto")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/produto/lista");
        mv.addObject("listaProduto", produtoRepository.findAll());
        return mv;
    }

    //EDITAR
    @GetMapping("/editarProduto/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return cadastrar(produto.get());
    }

    //REMOVER
    @GetMapping("/removerProduto/{id}")
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        produtoRepository.delete(produto.get());
        return listar();
    }

    //SALVAR
    @PostMapping("/salvarProduto")
    public ModelAndView salvar(Produto produto, BindingResult result){
        if (result.hasErrors()){
            return cadastrar(produto);
        }
        produtoRepository.saveAndFlush(produto);
        return cadastrar(new Produto());
    }
}
