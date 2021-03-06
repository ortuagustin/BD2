package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "apartments")
public class Apartment extends Property {
	private Integer rooms;

	public Apartment(String name, String description, Double price, Integer capacity, City city, Integer rooms) {
		super(name, description, price, capacity, city);
		this.rooms = rooms;
	}

	protected Apartment() {
		super();
	}

	public Integer getRooms() {
		return this.rooms;
	}
}