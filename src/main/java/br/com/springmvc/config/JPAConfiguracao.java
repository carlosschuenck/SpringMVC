package br.com.springmvc.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement //Indica que vai usar o controle transacional do spring
public class JPAConfiguracao {
	
	/**
	 * LocalContainerEntityManagerFactoryBean é a abstração do arquivo persistence.xml
	 * @return
	 */
	@Bean //Indica que os objetos gerados serão gerenciados pelo Spring.
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		JpaVendorAdapter vendorAdpter = new HibernateJpaVendorAdapter();
		em.setDataSource(dataSource);
		em.setPackagesToScan(new String[]{"br.com.springmvc.models"});
		em.setJpaVendorAdapter(vendorAdpter);
		em.setJpaProperties(adicionalProperties());
		return em;
	}
	
	@Bean
	@Profile("dev")
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/springmvc");
		dataSource.setUsername("root");
		dataSource.setPassword("carlos");
		return dataSource;
	}
	
	public Properties adicionalProperties(){
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true"); 
		return properties;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager jtm = new JpaTransactionManager();
		jtm.setEntityManagerFactory(emf);
		return jtm;
	}
}
