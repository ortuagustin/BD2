package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "apartments")
public class Apartment extends Property {
	String name, description, cityName;
	double price;
	int capacity;
	int rooms;

	@Override
	public Double getPrice() {
		return 0.0;
	}

	public Apartment() {}

	public Apartment(String name, String description, double price, int capacity, int rooms,
			String cityName) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.capacity = capacity;
		this.rooms = rooms;
		this.cityName = cityName;
	}
}