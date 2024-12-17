package betueltm.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import betueltm.architecture.cache.CacheEvict;
import betueltm.architecture.cache.Cacheable;
import betueltm.architecture.persistence.Repository;
import betueltm.model.Car;

public class CarRepository extends Repository<Car> {

	public CarRepository() {
		super(Car.class);
	}

	@Cacheable
	public List<Car> findAll(Long number) {
		StringBuilder query = new StringBuilder();
		query.append("select car from Car car");
		TypedQuery<Car> typedQuery = createTypedQuery(query);
		typedQuery.setMaxResults(100);
		return getResultList(typedQuery);
	}
	
	@Override
	@Cacheable(key = "primaryKey")
	public Car find(Long primaryKey) {
		return super.find(primaryKey);
	}
	
	@Override
	@CacheEvict(key = "entity.id", evictAllEntries = true)
	public void remove(Car entity) {
		super.remove(entity);
	}
	
	@Override
	@CacheEvict(key = "primaryKey")
	public void remove(Long primaryKey) {
		super.remove(primaryKey);
	}
	
	@Override
	@CacheEvict(key = "entity.id")
	public void persist(Car entity) {
		super.persist(entity);
	}
}
