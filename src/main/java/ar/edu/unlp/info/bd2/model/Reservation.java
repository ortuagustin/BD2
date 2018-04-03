package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.joda.time.DateTime;
import org.joda.time.Days;

@Entity
@Table(name = "reservations")
public class Reservation {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private User user;
  @ManyToOne
  private Property property;

  // los defino como dateFrom y dateTo porque "from" y "to" son palabras reservadas
  private Date dateFrom, dateTo;
  // lo defino como reservationStatus porque "status" es palabra reservada
  private ReservationStatus reservationStatus;

  public Reservation(Property property, User user, Date from, Date to) {
    this();
    this.property = property;
    this.user = user;
    this.dateFrom = from;
    this.dateTo = to;
    this.reservationStatus = ReservationStatus.TO_CONFIRM;
  }

  protected Reservation() {
    super();
  }

  /**
  * @return the Price
  */
  public Double getPrice() {
    return this.getRentedNights() * property.getPrice();
  }

  /**
  * @return the Property
  */
  public Property getProperty() {
    return property;
  }

  /**
   * @return the User
   */
  public User getUser() {
	  return user;
  }

  /**
   * @return the Id
   */
  public Long getId() {
	  return id;
  }

  /**
  * @return the ReservationStatus
  */
  public ReservationStatus getStatus() {
    return reservationStatus;
  }

  /**
  * @return the Date the Reservation starts
  */
  public Date getFrom() {
    return dateFrom;
  }

  /**
  * @return the Date the Reservation ends
  */
  public Date getTo() {
    return dateTo;
  }

  /**
  * @return the DateTime the Reservation starts
  */
  private DateTime getFromAtDayStart() {
    return new DateTime(this.getFrom()).withTimeAtStartOfDay();
  }

  /**
  * @return the DateTime the Reservation ends
  */
  private DateTime getToAtDayStart() {
    return new DateTime(this.getTo()).withTimeAtStartOfDay();
  }

  /**
  * @return the Rented nights calculated usign the "from" and "to" Date attributes
  */
  private Integer getRentedNights() {
    return Days.daysBetween(this.getFromAtDayStart(), this.getToAtDayStart()).getDays();
  }
}