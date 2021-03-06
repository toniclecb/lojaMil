package br.com.lojaMil.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.lojaMil.controller.IndexController;
import br.com.lojaMil.entities.Categoria;
import br.com.lojaMil.entities.Departamento;
import br.com.lojaMil.entities.Produto;
import br.com.lojaMil.entities.SubCategoria;
import br.com.lojaMil.hibernate.GenericDao;

/**
 * Executa as buscas relacionadas a Produto
 * 
 * @author Cleiton Balansin
 * 
 */
@Component
public class ProdutoDao extends GenericDao<Produto> {

	public ProdutoDao(Session session) {
		super(session, Produto.class);
	}

	/**
	 * Busca pelo titulo do produto.<br>
	 * A busca eh feita com base em "<code>ilike %titulo%</code>"<br>
	 * Caso o parametro <code>titulo</code> tenha espacos a busca sera feita
	 * como uma disjunction (OR).
	 * 
	 * @param titulo
	 *            string de busca
	 * @param ordena
	 *            1,2 ou 3
	 * @return lista de produtos
	 */
	public List<Produto> findTitulo(String titulo, String ordena) {
		if (titulo == null)
			titulo = new String("");
		Criteria crit = getSession().createCriteria(Produto.class);
		List<Criterion> clist = new ArrayList<Criterion>(0);
		for (String s : titulo.split(" ")) {
			Criterion c = Restrictions.ilike("titulo", s, MatchMode.ANYWHERE); // %titulo%
			clist.add(c);
		}
		Disjunction dis = Restrictions.disjunction(); // OR
		for (Criterion cc : clist) {
			dis.add(cc);
		}
		crit.add(dis);
		ordena(ordena, crit);
		return crit.list();
	}

	public List<Produto> findAll() {
		Criteria crit = getSession().createCriteria(getAccessedClass());
		return crit.list();
	}

	/**
	 * Executa a busca por departamento, categoria ou subCategoria.<br>
	 * Se departamento for != null faz a busca usando departamento<br>
	 * Senao se categoria for != null faz a busca usando categoria<br>
	 * Senao faz a busca usando a subcategoria.
	 * 
	 * @param page
	 * @return
	 */
	public List<Produto> find(String titulo, String departamento, String categoria, String subCategoria, String ordena,
			String page) {
		Criteria crit = find(titulo, departamento, categoria, subCategoria, ordena, page, false);

		return crit.list();
	}

	/**
	 * Executa a busca igual ProdutoDao.find(...), mas retorna apenas o numeros de linhas da pesquisa
	 * @return
	 */
	public Long count(String titulo, String departamento, String categoria, String subCategoria, String ordena) {
		Criteria crit = find(titulo, departamento, categoria, subCategoria, ordena, "0", true);
		
		return (Long) crit.uniqueResult();
	}

	private Criteria find(String titulo, String departamento, String categoria, String subCategoria, String ordena,
			String page, boolean count) {
		Criteria crit = getSession().createCriteria(getAccessedClass());
		if (titulo == null)
			titulo = new String("");
		List<Criterion> clist = new ArrayList<Criterion>(0);
		for (String s : titulo.split(" ")) {
			Criterion c = Restrictions.ilike("titulo", s, MatchMode.ANYWHERE); // %titulo%
			clist.add(c);
		}
		Disjunction dis = Restrictions.disjunction(); // OR
		for (Criterion cc : clist) {
			dis.add(cc);
		}
		crit.add(dis);
		if (departamento != null && !departamento.equals("")) {
			// nao precisa buscar os departamentos
			// Criteria critDep =
			// getSession().createCriteria(Departamento.class);
			// critDep.add(Restrictions.eq("idDepartamento", departamento));
			// List<Departamento> deps = critDep.list();

			Long departamentoL = Long.valueOf(departamento);
			Criteria critCat = getSession().createCriteria(Categoria.class);
			critCat.add(Restrictions.eq("departamento.idDepartamento", departamentoL));
			critCat.setProjection(Projections.property("idCategoria"));
			List<Long> cats = critCat.list();

			Criteria critSub = getSession().createCriteria(SubCategoria.class);
			if (cats.size() > 0)
				critSub.add(Restrictions.in("categoria.idCategoria", cats));
			critSub.setProjection(Projections.property("idSubCategoria"));
			List<Long> subs = critSub.list();

			if (subs.size() > 0)
				crit.add(Restrictions.in("subCategoria.idSubCategoria", subs));
		}
		if (categoria != null && !categoria.equals("")) {
			Criteria critSub = getSession().createCriteria(SubCategoria.class);
			Long categoriaL = Long.valueOf(categoria);
			critSub.add(Restrictions.eq("categoria.idCategoria", categoriaL));
			critSub.setProjection(Projections.property("idSubCategoria"));
			List<Long> subs = critSub.list();

			if (subs.size() > 0)
				crit.add(Restrictions.in("subCategoria.idSubCategoria", subs));
		}
		if (subCategoria != null && !subCategoria.equals("")) {
			Long subCategoriaL = Long.valueOf(subCategoria);
			crit.add(Restrictions.eq("subCategoria.idSubCategoria", subCategoriaL));
		}
		// ordenacao
		ordena(ordena, crit);
		// paginacao
		if (!count) {
			if (page == null || page.equals(""))
				page = "0";
			int size = Integer.valueOf(page);
			crit.setFirstResult(size * IndexController.PAGINATION_SIZE);
			crit.setMaxResults(IndexController.PAGINATION_SIZE);
		} else {
			crit.setProjection(Projections.rowCount());
		}
		return crit;
	}

	private void ordena(String ordena, Criteria crit) {
		if (ordena != null && !ordena.equals("")) {
			if (ordena.equals("1"))
				crit.addOrder(Order.desc("precoVenda"));
			else if (ordena.equals("2"))
				crit.addOrder(Order.asc("precoVenda"));
			else if (ordena.equals("3"))
				crit.addOrder(Order.asc("titulo"));
		} else {
			// ordenacao default eh por titulo
			crit.addOrder(Order.asc("titulo"));
		}
	}

	/**
	 * Busca o produto pelo ID
	 * 
	 * @param id
	 * @return
	 */
	public Produto findById(Long id) {
		Criteria crit = getSession().createCriteria(getAccessedClass());
		crit.add(Restrictions.eq("idProduto", id));
		List<Produto> lista = crit.list();
		if (lista != null && lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}
}
