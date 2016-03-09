package br.com.lojaMil.interceptors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotacao para utilizacao em  metodos dos controllers que precisam ser acessados apenas por usuarios logados.
 * @author Cleiton Balansin
 * @since 29/02/2016
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)
public @interface LoggedIn {

}