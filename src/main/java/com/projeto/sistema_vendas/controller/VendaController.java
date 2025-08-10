package com.projeto.sistema_vendas.controller;

import com.projeto.sistema_vendas.model.*;
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
public class VendaController {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private List<ItemVenda> listaItemVenda = new ArrayList<ItemVenda>();


    //CRUD

    //CADASTRAR
    @GetMapping("/cadastroVenda")
    public ModelAndView cadastrar(Venda venda, ItemVenda itemVenda){
        ModelAndView mv = new ModelAndView("administrativo/venda/cadastro");
        mv.addObject("venda", venda);
        mv.addObject("itemVenda", itemVenda);
        mv.addObject("listaItemVenda", this.listaItemVenda);
        mv.addObject("listaFuncionario", funcionarioRepository.findAll());
        mv.addObject("listaCliente", clienteRepository.findAll());
        mv.addObject("listaProduto", produtoRepository.findAll());
        return mv;
    }

    //LISTAR
    @GetMapping("/listarVenda")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/venda/lista");
        mv.addObject("listaVenda", vendaRepository.findAll());
        return mv;
    }

    //EDITAR
    @GetMapping("/editarVenda/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Venda> venda = vendaRepository.findById(id);
        this.listaItemVenda = itemVendaRepository.buscarPorVenda(venda.get().getId());
        return cadastrar(venda.get(), new ItemVenda());
    }

    //REMOVER
//    @GetMapping("/removerCidade/{id}")
//    public ModelAndView remover(@PathVariable("id") Long id){
//        Optional<Entrada> entrada = entradaRepository.findById(id);
//        entradaRepository.delete(entrada.get());
//        return listar();
//    }

    //SALVAR
    @PostMapping("/salvarVenda")
    public ModelAndView salvar(String acao, Venda venda, ItemVenda itemVenda ,BindingResult result){
        if (result.hasErrors()){
            return cadastrar(venda, itemVenda);
        }
        if (acao.equals("itens")){

            itemVenda.setValor(itemVenda.getProduto().getPrecoVenda());
            itemVenda.setSubTotal(itemVenda.getProduto().getPrecoVenda() * itemVenda.getQuantidade());

            venda.setValorTotal(venda.getValorTotal() + (itemVenda.getValor() * itemVenda.getQuantidade()));
            venda.setQtdTotal(venda.getQtdTotal() + itemVenda.getQuantidade());

            this.listaItemVenda.add(itemVenda);
        } else if (acao.equals("salvar")) {
            vendaRepository.saveAndFlush(venda);

            for (ItemVenda it : listaItemVenda){
                  it.setVenda(venda);
//                  it.setSubTotal(it.getValor()*it.getQuantidade());
                itemVendaRepository.saveAndFlush(it);

                Optional<Produto> prod = produtoRepository.findById(it.getProduto().getId());
                Produto produto = prod.get();
                produto.setEstoque(produto.getEstoque() - it.getQuantidade());
                produto.setPrecoVenda(it.getValor());
//                produto.setPrecoCusto(it.getValorCusto());
                produtoRepository.saveAndFlush(produto);

                this.listaItemVenda = new ArrayList<>();
            }
            return cadastrar(new Venda(), new ItemVenda());
        }
          return cadastrar(venda, new ItemVenda());
    }

    public List<ItemVenda> getListaItemVenda() {
        return listaItemVenda;
    }

    public void setListaItemEntrada(List<ItemEntrada> listaItemEntrada) {
        this.listaItemVenda = listaItemVenda;
    }
}
