package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "private_rooms")
public class PrivateRoom extends Property {
	String description, cityName;
	Double price;
	Integer capacity;
	Integer beds;

	@Override
	public Double getPrice() {
		return 0.0;
	}

	public PrivateRoom() {
		super();
	}

	public PrivateRoom(String name, String description, Double price, Integer capacity, Integer beds, String cityName) {
		this();
		this.name = name;
		this.description = description;
		this.price = price;
		this.capacity = capacity;
		this.beds = beds;
		this.cityName = cityName;
	}
}