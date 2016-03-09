package br.com.lojaMil.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/**
 * Classe generica para os Daos que implementa as funcoes mais basicas dos Daos.
 * Deve ser extendida pelos Daos especificos.
 * @author Cleiton Balansin
 *
 */
public class GenericDao<T> {

	private Session session;
	private Class accessedClass;

	public GenericDao(Session session, Class classe) {
		this.session = session;
		this.accessedClass = classe;
	}

	/**
	 * @return
	 */
	public Session getSession() {
		return session;
	}

	public Serializable add(T entity) {
		return this.session.save(entity);
	}

	public void remove(T entity) {
		this.session.delete(entity);
	}

	public Object atualiza(T entity) {
		return this.session.merge(entity);
	}

	public List<T> listaTudo() {
		return this.session.createCriteria(this.accessedClass).list();
	}

	public T busca(Long id) {
		return (T) session.load(this.accessedClass, id);
	}

	public Class getAccessedClass() {
		return accessedClass;
	}
}
