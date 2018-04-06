package ar.edu.unlp.info.bd2.model;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String username;
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private Set<Reservation> reservations = new HashSet<Reservation>();

  protected User() {
    super();
  }

  public User(String username, String name) {
    this();
    this.username = username;
    this.name = name;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return this.id;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return The Reservations that the User has made
   */
  public Set<Reservation> getReservations() {
    return this.reservations;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other)
      return true;

    if (!(other instanceof User))
      return false;

    final User user = (User) other;

    return this.getUsername().equals(user.getUsername());
  }

  public Reservation rent(Property property, Date from, Date to) {
    Reservation reservation = new Reservation(property, this, from, to);
    this.reservations.add(reservation);

    return reservation;
  }
}