package br.com.springmvc.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Preco {
	
	@Column(scale = 2)
	private BigDecimal valor;
	private TipoLivro tipoLivro;
	
	public Preco() {
	}
	
	public Preco(BigDecimal valor, TipoLivro tipoLivro) {
		super();
		this.valor = valor;
		this.tipoLivro = tipoLivro;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public TipoLivro getTipoLivro() {
		return tipoLivro;
	}
	public void setTipoLivro(TipoLivro tipoLivro) {
		this.tipoLivro = tipoLivro;
	}
	
	
}
