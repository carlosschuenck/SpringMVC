package br.com.springmvc.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity //Indica que a classe vai representar uma tabela na base de dados
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String titulo;
	@Lob
	@NotBlank
	private String descricao;
	@Min(30)
	private int pagina;
	@DateTimeFormat(iso=ISO.DATE)
	private Calendar dataLancamento;
	private String sumarioPath;
	@ElementCollection
	private List<Preco> precos = new ArrayList<Preco>();
	
	public Produto() {
	}
	
	public Produto(Integer id, String titulo, String descricao, int pagina, Calendar dataLancamento, String sumarioPath,
			List<Preco> precos) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.pagina = pagina;
		this.dataLancamento = dataLancamento;
		this.sumarioPath = sumarioPath;
		this.precos = precos;
	}

	@Override
	public String toString() {
		return "Produto \n"+
			   "titulo = " + titulo + 
			   "\ndescricao = " + descricao + 
			   "\npagina = " + pagina+
			   "\ndata de lançamento = " + dataLancamento+
			   "\nsumario = " + sumarioPath+
			   "\nprecos = " + precos;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Preco> getPrecos() {
		return precos;
	}

	public void setPrecos(List<Preco> precos) {
		this.precos = precos;
	}

	public Calendar getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getSumarioPath() {
		return sumarioPath;
	}

	public void setSumarioPath(String sumarioPath) {
		this.sumarioPath = sumarioPath;
	}
	
	public BigDecimal precoPara(TipoLivro tipoLivro) {
		return precos
				.stream()
				.filter(preco -> preco.getTipoLivro().equals(tipoLivro))
				.findFirst().get().getValor();
	}
}
