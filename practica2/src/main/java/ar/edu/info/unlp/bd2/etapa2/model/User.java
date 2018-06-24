package ar.edu.info.unlp.bd2.etapa2.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
  @Id
  private String id;
  private String username;
  private String name;
  @DBRef
  private List<Reservation> reservations;

  public User(String username, String name) {
    super();
    this.username = username;
    this.name = name;
    this.reservations = new ArrayList<Reservation>();
  }

  /**
   * Adds the reservation to the internal List of this user's reservations
   */
  public void addReservation(Reservation reservation) {
    this.reservations.add(reservation);
  }

  /**
   * @return the Id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the Username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @return the Name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the Reservations
   */
  public List<Reservation> getReservations() {
    return reservations;
  }
}