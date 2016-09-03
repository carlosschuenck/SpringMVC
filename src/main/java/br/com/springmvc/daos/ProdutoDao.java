package br.com.springmvc.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.springmvc.models.Produto;
import br.com.springmvc.models.TipoLivro;

@Repository //Indica que esta classe além de ser gerenciada pelo String é respondável pelo acesso aos dados
public class ProdutoDao {

	@PersistenceContext
	private EntityManager manager;

	public void save(Produto produto){
		manager.persist(produto);
	}

	public List<Produto> list() {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos", Produto.class)
				.getResultList();
	}
	
	public Produto find(Integer id) {
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos where p.id="+id, Produto.class)
				.getSingleResult();
	}
	
	/**
	 * Retorna o somatório do preço dos livros de acordo com o tipo do livro informado.
	 * @param tipoLivro
	 * @return {@link BigDecimal}
	 */
	public BigDecimal somarioPrecosPorTipo(TipoLivro tipoLivro) { 
		TypedQuery<BigDecimal> query = manager.createQuery( "select sum(preco.valor) from Produto p join p.precos preco where preco.tipoLivro =:tipoLivro", BigDecimal.class); 
		query.setParameter("tipoLivro", tipoLivro); 
		return query.getSingleResult();
	}
}
