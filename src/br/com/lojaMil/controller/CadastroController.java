package br.com.lojaMil.controller;

import java.util.Date;
import java.util.List;

import org.hibernate.Transaction;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import br.com.lojaMil.dao.DepartamentoDao;
import br.com.lojaMil.dao.UsuarioDao;
import br.com.lojaMil.entities.Departamento;
import br.com.lojaMil.entities.Usuario;
import br.com.lojaMil.entities.UsuarioLogado;

/**
 * Classe responsavel por cadastrar novos usuarios.
 * 
 * @author Cleiton Balansin
 * 
 */
@Resource
public class CadastroController {

	private final Result result;
	private UsuarioDao usuarioDao;
	private DepartamentoDao departamentoDao;
	private final UsuarioLogado usuarioLogado;
	private Validator validator;

	public CadastroController(Result result, UsuarioDao usuarioDao, UsuarioLogado usuarioLogado, Validator validator,
			DepartamentoDao departamentoDao) {
		this.result = result;
		this.validator = validator;
		this.usuarioDao = usuarioDao;
		this.usuarioLogado = usuarioLogado;
		this.departamentoDao = departamentoDao;
	}

	@Path("/cadastro")
	public void cadastro() {
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

	@Post("/cadastrar")
	@Consumes("application/json")
	public void cadastrar(final Usuario usuario) {
		usuario.setDataCadastro(new Date());
		// Verifica se o usuario satifaz as validacoes
		validator.checking(new Validations() {
			{
				that(usuario.getNome() != null && usuario.getNome().length() >= 0, "usuario.nome", "cad.obriga");
				that(usuario.getLogin() != null && usuario.getLogin().length() >= 0, "usuario.login", "cad.obriga");
				that(usuario.getPassword() != null && usuario.getPassword().length() >= 0, "usuario.password", "cad.obriga");

				// email
				that(usuario.getEmail() != null && usuario.getEmail().length() >= 0, "usuario.email", "cad.obriga");
			}
		});
		// se houve erro de validacao devolve uma mensagem de erro a pagina
		if (validator.hasErrors()) {
			List<Message> erros = validator.getErrors();
			result.use(Results.json()).from(erros, "erros").serialize();
			validator.onErrorSendBadRequest();
		} else {
			// inserindo usuario
			Transaction tx = usuarioDao.getSession().beginTransaction();
			try {
				usuarioDao.add(usuario);
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				validator.add(new I18nMessage("error", "erro.cadastro", new Object()));
				validator.onErrorSendBadRequest();
			}
			// se tudo ocorreu bem devolve OK para a pagina
			result.use(Results.status()).ok();
		}
	}
}
