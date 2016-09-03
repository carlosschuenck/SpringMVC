package br.com.springmvc.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//Esta classe fornece os itens basicos para o Spring como: mapeamento de URL do servlet do SpringMVC e quais outras classes de config devem ser carregadas
public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * O filtro do Spring Security é carregado antes do Servlet do Spring MVC
	 * Mas precisamos dos objetos de configuração da classe
	 * SecurityConfiguration antes do filtro ser inciado. O getRootConfigClasses
	 * faz com que as classes sejam carregadas dentro de um Listener que é lido
	 * quando servidor sobe. No caso do Spring MVC é o ContextLoaderListener
	 * 
	 * @return
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfiguration.class, 
							 AppServletConfiguration.class, 
							 JPAConfiguracao.class,
							 JPAConfiguracaoProducao.class,
							 AmazonConfiguracao.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		// Esta sendo passado uma string vazia por parametro para o servidor WEB
		// decidir
		// onde será o armazenamento temporário dos arquivos.
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

	@Override 
	public void onStartup(ServletContext servletContext) throws ServletException { 
		super.onStartup(servletContext); 
		servletContext.addListener(RequestContextListener.class); 
		servletContext.setInitParameter(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "dev"); 
	}
	
	//Mantém o EntityManager aberto para que o Hibernate possa fazer a busca dos relacionamentos via Lazy Load
	//O recomendado é utilizar Lazy Load apenas em ultimo caso
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new OpenEntityManagerInViewFilter() };
	}

}
