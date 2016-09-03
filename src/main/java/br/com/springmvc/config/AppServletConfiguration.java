package br.com.springmvc.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.springmvc.controllers.ProdutoController;
import br.com.springmvc.daos.ProdutoDao;
import br.com.springmvc.models.ShoppingCart;
import br.com.springmvc.utils.FileSaver;

@EnableWebMvc
@EnableCaching
@ComponentScan(basePackageClasses={ProdutoController.class, ProdutoDao.class, FileSaver.class, ShoppingCart.class})
public class AppServletConfiguration extends WebMvcConfigurerAdapter{
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		//Expoem os beans do spring nas telas através do Expression Language
		//Indica qual objeto vai ser disponibilizado na view. Forma encontrada para não ficar todos os objetos disponiveis.
		resolver.setExposedContextBeanNames("shoppingCart");
		return resolver;
	}
	
	@Bean(name="messageSource")//O spring vai procurar um bean com este nome para carregar as mensagens
	public MessageSource loadBundle(){
		ReloadableResourceBundleMessageSource rrbm = new ReloadableResourceBundleMessageSource();
		rrbm.setBasename("/resource/messages"); //indica onde está e o nome do arquivo.
		rrbm.setDefaultEncoding("ISO-8859-1"); //indica o encode
		rrbm.setCacheSeconds(1);//indica de quanto em quanto tempo que o arquivo deve ser recarregado.
		return rrbm;
	}
	
	/**
	 * O nome do método tem que ser mvcConversionService
	 * Pois o Spring usa o método que tem esse nome para registrar o objeto responsável por agrupar conversores
	 */
	@Bean
	public FormattingConversionService mvcConversionService(){
		//Objeto está sendo instanciado com o valor true passado como parametro, isso indica que todos os conversores padroes devem ser adicionados
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(true);
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
		registrar.registerFormatters(conversionService);
		return conversionService;
	}
	
	@Bean 
	public MultipartResolver multipartResolver(){ 
		return new StandardServletMultipartResolver(); 
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	//Implementação necessária para usar o cache
	@Bean
	public CacheManager chacheManager(){
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
														   .maximumSize(100)
														   .expireAfterAccess(5, TimeUnit.MINUTES);
		GuavaCacheManager gcm = new GuavaCacheManager();
		gcm.setCacheBuilder(builder);
		return gcm;
	}
	
	@Bean public ViewResolver contentNegotiatingViewResolver( ContentNegotiationManager manager) { 
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		resolvers.add(internalResourceViewResolver()); 
		resolvers.add(new JsonViewResolver());
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(resolvers);
		resolver.setContentNegotiationManager(manager);
		return resolver;
	}
	
	//
	@Override 
	public void addInterceptors(InterceptorRegistry registry) { 
		registry.addInterceptor(new LocaleChangeInterceptor()); 
	}

	//Essa implementação guarda o idoma preferido em um cookie
	//Existem outras como SessionLocaleResolver
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}
	
	/**
	 * Informa ao Spring MVC que caso ele nao consiga resolver o endereço,
	 * ele deve delegar a chamada para o Servlet Container em uso. O problema acontecia porque o Spring MVC
	 * estava achando que voce estava acessando um mapeamento de um controller, como nao encontrava ele retornava
	 * not found 404
	 */
	@Override 
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) { 
		configurer.enable(); 
	}
	
	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost("smtp.gmail.com");
		javaMailSenderImpl.setPassword("251102511093");
		javaMailSenderImpl.setPort(587);
		javaMailSenderImpl.setUsername("carlosschuenck.10@gmail.com");
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		javaMailSenderImpl.setJavaMailProperties(mailProperties);
		
		return javaMailSenderImpl;
	}

}
