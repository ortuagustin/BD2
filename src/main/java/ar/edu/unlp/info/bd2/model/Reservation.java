package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {
	long propertyId, userId;
	Date from, to;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Reservation() {
	}

	public Reservation(long propertyId, long userId, Date from, Date to) {
		this.propertyId = propertyId;
		this.userId = userId;
		this.from = from;
		this.to = to;
	}

	/**
	  * @return the Price
	  */
	public Double getPrice() {
		return 0.0;
	}

	/**
	* @return the Property
	*/
	public Property getProperty() {
		return null;
	}

	/**
	 * @return the User
	 */
	public User getUser() {
		return null;
	}

	/**
	 * @return the Id
	 */
	public Long getId() {
		return id;
	}

	/**
	* @return the Status
	*/
	public ReservationStatus getStatus() {
		return ReservationStatus.FINISHED;
	}
}