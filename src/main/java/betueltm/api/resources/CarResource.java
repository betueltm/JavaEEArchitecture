package betueltm.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import betueltm.javatests.Car;
import betueltm.repository.CarRepository;

@Path("car")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {

	@GET
	@Path("test")
	public String test() {
		CarRepository carRepository = new CarRepository();
		Car car = carRepository.find(1L);
		
		return "car " + car.getId() + ", " + car.getColor() + ", "+ car.getModel() + " ";
	}
}
