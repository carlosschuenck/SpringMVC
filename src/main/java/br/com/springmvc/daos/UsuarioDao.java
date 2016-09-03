package br.com.springmvc.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import br.com.springmvc.models.Usuario;

@Repository
public class UsuarioDao implements UserDetailsService{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		String jpql = "select usu from Usuario usu where usu.username = :username";
		List<Usuario> usuarios = em.createQuery(jpql, Usuario.class)
								   .setParameter("username", userName)
								   .getResultList();
		if (usuarios.isEmpty()) {
			throw new UsernameNotFoundException("O usuário "+userName+" não existe!");
		}
		System.out.println("Você logouc com usuário: "+usuarios.get(0).getNome()+", perfil: "+usuarios.get(0).getRoles().get(0).getAuthority());
		return usuarios.get(0);
	}
	
	public static void main(String[] args) {
		BCryptPasswordEncoder bbb = new BCryptPasswordEncoder();
		System.out.println(bbb.encode("123"));
	}
}
