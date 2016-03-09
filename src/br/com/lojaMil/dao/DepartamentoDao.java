package br.com.lojaMil.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.lojaMil.entities.Departamento;
import br.com.lojaMil.hibernate.GenericDao;

@Component
public class DepartamentoDao extends GenericDao<Departamento> {

	public DepartamentoDao(Session session) {
		super(session, Departamento.class);
	}
	
	public List<Departamento> findAll(){
		Criteria crit = getSession().createCriteria(Departamento.class);
		return crit.list();
	}
}
