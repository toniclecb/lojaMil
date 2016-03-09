package br.com.lojaMil.controller;

import java.util.List;

import org.hibernate.Transaction;

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
import br.com.lojaMil.dao.PedidoItemDao;
import br.com.lojaMil.dao.ProdutoDao;
import br.com.lojaMil.entities.Departamento;
import br.com.lojaMil.entities.Pedido;
import br.com.lojaMil.entities.PedidoItem;
import br.com.lojaMil.entities.Produto;
import br.com.lojaMil.entities.UsuarioLogado;
import br.com.lojaMil.interceptors.LoggedIn;

/**
 * 
 * @author Cleiton Balansin
 * 
 */
@Resource
public class ProdutoController {

	private final Result result;
	private DepartamentoDao departamentoDao;
	private ProdutoDao produtoDao;
	private final UsuarioLogado usuarioLogado;
	private Validator validator;
	private PedidoDao pedidoDao;
	private PedidoItemDao pedidoItemDao;

	public ProdutoController(Result result, Validator validator, UsuarioLogado usuarioLogado,
			DepartamentoDao departamentoDao, ProdutoDao produtoDao, PedidoDao pedidoDao, PedidoItemDao pedidoItemDao) {
		this.result = result;
		this.validator = validator;
		this.usuarioLogado = usuarioLogado;
		this.departamentoDao = departamentoDao;
		this.produtoDao = produtoDao;
		this.pedidoDao = pedidoDao;
		this.pedidoItemDao = pedidoItemDao;
	}

	/**
	 * Deve receber o id do produto para entao retornar o objeto para a pagina
	 * 
	 * @param prod
	 *            id do produto
	 */
	@Path("/produto")
	public void produto(Long prod) {
		List<Departamento> departamentos = departamentoDao.findAll();
		result.include("departamentos", departamentos);

		if (usuarioLogado.isLogged()) {
			result.include("username", usuarioLogado.getUser().getNome());
			PedidoController.getPedidoNaoFinalizado(pedidoDao, usuarioLogado);
			int n = usuarioLogado.getPedidoItensSize();
			result.include("carrinhoquant", n);
		} else {
			result.include("carrinhoquant", 0);
		}
		//
		Produto produto = produtoDao.findById(prod);
		if (produto != null) {
			result.include("produto", produto);
		} else {
			// nao envia o produto para a pagina
			result.include("noproduto", "noproduto");
		}
	}

	/**
	 * Adiciona produto ao pedido atual (carrinho)
	 * 
	 * @param pedidoItem
	 */
	@Post("/compra")
	@Consumes("application/json")
	public void compra(PedidoItem pedidoItem) {
		if (usuarioLogado == null || !usuarioLogado.isLogged()) {
			validator.add(new I18nMessage("error", "erro.precisalogin", new Object()));
			validator.onErrorSendBadRequest();
		} else {
			Produto prod = produtoDao.findById(pedidoItem.getProduto().getIdProduto());
			usuarioLogado.compraItem(pedidoItem, prod);
			
			Transaction tx = pedidoDao.getSession().beginTransaction();
			Pedido p = usuarioLogado.getPedido();
			try {
				p.setFinalizado(0);
				p.calculaValorTotal();
				p.setUsuario(usuarioLogado.getUser());
				// se o pedido ainda nao foi salvo no banco salva!
				if (p.getIdPedido() == null || p.getIdPedido() == 0)
					pedidoDao.add(p);
				for (PedidoItem pi : p.getPedidoItems()) {
					pi.setPedido(p);
					pedidoItemDao.add(pi);
				}
//				p = (Pedido) pedidoDao.add(p);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				validator.add(new I18nMessage("error", "erro.compra", new Object()));
				validator.onErrorSendBadRequest();
			}
			usuarioLogado.setPedido(p);
			
			result.use(Results.status()).ok();
		}
	}

	/**
	 * Exibe as informacoes do carrinho
	 */
	@Path("/carrinho")
	public void carrinho() {
		List<Departamento> departamentos = departamentoDao.findAll();
		result.include("departamentos", departamentos);

		if (usuarioLogado.isLogged()) {
			result.include("username", usuarioLogado.getUser().getNome());
			
			PedidoController.getPedidoNaoFinalizado(pedidoDao, usuarioLogado);
			
			int n = usuarioLogado.getPedidoItensSize();
			result.include("carrinhoquant", n);

			result.include("pedido", usuarioLogado.getPedido());
		} else {
			result.include("carrinhoquant", 0);
		}
	}
	
	@Path("/removeProduto")
	public void removeProduto(Long pedidoItem) {
		if (usuarioLogado.isLogged()) {
			PedidoItem pedidoItemEntity = pedidoItemDao.findById(pedidoItem);
			Transaction tx = pedidoDao.getSession().beginTransaction();
			try {
				pedidoItemDao.remove(pedidoItemEntity);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				validator.add(new I18nMessage("error", "erro.remover.item", new Object()));
				validator.onErrorSendBadRequest();
			}
			
			result.use(Results.logic()).redirectTo(ProdutoController.class).carrinho();
		}
	}
}
