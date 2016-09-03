package br.com.springmvc.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.springmvc.daos.ProdutoDao;
import br.com.springmvc.models.Produto;
import br.com.springmvc.models.TipoLivro;
import br.com.springmvc.utils.FileSaver;

@Controller // Indica que esta classe vai receber requisições da view
@Transactional // Indica que os métodos dessa classe precisam de uma transação
@RequestMapping("/livraria/produtos")
public class ProdutoController {

	@Autowired // Indica os pontos de injeção de dependencia dentro de uma classe.
	private ProdutoDao produtoDao;

	@Autowired
	private FileSaver fileSaver;

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		ModelAndView mav = new ModelAndView("produtos/form");
		mav.addObject("tipos", TipoLivro.values());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST, name = "salvarProduto")
	//Invalida os valores de uma determinada região do cache quando este método for chamado
	//allEntries indica que todos os valores vão ser retirados
	@CacheEvict(value="livros",allEntries=true)
	public ModelAndView save(MultipartFile sumario, @Valid Produto produto, BindingResult br, RedirectAttributes ra) {
		System.out.println(sumario.getName() + " ; " + sumario.getOriginalFilename());
		ModelAndView mdv = new ModelAndView("redirect:produtos");
		System.out.println(sumario.getName());
		if (br.hasErrors()) {
			return form(produto);
		}
		try {
			//String webPath = fileSaver.write("uploaded-images", sumario);
			//produto.setSumarioPath(webPath);
			produtoDao.save(produto);
			ra.addFlashAttribute("msg", "Produto cadastrado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdv;
	}

	// Método informa ao Spring qual classe vai validar o objeto do tipo
	// Produto.
	// @InitBinder
	// protected void initBinder(WebDataBinder binder) {
	// binder.setValidator(new ValidacaoProduto());
	// }

	@RequestMapping(method=RequestMethod.GET)
	@Cacheable(value="livros")
	public ModelAndView list(){
		System.out.println("LISTANDO...");
		ModelAndView modelAndView = new ModelAndView("produtos/list");
		modelAndView.addObject("produtos", produtoDao.list());
		return modelAndView;
	}
	/**
	@RequestMapping(method=RequestMethod.GET, value="json")
	 Informa ao Spring MVC que o retorno do método vai ser usado diretamente como corpo da resposta
	 Ou seja, vai imprimir um Json. Caso não for utilizada, vai ser procurado uma view com nome passado.
	 O método já retorna um json devido já termos configurado a dependencia do Jackson
	 Caso exista um Controller que ira gerar os resultados direto no corpo da respota, pode usar o @RestController
	 Ao invés de usar o ResponseBody em casa método.
	@ResponseBody
	public List<Produto> listJson(){
		return produtoDao.list();
	}
	*/

	@RequestMapping("/{id}") 
	public ModelAndView show(@PathVariable("id") Integer id){ 
		ModelAndView modelAndView = new ModelAndView("produtos/show"); 
		Produto produto = produtoDao.find(id);
		modelAndView.addObject("produto", produto); 
		return modelAndView; 
	}

}
