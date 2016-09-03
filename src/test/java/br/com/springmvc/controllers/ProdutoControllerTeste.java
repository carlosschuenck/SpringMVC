package br.com.springmvc.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import javax.servlet.Filter;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.springmvc.builders.ProdutoBuilder;
import br.com.springmvc.conf.DataSourceConfigurationTest;
import br.com.springmvc.config.AppServletConfiguration;
import br.com.springmvc.config.JPAConfiguracao;
import br.com.springmvc.config.SecurityConfiguration;
import br.com.springmvc.daos.ProdutoDao;
import br.com.springmvc.models.Produto;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration//Responsavel por fazer o Runner do Spring Test carregar os obj necessários para uma app WEB
@ContextConfiguration(classes = { AppServletConfiguration.class, 
								  JPAConfiguracao.class, 
								  SecurityConfiguration.class,
								  DataSourceConfigurationTest.class })
@ActiveProfiles("test")
public class ProdutoControllerTeste {
	@Autowired
	private ProdutoDao dao;
	@Autowired
	private WebApplicationContext wac;
	@Autowired 
	private Filter springSecurityFilterChain;

	private MockMvc mockMvc; // simula os requests dos testes

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
									.addFilters(springSecurityFilterChain)
									.build();
	}

	@Test
	@Transactional
	public void listarLivrosPaginaInicial() throws Exception {
		Produto produto = ProdutoBuilder.newProduto().buildOne();
		dao.save(produto);
		
		ResultMatcher modelAndViewMatcher = new ResultMatcher() {
			@SuppressWarnings("unchecked")
			@Override
			public void match(MvcResult result) throws Exception {
				ModelAndView mv = result.getModelAndView();
				List<Produto> produtos = (List<Produto>) mv.getModel().get("produtos");
				Assert.assertEquals(1, produtos.size());
			}
		};
		this.mockMvc.perform(get("/livraria/produtos"))
				.andExpect(modelAndViewMatcher)
				.andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/produtos/list.jsp"));
	}

	@Test
	public void penasAdministradoresAcessamOCadastro() throws Exception {
		//Serve para adicionar alguma coisa ao request antes do mesmo ser processado.
		RequestPostProcessor processor = SecurityMockMvcRequestPostProcessors.user("comprador")
				.password("123").roles("COMPRADOR");
		this.mockMvc.perform(get("/produtos/form")
					.with(processor))
					.andExpect(MockMvcResultMatchers.status().is(403));
	}
}