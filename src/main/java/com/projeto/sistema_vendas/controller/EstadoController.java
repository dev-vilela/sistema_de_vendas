package com.projeto.sistema_vendas.controller;

import com.projeto.sistema_vendas.model.Estado;
import com.projeto.sistema_vendas.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping("/cadastroEstado")
    public ModelAndView cadastrar(Estado estado){
        ModelAndView mv = new ModelAndView("/estado/cadastro");
        mv.addObject("estado", estado);
        return mv;
    }

    @PostMapping("/salvarEstado")
    public ModelAndView salvar(Estado estado, BindingResult result){
        if (result.hasErrors()){
            return cadastrar(estado);
        }
        estadoRepository.saveAndFlush(estado);
        return cadastrar(new Estado());
    }
}
