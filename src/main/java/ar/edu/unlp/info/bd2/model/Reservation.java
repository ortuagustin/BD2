package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
  @OneToOne(cascade = CascadeType.ALL)
  private ReservationRating rating;

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

  public Reservation() {
    super();
  }

  /**
  * @return el precio por la reserva
  */
  public Double getPrice() {
    return this.getRentedNights() * property.getPrice();
  }

  /**
  * @return Propiedad alquilada
  */
  public Property getProperty() {
    return property;
  }

  /**
   * @return Usuario que realizo la reserva
   */
  public User getUser() {
	  return user;
  }

  /**
   * @return Id de la reserva
   */
  public Long getId() {
	  return id;
  }

  /**
  * @return estado actual de la reserva
  */
  public ReservationStatus getStatus() {
    return reservationStatus;
  }

  /**
  * @return la fecha en la que la reserva empieza
  */
  public Date getFrom() {
    return dateFrom;
  }

  /**
  * @return la fecha en la que la reserva termina
  */
  public Date getTo() {
    return dateTo;
  }

  /**
  * @return la fecha+hora en la que la reserva empieza
  */
  private DateTime getFromAtDayStart() {
    return new DateTime(this.getFrom()).withTimeAtStartOfDay();
  }

  /**
  * @return la fecha+hora en la que la reserva termina
  */
  private DateTime getToAtDayStart() {
    return new DateTime(this.getTo()).withTimeAtStartOfDay();
  }

  /**
  * @return la cantidad de dias que dura la reserva
  */
  private Integer getRentedNights() {
    return Days.daysBetween(this.getFromAtDayStart(), this.getToAtDayStart()).getDays();
  }

  /**
   * @return la calificacion realizada a la reserva
   */
  public ReservationRating getReservationRating() {
    return this.rating;
  }

  /**
  * Agrega una calificacion a la reserva con los puntos y comentario indicados si el metodo canRate() evalua a true
  * @return el objeto ReservationRating creado con el puntaje y el comentario
  */
  public void rate(Integer points, String comment) throws RateException {
    if (!this.canRate())
      throw new RateException();

    this.rating = new ReservationRating(points, comment);
  }

  /**
  * @return devuelve true si se pueden agregar calificaciones a la reserva; false en caso contrario
  */
  private boolean canRate() {
    return this.getStatus() == ReservationStatus.FINISHED;
  }

  /**
   * Pasa la reserva a estado terminado (<code>ReservationStatus.FINISHED</code>)
   */
  public void finish() {
    this.reservationStatus = ReservationStatus.FINISHED;
  }
}