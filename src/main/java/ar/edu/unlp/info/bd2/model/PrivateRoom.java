package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "private_rooms")
public class PrivateRoom extends Property {
	String name, description, cityName;
	double price;
	int capacity;
	int beds;

	@Override
	public Double getPrice() {
		return 0.0;
	}

	public PrivateRoom() {}

	public PrivateRoom(String name, String description, double price, int capacity, int beds, String cityName) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.capacity = capacity;
		this.beds = beds;
		this.cityName = cityName;
	}
}