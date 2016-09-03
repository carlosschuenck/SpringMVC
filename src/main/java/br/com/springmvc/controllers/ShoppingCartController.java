package br.com.springmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.springmvc.daos.ProdutoDao;
import br.com.springmvc.models.Produto;
import br.com.springmvc.models.ShoppingCart;
import br.com.springmvc.models.ShoppingItem;
import br.com.springmvc.models.TipoLivro;

@Controller
@RequestMapping("/shopping")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class ShoppingCartController {

	@Autowired
	private ProdutoDao produtoDao;
	@Autowired
	private ShoppingCart shoppingCart;

	@RequestMapping(method=RequestMethod.POST) 
	public ModelAndView add(Integer produtoId, TipoLivro tipoLivro){ 
		ShoppingItem item = createItem(produtoId, tipoLivro); 
		shoppingCart.add(item); 
		return new ModelAndView("redirect:/livraria/produtos"); 
	}

	private ShoppingItem createItem(Integer produtoId, TipoLivro tipoLivro) { 
		Produto produto = produtoDao.find(produtoId); 
		ShoppingItem item = new ShoppingItem(produto, tipoLivro); 
		return item; 
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String items(){
		return "shoppingCart/items";
	}
}
