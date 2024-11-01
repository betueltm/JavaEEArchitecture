package betueltm.repository;

import betueltm.javatests.Car;
import betueltm.persistence.Repository;

public class CarRepository extends Repository<Car> {

	public CarRepository() {
		super(Car.class);
	}
}
