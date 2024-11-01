package betueltm.persistence;

import java.util.Objects;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

public class Repository<T> {
	
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
		return find(entityClass, primaryKey);
	}
	
	public <E> E find(Class<E> entityClass, Long primaryKey) {
		try{
		    return getEntityManager().find(entityClass, primaryKey);
		}catch(EntityNotFoundException e){
            return null;
        }
	}
}
