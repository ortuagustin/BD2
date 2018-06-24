package ar.edu.info.unlp.bd2.etapa2.model;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Reservation {
  @Id
  private String id;
  @DBRef
  private Property property;
  @DBRef
  private User user;
  private Date from;
  private Date to;
  private ReservationStatus status;

  public Reservation(Property property, User user, Date from, Date to, ReservationStatus status) {
    super();
    this.property = property;
    this.user = user;
    this.from = from;
    this.to = to;
    this.status = status;
  }

  /**
   * @return the status
   */
  public ReservationStatus getReservationStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setReservationStatus(ReservationStatus status) {
    this.status = status;
  }

  /**
   * @return the to
   */
  public Date getTo() {
    return to;
  }

  /**
   * @param to the to to set
   */
  public void setTo(Date to) {
    this.to = to;
  }

  /**
   * @return the from
   */
  public Date getFrom() {
    return from;
  }

  /**
   * @param from the from to set
   */
  public void setFrom(Date from) {
    this.from = from;
  }

  /**
   * @return the Id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the Price
   */
  public Double getPrice() {
    return this.getNights() * property.getPrice();
  }

  /**
   * @return the number of nights booked
   */
  private Integer getNights() {
    return Days.daysBetween(new DateTime(from).withTimeAtStartOfDay(), new DateTime(to).withTimeAtStartOfDay()).getDays();
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
}