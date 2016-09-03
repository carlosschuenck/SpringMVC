package br.com.springmvc.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Toda logica de segurança do Spring Security é inicializada através do filter FilterSecurityInterceptor
 * Usando essa estratégia de herdar AbstractSecurityWebApplicationInitializer. O Spring Security faz todo trabalho
 * e o filtro já fica configurado.
 * @author Carlos Schuenck
 */
public class SpringSecurityFilterConfiguration extends AbstractSecurityWebApplicationInitializer{

}
