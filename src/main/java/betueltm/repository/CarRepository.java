package betueltm.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import betueltm.architecture.persistence.Repository;
import betueltm.model.Car;

public class CarRepository extends Repository<Car> {

	public CarRepository() {
		super(Car.class);
	}

	public List<Car> findAll() {
		StringBuilder query = new StringBuilder();
		query.append("select * from Car");
		TypedQuery<Car> typedQuery = createTypedQuery(query);
		return getResultList(typedQuery);
	}
}
