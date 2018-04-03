package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation_ratings")
public class ReservationRating {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
  * @return the Points
  */
  public int getPoints() {
    return 0;
  }

  /**
  * @return the Comment
  */
  public String getComment() {
    return "";
  }
}