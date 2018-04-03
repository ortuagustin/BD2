package ar.edu.unlp.info.bd2.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Property {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  /**
  * @return the Id
  */
  public Long getId() {
    return id;
  }

  /**
   * @return the Name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the Price
   */
  public abstract Double getPrice();
}