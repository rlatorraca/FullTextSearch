package com.rlsp.ecommerce.util.jpa;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

@ApplicationScoped
public class FullTextEntityManagerProducer {

	@Inject
	private EntityManagerFactory factory;
	
	@PostConstruct
	public void init() {
		/**
		 * Encapsula o EntityManager do JPA
		 */		
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(factory.createEntityManager());
		
		try {
			/**
			 * Comeca o processo de indexicao do DB (nao se passa o que sera indexido nessa parte)
			 */
			fullTextEntityManager.createIndexer().startAndWait();
		
		} catch (InterruptedException e) {
		
			throw new RuntimeException("Erro indexando o banco de dados.", e);
		
		}
		
		fullTextEntityManager.close();
	}
	
	@Produces		//Produz um @FullTextSearc
	@RequestScoped  // Cada pesquisa e usuario precisara de um FullTextEntitManager
	@FullTextSearch
	public FullTextEntityManager createFullTextEntityManager() {
		return Search.getFullTextEntityManager(factory.createEntityManager());
	}
	
	
	/**
	 * FECHA o FullTextEntityManager 
	 */
	public void close(@FullTextSearch @Disposes FullTextEntityManager fullTextEntityManager) {
		fullTextEntityManager.close();
	}
	
}











