package br.com.springmvc.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceConfigurationTest {
	
	@Bean
	//Indica que o método de conf do DS vai ser carregado apenas no contexto de testes
	//Isso serve para nao ser sobre posto ou dar problema com o metodo de conf do DS do ambiente de desenvolvimento
	@Profile("test")
	public DataSource dataSource(){
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver"); 
		ds.setUrl("jdbc:mysql://localhost:3306/teste_springmvc"); 
		ds.setUsername("root"); 
		ds.setPassword("carlos");
		return ds;
	}
}
