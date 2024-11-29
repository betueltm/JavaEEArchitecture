package betueltm.architecture.persistence;

import java.util.List;
import java.util.Objects;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

public class Repository<T extends Identifier<Long>> {
	
	private EntityManager entityManager;
	protected Class<T> entityClass;

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
		
		if(Objects.isNull(entity)) {
			entity = find(entityClass, primaryKey);
		}
		
		return entity;
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
	}
	
	public TypedQuery<T> createTypedQuery(StringBuilder sqlStringBuilder){
		return createTypedQuery(sqlStringBuilder.toString());
	}
	
	private TypedQuery<T> createTypedQuery(String sqlString) {
		TypedQuery<T> typedQuery = getEntityManager().createQuery(sqlString, entityClass);
		return typedQuery;
	}
	
	protected <C> List<C> getResultList(TypedQuery<C> typedQuery) {
	  return typedQuery.getResultList();
	}
}
