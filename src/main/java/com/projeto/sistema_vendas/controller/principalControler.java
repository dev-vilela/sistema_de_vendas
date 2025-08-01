package com.projeto.sistema_vendas.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class principalControler {

    @GetMapping("/administrativo")
    public String acessarPrincipal(){
        return "administrativo/home";
    }
}
