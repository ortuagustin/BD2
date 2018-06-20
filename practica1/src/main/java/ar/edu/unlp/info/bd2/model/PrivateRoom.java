package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "private_rooms")
public class PrivateRoom extends Property {
	Integer beds;

	public PrivateRoom(String name, String description, Double price, Integer capacity, City city, Integer beds) {
		super(name, description, price, capacity, city);
		this.beds = beds;
	}

	protected PrivateRoom() {
		super();
	}
}