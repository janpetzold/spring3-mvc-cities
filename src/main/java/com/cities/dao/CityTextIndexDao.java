package com.cities.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cities.controller.CityController;
import com.cities.model.City;

@Repository
public class CityTextIndexDao {	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(CityController.class);
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> searchSimilarCities(String term) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(City.class).get();
		Query luceneQuery = qb.keyword().fuzzy().onField("name").matching(term).createQuery();
		
		List<City> cities = new ArrayList<City>();
		List<String> citynames = new ArrayList<String>();
		
		int results = fullTextEntityManager.createFullTextQuery(luceneQuery, City.class).getResultSize();
		
		logger.info("Es gibt " + results + " Ergebnisse von hibernate-search.");
		
		if(results > 0) {
			FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, City.class);
			logger.info("Es wurden " + fullTextQuery.getResultSize() + " Ergebnisse im Lucene-Index gefunden.");
			cities = fullTextQuery.getResultList();
			for(City currentCity: cities) {
				citynames.add(currentCity.getName());
			}
		}
		
		fullTextEntityManager.close();
		return citynames;
	}
	
	public void createSearchIndex() throws InterruptedException {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer(City.class).startAndWait();
		fullTextEntityManager.close();
	}
}
