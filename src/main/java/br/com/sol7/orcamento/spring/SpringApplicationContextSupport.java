package br.com.sol7.orcamento.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Victor Lindberg
 */
@Component
public class SpringApplicationContextSupport implements ApplicationContextAware{

	private ApplicationContext applicationContext;

	private static SpringApplicationContextSupport instance;

	private SpringApplicationContextSupport() {
	}

	@PostConstruct
	public void initSingletonInstance(){
		instance = this;
	}

	public static SpringApplicationContextSupport getInstance() {
		return instance;
	}

	@Override
	public final void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public <E> E getBean(String name){
		return (E) getApplicationContext().getBean(name);
	}

	public <E> E getBean(String name, Class<E> requiredType){
		return getApplicationContext().getBean(name, requiredType);
	}

	public <E> E getBean(Class<E> requiredType){
		return getApplicationContext().getBean(requiredType);
	}

	public Object getBean(String name, Object... args){
		return getApplicationContext().getBean(name, args);
	}
}
