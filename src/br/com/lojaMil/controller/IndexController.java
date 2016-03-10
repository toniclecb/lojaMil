package br.com.lojaMil.controller;

import java.util.List;

import com.mysql.fabric.xmlrpc.base.Array;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.view.Results;
import br.com.lojaMil.dao.DepartamentoDao;
import br.com.lojaMil.dao.PedidoDao;
import br.com.lojaMil.dao.ProdutoDao;
import br.com.lojaMil.dao.UsuarioDao;
import br.com.lojaMil.entities.Departamento;
import br.com.lojaMil.entities.Pedido;
import br.com.lojaMil.entities.Produto;
import br.com.lojaMil.entities.Usuario;
import br.com.lojaMil.entities.UsuarioLogado;

/**
 * Trata as requisicoes para a pagina inicial, as requisicoes de login e logout
 * e as buscas de produto.
 * 
 * @author Cleiton Balansin
 * 
 */
@Resource
public class IndexController {

	public static final int PAGINATION_SIZE = 12;
	private final Result result;
	private UsuarioDao usuarioDao;
	private PedidoDao pedidoDao;
	private DepartamentoDao departamentoDao;
	private ProdutoDao produtoDao;
	private final UsuarioLogado usuarioLogado;
	private Validator validator;

	public IndexController(Result result, UsuarioDao usuarioDao, UsuarioLogado usuarioLogado,
			Validator validator, DepartamentoDao departamentoDao, ProdutoDao produtoDao, PedidoDao pedidoDao) {
		this.result = result;
		this.validator = validator;
		this.usuarioDao = usuarioDao;
		this.usuarioLogado = usuarioLogado;
		this.departamentoDao = departamentoDao;
		this.produtoDao = produtoDao;
		this.pedidoDao  = pedidoDao;
	}

	@Path({ "/", "/index" })
	public void index() {
		List<Departamento> departamentos = departamentoDao.findAll();
		result.include("departamentos", departamentos);

		if (usuarioLogado.isLogged()) {
			result.include("username", usuarioLogado.getUser().getNome());
			int n = usuarioLogado.getPedidoItensSize();
			result.include("carrinhoquant", n);
		} else {
			result.include("carrinhoquant", 0);
		}
	}

	
	
	
//	/**
//	 * Executa buscas requisitadas atraves do menu de categorias
//	 * 
//	 * @param departamento
//	 * @param categoria
//	 * @param subCategoria
//	 */
//	@Post("/buscarInicial")
//	public void buscarInicial(String departamento, String categoria, String subCategoria, String ordena) {
//		List<Produto> produtos = produtoDao.find(departamento, categoria, subCategoria, ordena);
//		result.use(Results.json()).from(produtos, "produtos").serialize();
//	}
//
//	@Post("/buscar")
////	@Consumes("application/json")
//	public void buscar(String busca, String ordena) {
//		List<Produto> produtos = produtoDao.findTitulo(busca, ordena);
//		result.use(Results.json()).from(produtos, "produtos").serialize();
//	}

	/**
	 * Executa a busca dos dados de produto
	 * @param titulo busca no titulo do produto
	 * @param departamento
	 * @param categoria
	 * @param subCategoria
	 * @param ordena deve ser 1, 2, ou 3
	 */
	@Post("/busca")
	public void busca(String titulo, String departamento, String categoria, String subCategoria, String ordena, String page) {
		List<Produto> produtos = produtoDao.find(titulo, departamento, categoria, subCategoria, ordena, page);
		Long count = produtoDao.count(titulo, departamento, categoria, subCategoria, ordena);
		Long c = count / PAGINATION_SIZE;
		if (count % PAGINATION_SIZE > 0)
			c++;
		Long nPages = Long.valueOf(c);
		result.use(Results.json()).from(new Object[]{produtos, nPages}, "produtos").serialize();
	}
	
	
	@Post("/login")
	@Consumes("application/json")
	public void login(Usuario usuario) {
		List<Usuario> lista = usuarioDao.findByLogin(usuario.getLogin());
		if (lista.size() == 1) {
			if (usuario.getPassword().equals(lista.get(0).getPassword())) {
				// se o usuario eh valido entao adiciona ele na session
				usuarioLogado.login(lista.get(0));
				PedidoController.getPedidoNaoFinalizado(pedidoDao, usuarioLogado);

				int n = usuarioLogado.getPedidoItensSize();
				String [] nomen = new String[2];
				nomen[0] = usuarioLogado.getUser().getNome();
				nomen[1] = ""+n;
				result.use(Results.json()).from(nomen, "nomeusuario_carrinho").serialize();

				result.use(Results.status()).ok();
			} else {
				validator.add(new I18nMessage("error", "erro.login", new Object()));
				validator.onErrorSendBadRequest();
			}
		} else {
			validator.add(new I18nMessage("error", "erro.login", new Object()));
			validator.onErrorSendBadRequest();
		}
	}
	
	/**
	 * remove o usuario da sessao
	 */
	@Post("/logout")
	public void logout() {
		usuarioLogado.logout();
		result.use(Results.logic()).redirectTo(IndexController.class).index();
	}
}
