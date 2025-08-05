package com.projeto.sistema_vendas.controller;

import com.projeto.sistema_vendas.model.Fornecedor;
import com.projeto.sistema_vendas.repositories.CidadeRepository;
import com.projeto.sistema_vendas.repositories.ForncedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class FornecedorController {

    @Autowired
    private ForncedorRepository forncedorRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    //CRUD

    //CADASTRAR
    @GetMapping("/cadastroFornecedor")
    public ModelAndView cadastrar(Fornecedor fornecedor){
        ModelAndView mv = new ModelAndView("administrativo/fornecedor/cadastro");
        mv.addObject("fornecedor", fornecedor);
        mv.addObject("listaCidades", cidadeRepository.findAll());
        return mv;
    }

    //LISTAR
    @GetMapping("/listarFornecedor")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/fornecedor/lista");
        mv.addObject("listaFornecedor", forncedorRepository.findAll());
        return mv;
    }

    //EDITAR
    @GetMapping("/editarFornecedor/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Fornecedor> fornecedor = forncedorRepository.findById(id);
        return cadastrar(fornecedor.get());
    }

    //REMOVER
    @GetMapping("/removerFornecedor/{id}")
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Fornecedor> fornecedor = forncedorRepository.findById(id);
        forncedorRepository.delete(fornecedor.get());
        return listar();
    }

    //SALVAR
    @PostMapping("/salvarFornecedor")
    public ModelAndView salvar(Fornecedor fornecedor, BindingResult result){
        if (result.hasErrors()){
            return cadastrar(fornecedor);
        }
        forncedorRepository.saveAndFlush(fornecedor);
        return cadastrar(new Fornecedor());
    }
}
