package betueltm.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import betueltm.architecture.persistence.Identifier;

@Entity
public class Car implements Identifier<Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	private String color;
	private String model;

	public Car(){ }
	
    public Car(String color){
        this.color = color;
    }

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
