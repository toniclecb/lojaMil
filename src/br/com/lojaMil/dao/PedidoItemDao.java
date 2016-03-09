package br.com.lojaMil.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.lojaMil.entities.Pedido;
import br.com.lojaMil.entities.PedidoItem;
import br.com.lojaMil.entities.Produto;
import br.com.lojaMil.hibernate.GenericDao;

@Component
public class PedidoItemDao extends GenericDao<PedidoItem> {

	public PedidoItemDao(Session session) {
		super(session, PedidoItem.class);
	}

	public List<Produto> findAll(){
		Criteria crit = getSession().createCriteria(getAccessedClass());
		return crit.list();
	}

	public PedidoItem findById(Long pedidoItem) {
		Criteria crit = getSession().createCriteria(getAccessedClass());
		crit.add(Restrictions.eq("idPedidoItem", pedidoItem));
		return (PedidoItem) crit.uniqueResult();
	}
	
}
