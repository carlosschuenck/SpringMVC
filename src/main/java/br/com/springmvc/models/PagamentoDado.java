package br.com.springmvc.models;

import java.math.BigDecimal;

public class PagamentoDado {
	
	private BigDecimal valor;
	
	public PagamentoDado() {
	}
	
	public PagamentoDado(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
