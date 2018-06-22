package ar.edu.info.unlp.bd2.etapa2.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
  @Id
  private String id;
  private String username;
  private String name;

  public User(String username, String name) {
    super();
    this.username = username;
    this.name = name;
  }

  private User() {
    super();
  }

  /**
   * @return the Id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @return the Username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * @return the Name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return the Reservations
   */
  // TODO implementar
  public List<Reservation> getReservations() {
    return null;
  }
}