package betueltm.javatests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExemple {
	public static void main(String[] args) {
		List<Car> cars = Arrays.asList(new Car("blue"),new Car("white"),new Car("red"),new Car("gray"),new Car("black"),new Car("white"));
		List<Car> whiteCars = cars.stream().filter().collect(Collectors.toList());
		
		whiteCars.forEach(car -> System.out.println(car.getColor()));
	}
}
