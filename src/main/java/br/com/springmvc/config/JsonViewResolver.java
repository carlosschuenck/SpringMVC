package br.com.springmvc.config;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setPrettyPrint(true);
	/**
	 * Indica ao Spring MVC que o view resolver só irá gerar o Json dessa chave que está dentro do ModelAndView.
	 * Isso pode ser utilizado caso o ModelAndView retorne varios objetos e você só queira gerar o Json de apenas um.
	 * view.setModelKey("products");
	 */
		return view;
	}

}
