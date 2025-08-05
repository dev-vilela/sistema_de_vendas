package com.projeto.sistema_vendas.controller;

import com.projeto.sistema_vendas.model.Funcionario;
import com.projeto.sistema_vendas.repositories.CidadeRepository;
import com.projeto.sistema_vendas.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    //CRUD

    //CADASTRAR
    @GetMapping("/cadastroFuncionario")
    public ModelAndView cadastrar(Funcionario funcionario){
        ModelAndView mv = new ModelAndView("administrativo/funcionario/cadastro");
        mv.addObject("funcionario", funcionario);
        mv.addObject("listaCidades", cidadeRepository.findAll());
        return mv;
    }

    //LISTAR
    @GetMapping("/listarFuncionario")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/funcionario/lista");
        mv.addObject("listaFuncionario", funcionarioRepository.findAll());
        return mv;
    }

    //EDITAR
    @GetMapping("/editarFuncionario/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return cadastrar(funcionario.get());
    }

    //REMOVER
    @GetMapping("/removerFuncionario/{id}")
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        funcionarioRepository.delete(funcionario.get());
        return listar();
    }

    //SALVAR
    @PostMapping("/salvarFuncionario")
    public ModelAndView salvar(Funcionario funcionario, BindingResult result){
        if (result.hasErrors()){
            return cadastrar(funcionario);
        }
        funcionarioRepository.saveAndFlush(funcionario);
        return cadastrar(new Funcionario());
    }
}
