package ar.edu.info.unlp.bd2.etapa2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class City {
  @Id
  private String id;
  private String name;

  public City(String name) {
    super();
    this.name = name;
  }

  private City() {

  }

  /**
   * @return the Id
   */
  public String getId() {
    return this.id;
  }

  /**
   * @return the Name
   */
  public String getName() {
    return this.name;
  }
}