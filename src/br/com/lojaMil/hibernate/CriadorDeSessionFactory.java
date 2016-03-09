package br.com.lojaMil.hibernate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class CriadorDeSessionFactory implements ComponentFactory<SessionFactory>{
	// em vez de fazer um factory global e final usa-se as anotacoes @PostConstruct e o @PreDestroy

	private SessionFactory factory;
	
	@PostConstruct
	public void open() {
		Configuration conf = new Configuration();
		conf.configure();
		factory = conf.buildSessionFactory();
	}
	
	public SessionFactory getInstance() {
		return this.factory;
	}
	
	@PreDestroy
	public void close() {
		this.factory.close();
	}
}
