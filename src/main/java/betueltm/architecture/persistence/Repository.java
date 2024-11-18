package betueltm.architecture.persistence;

import java.util.Objects;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import betueltm.architecture.cache.CacheProviderFactory;

public class Repository<T extends Identifier<Long>> {
	
	private EntityManager entityManager;
	protected Class<T> entityClass;
	private boolean cacheEnabled = true; 

	public Repository(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected EntityManager getEntityManager() {
    	if(Objects.isNull(entityManager)){
    		try { entityManager = (EntityManager) new InitialContext().lookup("java:app/EntityManager");
    		} catch (NamingException e) { throw new RuntimeException(e);} 
    	}
    	return entityManager;
    }
	
	public T find(Long primaryKey) {
		if(Objects.isNull(primaryKey)) return null;
		T entity = null;
		
		if(cacheEnabled) entity = findInCache(primaryKey);
		
		if(Objects.isNull(entity)) {
			entity = find(entityClass, primaryKey);
			if(Objects.nonNull(entity)) insertOrUpdateCache(entity);
		}
		
		return entity;
	}
	
	private T findInCache(Long primaryKey) {
		return CacheProviderFactory.getCache().getValueAsObject(primaryKey.toString(), entityClass);
	}

	private <E> E find(Class<E> entityClass, Long primaryKey) {
		try{
		    return getEntityManager().find(entityClass, primaryKey);
		}catch(EntityNotFoundException e){
            return null;
        }
	}
	
	public void persist(T entity) {
		getEntityManager().persist(entity);
		if(cacheEnabled) insertOrUpdateCache(entity);
	}

	private void insertOrUpdateCache(T entity) {
		CacheProviderFactory.getCache().setValue(entity.getId().toString(), entity);
	}
}
