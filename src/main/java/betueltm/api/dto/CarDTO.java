package betueltm.api.dto;

import java.io.Serializable;

public class CarDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String color;
	private String model;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
}
