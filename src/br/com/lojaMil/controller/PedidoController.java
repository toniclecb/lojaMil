package br.com.lojaMil.controller;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.simplemail.Mailer;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.view.Results;
import br.com.lojaMil.dao.DepartamentoDao;
import br.com.lojaMil.dao.PedidoDao;
import br.com.lojaMil.dao.PedidoItemDao;
import br.com.lojaMil.entities.Departamento;
import br.com.lojaMil.entities.Pedido;
import br.com.lojaMil.entities.PedidoItem;
import br.com.lojaMil.entities.UsuarioLogado;
import br.com.lojaMil.interceptors.LoggedIn;

/**
 * 
 * @author Cleiton Balansin
 * 
 */
@Resource
public class PedidoController {

	private final Result result;
	private DepartamentoDao departamentoDao;
	private PedidoDao pedidoDao;
	private PedidoItemDao pedidoItemDao;
	private final UsuarioLogado usuarioLogado;
	private Validator validator;
	private Mailer mailer;

	// private TemplateMailer template;

	public PedidoController(Result result, UsuarioLogado usuarioLogado, Validator validator,
			DepartamentoDao departamentoDao, PedidoDao pedidoDao, PedidoItemDao pedidoItemDao, Mailer mailer) {
		// TemplateMailer template,
		this.result = result;
		this.validator = validator;
		this.usuarioLogado = usuarioLogado;
		this.departamentoDao = departamentoDao;
		this.pedidoDao = pedidoDao;
		this.pedidoItemDao = pedidoItemDao;
		// this.template = template;
		this.mailer = mailer;
	}

	@LoggedIn
	@Path("/pedidos")
	public void pedidos() {
		List<Departamento> departamentos = departamentoDao.findAll();
		result.include("departamentos", departamentos);

		// se o usuario esta logado envia os dados do usuario e dos pedidos para
		// a pagina
		if (usuarioLogado.isLogged()) {
			result.include("username", usuarioLogado.getUser().getNome());
			
			PedidoController.getPedidoNaoFinalizado(pedidoDao, usuarioLogado);
			int n = usuarioLogado.getPedidoItensSize();
			result.include("carrinhoquant", n);

			List<Pedido> pedidos = pedidoDao.findByUsuario(usuarioLogado.getUser());
			result.include("pedidos", pedidos);
			result.include("pedidoquant", pedidos.size());
		} else {
			result.include("carrinhoquant", 0);
			result.include("pedidoquant", 0);
		}
	}

	/**
	 * Finaliza o pedido que esta sendo realizado no momento;<br>
	 * Envia email caso o pedido seja finalizado.
	 */
	@Path("/finalizaPedido")
	public void finalizaPedido() {
		if (usuarioLogado.isLogged()) {
			long sizeListaPedidos = pedidoDao.count(usuarioLogado.getUser());
			Transaction tx = pedidoDao.getSession().beginTransaction();
			Pedido p = usuarioLogado.getPedido();
			try {
				p.setFinalizado(1);
				p.calculaValorTotal();
				p.setUsuario(usuarioLogado.getUser());
				for (PedidoItem pi : p.getPedidoItems()) {
					pedidoItemDao.atualiza(pi);//add(pi);
				}
				pedidoDao.atualiza(p);//add(p);
				tx.commit();
				// enviar email
				enviaEmail(usuarioLogado, p, sizeListaPedidos);
			} catch (Exception e) {
				tx.rollback();
				validator.add(new I18nMessage("error", "erro.finalizar", new Object()));
				validator.onErrorSendBadRequest();
			}
			// retira o pedido da edicao
			usuarioLogado.setPedido(null);
		} else {
			validator.add(new I18nMessage("error", "erro.login", new Object()));
			validator.onErrorSendBadRequest();
		}

		result.use(Results.status()).ok();
		// result.use(Results.logic()).redirectTo(IndexController.class).index();
	}

	/**
	 * Envia email para o usuario que finalizou o pedido.<br>
	 * As configuracoes para envio correto de email estao em
	 * production.properties ou environment.properties
	 * 
	 * @param user
	 * @param p
	 * @param sizeListaPedidos
	 */
	private void enviaEmail(UsuarioLogado user, Pedido p, long sizeListaPedidos) {
		// FIXME Existe a alternativa de uso de template de email para construir
		// um email e codigo mais limpo.
		// basta utilizar a classe TemplateMailer para criar template de email.
		Email email = new SimpleEmail();
		Locale ptBR = new Locale("pt", "BR");
		ResourceBundle bundle = ResourceBundle.getBundle("messages", ptBR);
		try {
			if (sizeListaPedidos == 0)
				email.setSubject(bundle.getString("finalizadosucesso.primeiro"));
			else
				email.setSubject(bundle.getString("finalizadosucesso"));
			email.addTo(user.getUser().getEmail());
			StringBuffer b = new StringBuffer("");
			b.append("<html xmlns=\'http://www.w3.org/1999/xhtml\'>");
			b.append("<head>		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			b.append("<title>Pedido efetuado com Sucesso!</title> </head> 	<body>");
			b.append("<p>Olá, <b>" + user.getUser().getNome() + "</b>");
			// dando boas vindas e agradecendo pela primeira compra
			if (sizeListaPedidos == 0)
				b.append(", seja bem-vindo a LojaMil. Ficamos gratos de tê-lo como cliente.");
			b.append("</p><br /><br /> Seu pedido foi efetuado com Sucesso</b>.<br /><br />");
			b.append("Seu pedido contém " + p.getPedidoItems().size() + " item(s).<br /><br />");
			b.append("O valor total de seu pedido é de R$ " + p.getValorTotal() + ".<br /><br />");
			b.append("</body>" + "</html>");
			email.setMsg(b.toString());
			mailer.send(email);
		} catch (Exception e) {
			Logger.getLogger(ProdutoController.class).error("Não foi possivel enviar email para o pedido: " + p.getIdPedido(),
					e);
			e.printStackTrace();
		}
	}
	
	public static void getPedidoNaoFinalizado(PedidoDao pedidoDao, UsuarioLogado usuarioLogado) {
		try {
			Pedido pedido = pedidoDao.findByUsuarioNaoFinalizado(usuarioLogado.getUser());
			usuarioLogado.setPedido(pedido);
		} catch (Exception e) {
			usuarioLogado.setPedido(null);
		}
	}
}
