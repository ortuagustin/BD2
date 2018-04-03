package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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