package br.com.lojaMil.interceptors;


import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.view.Results;
import br.com.lojaMil.controller.IndexController;
import br.com.lojaMil.entities.UsuarioLogado;

/**
 * 
 * @author Cleiton Balansin
 */

@Intercepts
@RequestScoped
public class AccessControllerInterceptor implements Interceptor {
	private Result result;
	private UsuarioLogado usuarioLogado;

	public AccessControllerInterceptor(Result result, UsuarioLogado usuarioLogado) {
		this.result = result;
		this.usuarioLogado = usuarioLogado;
	}

	public boolean accepts(ResourceMethod method) {
		// verifica se o metodo acessado deve ser interceptado 
		return method.containsAnnotation(LoggedIn.class);
	}

	/**
	 * Verifica se o usuario esta logado na aplicacao
	 * caso o usuario NAO esteja logado retorna para o index 
	 * caso o usuario esteja logado continua a execucao normalmente
	 */
	public void intercept(InterceptorStack stack, ResourceMethod res, Object arg2) throws InterceptionException {
		if (!usuarioLogado.isLogged()){
			// caso o usuario NAO esteja logado retorna para o index 
			result.redirectTo(IndexController.class).index();
		} else {
			// caso o usuario esteja logado continua a execucao normalmente
			stack.next(res, arg2);
		}
	}

}
