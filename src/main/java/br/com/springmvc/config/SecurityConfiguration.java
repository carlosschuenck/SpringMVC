package br.com.springmvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService users;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(users).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//Retorna o objeto onde podemos configurar as regras de acesso
		.antMatchers("/livraria/produtos/form").hasRole("ADMIN")
		.antMatchers("/shopping/**").permitAll()
		.antMatchers(HttpMethod.POST,"/livraria/produtos").hasRole("ADMIN") 
		.antMatchers("/livraria/produtos/**").permitAll()
		.antMatchers(HttpMethod.POST,"/pagamento/checkout").hasRole("ADMIN")
		.anyRequest().authenticated()//Informa que qualquer requisição tem que ser feita por alguém autorizado
		.and().formLogin()//Indica que queremos que o sistema suporte autenticação baseada em formulário comum
		.loginPage("/login").permitAll()
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	//Faz com que o Spring Security desbloqueie o acesso a pasta resouce permitindo o acesso aos arquivos css e js
	//Desta forma poderá apontar os arquivos na jsp
	@Override 
	public void configure(WebSecurity web) throws Exception { 
		web.ignoring().antMatchers("/resource/**"); 
	}

}
