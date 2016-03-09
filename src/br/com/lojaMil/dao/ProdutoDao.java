package br.com.lojaMil.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.lojaMil.entities.Categoria;
import br.com.lojaMil.entities.Departamento;
import br.com.lojaMil.entities.Produto;
import br.com.lojaMil.entities.SubCategoria;
import br.com.lojaMil.hibernate.GenericDao;

/**
 * Executa as buscas relacionadas a Produto
 * @author Cleiton Balansin
 *
 */
@Component
public class ProdutoDao extends GenericDao<Produto>{

	public ProdutoDao(Session session) {
		super(session, Produto.class);
	}

	/**
	 * Busca pelo titulo do produto.<br>
	 * A busca eh feita com base em  "<code>ilike %titulo%</code>"<br>
	 * Caso o parametro <code>titulo</code> tenha espacos a busca sera feita
	 * como uma disjunction (OR).
	 * @param titulo string de busca
	 * @return lista de produtos
	 */
	public List<Produto> findTitulo(String titulo){
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
		return crit.list();
	}
	
	public List<Produto> findAll(){
		Criteria crit = getSession().createCriteria(getAccessedClass());
		return crit.list();
	}

	/**
	 * Executa a busca por departamento, categoria ou subCategoria.<br>
	 * Se departamento for != null faz a busca usando departamento<br>
	 * Senao se categoria for != null faz a busca usando categoria<br>
	 * Senao faz a busca usando a subcategoria.
	 * @param departamento
	 * @param categoria
	 * @param subCategoria
	 * @return
	 */
	public List<Produto> find(String departamento, String categoria, String subCategoria) {
		Criteria crit = getSession().createCriteria(getAccessedClass());
		if (departamento != null && !departamento.equals("")){
//			nao precisa buscar os departamentos
//			Criteria critDep = getSession().createCriteria(Departamento.class);
//			critDep.add(Restrictions.eq("idDepartamento", departamento));
//			List<Departamento> deps = critDep.list();
			
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
		if (categoria != null && !categoria.equals("")){
			Criteria critSub = getSession().createCriteria(SubCategoria.class);
			Long categoriaL = Long.valueOf(categoria);
			critSub.add(Restrictions.eq("categoria.idCategoria", categoriaL));
			critSub.setProjection(Projections.property("idSubCategoria"));
			List<Long> subs = critSub.list();
			
			if (subs.size() > 0)
				crit.add(Restrictions.in("subCategoria.idSubCategoria", subs));
		}
		if (subCategoria != null && !subCategoria.equals("")){
			Long subCategoriaL = Long.valueOf(subCategoria);
			crit.add(Restrictions.eq("subCategoria.idSubCategoria", subCategoriaL ));
		}
		return crit.list();
	}

	/**
	 * Busca o produto pelo ID
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
