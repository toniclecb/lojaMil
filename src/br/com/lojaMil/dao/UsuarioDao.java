package br.com.lojaMil.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.lojaMil.entities.Usuario;
import br.com.lojaMil.hibernate.GenericDao;
/**
 * 
 * @author Cleiton Balansin
 * a anotacao Component faz com que o vraptor saiba que ele precisa instanciar esta classe.
 */
@Component
public class UsuarioDao extends GenericDao<Usuario>{

	public UsuarioDao(Session session) { 
		super(session, Usuario.class);
	}

	public List<Usuario> findByLogin(String login){
		Criteria crit = getSession().createCriteria(this.getAccessedClass());
		crit.add(org.hibernate.criterion.Restrictions.eq("login", login));
		List<Usuario> lista = crit.list();
		return lista;
	}

	public List<Usuario> findAll(){
		Criteria crit = getSession().createCriteria(Usuario.class);
		return crit.list();
	}
	
}
