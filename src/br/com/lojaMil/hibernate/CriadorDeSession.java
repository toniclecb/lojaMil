package br.com.lojaMil.hibernate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
public class CriadorDeSession implements ComponentFactory<Session>{
	  
	private final SessionFactory factory;
	private Session session;
	
		
	public CriadorDeSession(SessionFactory factory) {
		this.factory = factory;
	}

	public Session getInstance() {
		return this.session;
	}
	
	// seria o mesmo que anotar com @RequestScoped
	@PostConstruct
	public void open(){
		this.session = factory.openSession();
	}
	
	@PreDestroy
	public void close(){
		this.session.close();
	}
}
