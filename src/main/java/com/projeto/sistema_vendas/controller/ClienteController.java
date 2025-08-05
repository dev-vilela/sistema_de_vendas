package com.projeto.sistema_vendas.controller;

import com.projeto.sistema_vendas.model.Cliente;
import com.projeto.sistema_vendas.model.Funcionario;
import com.projeto.sistema_vendas.repositories.CidadeRepository;
import com.projeto.sistema_vendas.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    //CRUD

    //CADASTRAR
    @GetMapping("/cadastroCliente")
    public ModelAndView cadastrar(Cliente cliente){
        ModelAndView mv = new ModelAndView("administrativo/cliente/cadastro");
        mv.addObject("cliente", cliente);
        mv.addObject("listaCidades", cidadeRepository.findAll());
        return mv;
    }

    //LISTAR
    @GetMapping("/listarCliente")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/cliente/lista");
        mv.addObject("listaCliente", clienteRepository.findAll());
        return mv;
    }

    //EDITAR
    @GetMapping("/editarCliente/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cadastrar(cliente.get());
    }

    //REMOVER
    @GetMapping("/removerCliente/{id}")
    public ModelAndView remover(@PathVariable("id") Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        clienteRepository.delete(cliente.get());
        return listar();
    }

    //SALVAR
    @PostMapping("/salvarCliente")
    public ModelAndView salvar(Cliente cliente, BindingResult result){
        if (result.hasErrors()){
            return cadastrar(cliente);
        }
        clienteRepository.saveAndFlush(cliente);
        return cadastrar(new Cliente());
    }
}
