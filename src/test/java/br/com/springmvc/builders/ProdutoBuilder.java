package br.com.springmvc.builders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.springmvc.models.Preco;
import br.com.springmvc.models.Produto;
import br.com.springmvc.models.TipoLivro;

/**
 * Utilizado para criar o cenário dos testes automatizados
 * @author Carlos Schuenck
 *
 */
public class ProdutoBuilder {
	private List<Produto> produtos = new ArrayList<Produto>();

	private ProdutoBuilder(Produto product){
		produtos.add(product);
		
	}
	
	public static ProdutoBuilder newProduto(TipoLivro tipoLivro,BigDecimal valor){
		Produto livro = create("Book 1",tipoLivro, valor);
		return new ProdutoBuilder(livro);	
	}
	
	public static ProdutoBuilder newProduto(){
		Produto livro = create("Book 1",TipoLivro.COMBO, BigDecimal.TEN);
		return new ProdutoBuilder(livro);	
	}

	private static Produto create(String livroName,TipoLivro tipoLivro, BigDecimal valor) {
		Produto livro = new Produto();
		livro.setTitulo(livroName);
		livro.setDataLancamento(Calendar.getInstance());
		livro.setPagina(150);
		livro.setDescricao("great livro about testing");
		Preco preco = new Preco();
		preco.setTipoLivro(tipoLivro);
		preco.setValor(valor);		
		livro.getPrecos().add(preco);
		return livro;
	}
	
	public ProdutoBuilder more(int number){
		Produto base = produtos.get(0);
		Preco preco = base.getPrecos().get(0);
		for (int i = 0; i < number; i++) {
			produtos.add(create("Livro "+i, preco.getTipoLivro(), preco.getValor()));
		}
		return this;
	}
	
	public Produto buildOne() {
		return produtos.get(0);
	}
	
	public List<Produto> buildAll(){
		return produtos;
	}
}
