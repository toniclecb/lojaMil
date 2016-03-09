package br.com.lojaMil.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.lojaMil.entities.Pedido;
import br.com.lojaMil.entities.Produto;
import br.com.lojaMil.entities.Usuario;
import br.com.lojaMil.hibernate.GenericDao;

@Component
public class PedidoDao extends GenericDao<Pedido> {

	public PedidoDao(Session session) {
		super(session, Pedido.class);
	}

	public List<Produto> findAll(){
		Criteria crit = getSession().createCriteria(getAccessedClass());
		return crit.list();
	}

	public long count(Usuario user) {
		Criteria crit = getSession().createCriteria(getAccessedClass());
		crit.add(Restrictions.eq("usuario", user));
		crit.setProjection(Projections.rowCount());
		
		return (Long) crit.uniqueResult();
	}

	public List<Pedido> findByUsuario(Usuario user) {
		Criteria crit = getSession().createCriteria(getAccessedClass());
		crit.add(Restrictions.eq("usuario", user));
		
		return crit.list();
	}

	public Pedido findByUsuarioNaoFinalizado(Usuario user) {
		Criteria crit = getSession().createCriteria(getAccessedClass());
		crit.add(Restrictions.eq("usuario", user));
		crit.add(Restrictions.eq("finalizado", 0));
		
		return (Pedido) crit.uniqueResult();
	}
	
}
