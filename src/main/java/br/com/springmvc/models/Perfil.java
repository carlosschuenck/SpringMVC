package br.com.springmvc.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Perfil implements GrantedAuthority {
	
	private static final long serialVersionUID = -7586392964410038312L;
	
	@Id
	private String nome;

	@Override
	public String getAuthority() {
		return nome;
	}

}
