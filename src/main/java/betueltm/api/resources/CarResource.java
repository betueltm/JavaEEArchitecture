package betueltm.api.resources;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import betueltm.api.dto.CarDTO;
import betueltm.architecture.cache.CacheFactory;
import betueltm.model.Car;
import betueltm.repository.CarRepository;

@Path("car")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean
@Transactional
public class CarResource {

	@GET
	@Path("test")
	public String test() {
		CarRepository carRepository = new CarRepository();
		List<Car> allCars = carRepository.findAll();
		CacheFactory.getCache().setValue("cars", allCars);
		return "ok";
	}
	
	@GET
	@Path("{id}")
	public CarDTO getCar(@PathParam("id") Long id) {
		CarRepository carRepository = new CarRepository();
		Car car = carRepository.find(id);
		return convertToDTO(car);
	}
	
	@PUT
	public Long insert(CarDTO carDTO) {
		Car car = createNewFromDTO(carDTO);
		CarRepository carRepository = new CarRepository();
		carRepository.persist(car);
		return car.getId();
	}
	
	private Car createNewFromDTO(CarDTO carDTO) {
		Car car = new Car();
		car.setId(carDTO.getId());
		car.setColor(carDTO.getColor());
		car.setModel(carDTO.getModel());
		return car;
	}
	
	private CarDTO convertToDTO(Car car) {
		CarDTO carDTO = new CarDTO();
		carDTO.setId(car.getId());
		carDTO.setColor(car.getColor());
		carDTO.setModel(car.getModel());
		return carDTO;
	}
}
