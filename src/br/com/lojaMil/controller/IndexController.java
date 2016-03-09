package br.com.lojaMil.controller;

import java.util.List;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.view.Results;
import br.com.lojaMil.dao.DepartamentoDao;
import br.com.lojaMil.dao.ProdutoDao;
import br.com.lojaMil.dao.UsuarioDao;
import br.com.lojaMil.entities.Departamento;
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

	private final Result result;
	private UsuarioDao usuarioDao;
	private DepartamentoDao departamentoDao;
	private ProdutoDao produtoDao;
	private final UsuarioLogado usuarioLogado;
	private Validator validator;

	public IndexController(Result result, UsuarioDao usuarioDao, UsuarioLogado usuarioLogado,
			Validator validator, DepartamentoDao departamentoDao, ProdutoDao produtoDao) {
		this.result = result;
		this.validator = validator;
		this.usuarioDao = usuarioDao;
		this.usuarioLogado = usuarioLogado;
		this.departamentoDao = departamentoDao;
		this.produtoDao = produtoDao;
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

	/**
	 * remove o usuario da sessao
	 */
	@Post("/logout")
	public void logout() {
		usuarioLogado.logout();
		result.use(Results.logic()).redirectTo(IndexController.class).index();
	}

	/**
	 * Executa buscas requisitadas atraves do menu de categorias
	 * 
	 * @param departamento
	 * @param categoria
	 * @param subCategoria
	 */
	@Post("/buscarInicial")
	public void buscarInicial(String departamento, String categoria, String subCategoria) {
		List<Produto> produtos = produtoDao.find(departamento, categoria, subCategoria);
		result.use(Results.json()).from(produtos, "produtos").serialize();
	}

	@Post("/buscar")
	@Consumes("application/json")
	public void buscar(String busca) {
		List<Produto> produtos = produtoDao.findTitulo(busca);
		result.use(Results.json()).from(produtos, "produtos").serialize();
	}

	@Post("/login")
	@Consumes("application/json")
	public void login(Usuario usuario) {
		List<Usuario> lista = usuarioDao.findByLogin(usuario.getLogin());
		if (lista.size() == 1) {
			if (usuario.getPassword().equals(lista.get(0).getPassword())) {
				// se o usuario eh valido entao adiciona ele na session
				usuarioLogado.login(lista.get(0));
				result.use(Results.json()).from(usuarioLogado.getUser().getNome(), "nomeusuario").serialize();
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
}