package br.com.springmvc.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import br.com.springmvc.models.IntegrandoComPagamento;
import br.com.springmvc.models.ShoppingCart;
import br.com.springmvc.models.Usuario;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {
	@Autowired
	private ShoppingCart shoppingCart;
	@Autowired
	private RestTemplate restTemplate; // Disponibiliza diversos métodos para
										// realizar diversos tipos de
										// requisições
	@Autowired
	private MailSender mailer;

	/**
	 * Só pelo fato de retornar um objeto do tipo Callable o Spring MVC já vai iniciar um contexto assíncrono em sua servlet
	 * e liberar para que o Tomcat possa usar a Thread dele para atender novas requisições. Assim que receber a resposta da integração
	 * retornamos o endereço de redirect e o mesmo é informado para o Tomcat.
	 * (A implementação com Callable foi substituida pelo DeferredResult)
	 * @return
	 */
	@RequestMapping(value="checkout",method=RequestMethod.POST) 
	public DeferredResult<String> checkout(@AuthenticationPrincipal Usuario user) { 
			BigDecimal total = shoppingCart.getTotal(); //código de integração 
			DeferredResult<String> resultado = new DeferredResult<String>();
			IntegrandoComPagamento icp = new IntegrandoComPagamento(resultado, total, restTemplate, user, mailer);
			Thread thread = new Thread(icp);
			thread.start();
			return resultado;
	}
	
	@RequestMapping(value="checkout",method=RequestMethod.GET) 
	public String check(@AuthenticationPrincipal Usuario user) {
			System.out.println("entrou no checkout: "+user.getNome());
			return "redirect:/livraria/produtos";
	}

}
