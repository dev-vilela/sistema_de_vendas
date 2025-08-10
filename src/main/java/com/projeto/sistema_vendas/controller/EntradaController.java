package com.projeto.sistema_vendas.controller;

import com.projeto.sistema_vendas.model.Cidade;
import com.projeto.sistema_vendas.model.Entrada;
import com.projeto.sistema_vendas.model.ItemEntrada;
import com.projeto.sistema_vendas.model.Produto;
import com.projeto.sistema_vendas.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EntradaController {

    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private ItemEntradaRepository itemEntradaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ForncedorRepository forncedorRepository;

    private List<ItemEntrada> listaItemEntrada = new ArrayList<ItemEntrada>();


    //CRUD

    //CADASTRAR
    @GetMapping("/cadastroEntrada")
    public ModelAndView cadastrar(Entrada entrada, ItemEntrada itemEntrada){
        ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
        mv.addObject("entrada", entrada);
        mv.addObject("itemEntrada", itemEntrada);
        mv.addObject("listaItemEntrada", this.listaItemEntrada);
        mv.addObject("listaFuncionario", funcionarioRepository.findAll());
        mv.addObject("listaFornecedor", forncedorRepository.findAll());
        mv.addObject("listaProduto", produtoRepository.findAll());
        return mv;
    }

    //LISTAR
    @GetMapping("/listarEntrada")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/entrada/lista");
        mv.addObject("listaEntrada", entradaRepository.findAll());
        return mv;
    }

    //EDITAR
    @GetMapping("/editarEntrada/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Entrada> entrada = entradaRepository.findById(id);
        this.listaItemEntrada = itemEntradaRepository.buscarPorEntrada(entrada.get().getId());
        return cadastrar(entrada.get(), new ItemEntrada());
    }

    //REMOVER
//    @GetMapping("/removerCidade/{id}")
//    public ModelAndView remover(@PathVariable("id") Long id){
//        Optional<Entrada> entrada = entradaRepository.findById(id);
//        entradaRepository.delete(entrada.get());
//        return listar();
//    }

    //SALVAR
    @PostMapping("/salvarEntrada")
    public ModelAndView salvar(String acao, Entrada entrada, ItemEntrada itemEntrada ,BindingResult result){
        if (result.hasErrors()){
            return cadastrar(entrada, itemEntrada);
        }
        if (acao.equals("itens")){
            this.listaItemEntrada.add(itemEntrada);
            entrada.setValorTotal(entrada.getValorTotal() + (itemEntrada.getValor() * itemEntrada.getQuantidade()));
            entrada.setQtdTotal(entrada.getQtdTotal() + itemEntrada.getQuantidade());
        } else if (acao.equals("salvar")) {
            entradaRepository.saveAndFlush(entrada);

            for (ItemEntrada it : listaItemEntrada){
                it.setEntrada(entrada);
                itemEntradaRepository.saveAndFlush(it);

                Optional<Produto> prod = produtoRepository.findById(it.getProduto().getId());
                Produto produto = prod.get();
                produto.setEstoque(produto.getEstoque() + it.getQuantidade());
                produto.setPrecoVenda(it.getValor());
                produto.setPrecoCusto(it.getValorCusto());
                produtoRepository.saveAndFlush(produto);

                this.listaItemEntrada = new ArrayList<>();
            }
            return cadastrar(new Entrada(), new ItemEntrada());
        }
          return cadastrar(entrada, new ItemEntrada());
    }

    public List<ItemEntrada> getListaItemEntrada() {
        return listaItemEntrada;
    }

    public void setListaItemEntrada(List<ItemEntrada> listaItemEntrada) {
        this.listaItemEntrada = listaItemEntrada;
    }
}
