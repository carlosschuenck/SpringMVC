package br.com.springmvc.models;

import java.math.BigDecimal;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

public class IntegrandoComPagamento implements Runnable {

	private DeferredResult<String> resultado;
	private BigDecimal valor;
	private RestTemplate restTemplate;
	private Usuario user;
	private MailSender mailer;

	public IntegrandoComPagamento(DeferredResult<String> resultado, BigDecimal valor, RestTemplate restTemplate, Usuario user, MailSender mailer) {
		super();
		this.resultado = resultado;
		this.valor = valor;
		this.restTemplate = restTemplate;
		this.user = user;
		this.mailer = mailer;
	}

	@Override
	public void run() {
	String uriToPay = "http://localhost:8081/SpringMVC/livraria/produtos";
		try {
			restTemplate.postForObject(uriToPay, new PagamentoDado(valor), String.class); // linha de notificação
			//sendNewPurchaseMail(user);
			resultado.setResult("redirect:/pagamento/sucesso");
		} catch (HttpClientErrorException exception) {
			resultado.setResult("redirect:/pagamento/erro");
		}
	}
	
	private void sendNewPurchaseMail(Usuario user) { 
		SimpleMailMessage email = new SimpleMailMessage(); 
		email.setFrom("compras@casadocodigo.com.br"); 
		email.setTo("carlosschuenck@hotmail.com"); 
		email.setSubject("Spring MVC - Teste"); 
		email.setText("Teste de envio de email"); 
		mailer.send(email); 
	}
}