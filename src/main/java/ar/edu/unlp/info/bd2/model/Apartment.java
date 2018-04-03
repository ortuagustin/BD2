package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "apartments")
public class Apartment extends Property {
	private String description, cityName;
	private Double price;
	private Integer capacity;
	private Integer rooms;

	@Override
	public Double getPrice() {
		return 0.0;
	}

	public Apartment() {
		super();
	}

	public Apartment(String name, String description, Double price, Integer capacity, Integer rooms,
			String cityName) {
		this();
		this.name = name;
		this.description = description;
		this.price = price;
		this.capacity = capacity;
		this.rooms = rooms;
		this.cityName = cityName;
	}
}