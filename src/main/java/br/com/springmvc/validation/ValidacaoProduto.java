package br.com.springmvc.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.springmvc.models.Produto;

public class ValidacaoProduto implements Validator{
	
	/**
	 * Método responsável por verificar esta classe de validação é responsável por validar objetos do mesmo tipos.
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}
	
	/**
	 * Método responsável pela validação!
	 * erros = onde é guardado os erros de validação
	 * obj = objeto a ser validado.
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "campo.obrigatorio.produto.titulo");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descricao", "campo.obrigatorio.produto.descricao");
		
		Produto produto = (Produto) obj;
		
		if (produto.getPagina() == 0) {
			//para adicionar mensagem na lista de erro quando fizer uma validação independente do ValidationUtilss
			errors.rejectValue("pagina", "campo.obrigatorio.produto.pagina");
		}
	}

}
