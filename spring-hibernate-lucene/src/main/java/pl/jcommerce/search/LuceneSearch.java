package pl.jcommerce.search;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.jcommerce.domain.Base;

@Repository
@Transactional
public abstract class LuceneSearch<T extends Base> {

    private static final Logger LOG = Logger.getLogger(LuceneSearch.class);
    
    private Class<T> tClass;
    
    @Autowired
    private EntityManager entityManager;
    
    private FullTextEntityManager fullTextEntityManager;
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init(){
        final ParameterizedType type = (ParameterizedType)getClass().getGenericSuperclass();
        tClass = (Class<T>)type.getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public List<T> search(String query){
        FullTextQuery fulltextSearchQuery = prepareQuery(query);
        
        return fulltextSearchQuery.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Object[]> search(String query, String... projectionFields){
        FullTextQuery fulltextSearchQuery = prepareQuery(query);
        
        if(Objects.nonNull(projectionFields)){
            fulltextSearchQuery.setProjection(projectionFields);
        }
        
        return fulltextSearchQuery.getResultList();
    }
    
    private FullTextQuery prepareQuery(String query){
        QueryParser queryParser = new QueryParser("title", 
                getFullTextEntityManager().getSearchFactory().getAnalyzer(tClass));
        
        Query luceneQuery = null;
        try {
            luceneQuery = queryParser.parse(query);
        } catch (ParseException e) {
            LOG.error(e);
        }
        
        return getFullTextEntityManager().createFullTextQuery(luceneQuery, tClass);
    }
    
    
    @SuppressWarnings("unchecked")
    public List<T> rangeQuery(String fieldName, Object from, Object to){
        
        QueryBuilder dateQB = getFullTextEntityManager().getSearchFactory()
                .buildQueryBuilder().forEntity( tClass ).get();
        
        Query rangeQuery = dateQB
              .range()
              .onField(fieldName)
              .from(from).to(to)
              .createQuery();
       
       FullTextQuery fullTextQuery = getFullTextEntityManager().createFullTextQuery(rangeQuery, tClass);
       
       return fullTextQuery.getResultList();
    }

    public void clearIndex(){
        getFullTextEntityManager().purgeAll(tClass);
    }

    public FullTextEntityManager getFullTextEntityManager() {
        if(Objects.isNull(this.fullTextEntityManager)){
            return org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        }
        
        return this.fullTextEntityManager;
    }
    
    
}
