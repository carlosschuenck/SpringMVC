package br.com.springmvc.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.springmvc.builders.ProdutoBuilder;
import br.com.springmvc.conf.DataSourceConfigurationTest;
import br.com.springmvc.config.JPAConfiguracao;
import br.com.springmvc.models.Produto;
import br.com.springmvc.models.TipoLivro;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ProdutoDao.class,JPAConfiguracao.class,DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProdutoDaoTeste {
	
	@Autowired
	private ProdutoDao dao;
	
	@Transactional //Informa que o método precisa ser executado dentro de uma transação
	@Test
	public void somatorioTodosPrecosPorTipoDeLivro(){
		//cria uma lista de livros impressos
		List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoLivro.PRINTED, BigDecimal.TEN)
													.more(4)
													.buildAll();
		
		//usa o foreach do java 8 para persistir os livros
		livrosImpressos.stream().forEach(dao::save);
		
		List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoLivro.EBOOK, BigDecimal.TEN)
				.more(4)
				.buildAll();

		livrosEbook.stream().forEach(dao::save);
		
		BigDecimal value = dao.somarioPrecosPorTipo(TipoLivro.PRINTED);
		Assert.assertEquals(new BigDecimal(50).setScale(2), value);
	}
}
