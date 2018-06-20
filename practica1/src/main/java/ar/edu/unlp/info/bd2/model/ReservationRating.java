package ar.edu.unlp.info.bd2.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "reservation_ratings")
public class ReservationRating {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Integer points;
  private String comment;

  public ReservationRating(Integer points, String comment) {
    this();
    this.points = points;
    this.comment = comment;
  }

  protected ReservationRating() {
    super();
  }

  /**
  * @return the Points
  */
  public int getPoints() {
    return this.points;
  }

  /**
  * @return the Comment
  */
  public String getComment() {
    return this.comment;
  }
}