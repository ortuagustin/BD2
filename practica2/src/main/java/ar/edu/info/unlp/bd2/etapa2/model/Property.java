package ar.edu.info.unlp.bd2.etapa2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Property {
  @Id
  private String id;
  private String name;
  private String description;
  private Double price;
  private Integer capacity;
  private Integer rooms;
  private City city;

  public Property(String name, String description, Double price, Integer capacity, Integer rooms, City city) {
    super();
    this.name = name;
    this.description = description;
    this.price = price;
    this.capacity = capacity;
    this.rooms = rooms;
    this.city = city;
  }

  /**
   * @return the Id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the city
   */
  public City getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(City city) {
    this.city = city;
  }

  /**
   * @return the rooms
   */
  public Integer getRooms() {
    return rooms;
  }

  /**
   * @param rooms the rooms to set
   */
  public void setRooms(Integer rooms) {
    this.rooms = rooms;
  }

  /**
   * @return the capacity
   */
  public Integer getCapacity() {
    return capacity;
  }

  /**
   * @param capacity the capacity to set
   */
  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  /**
   * @return the price
   */
  public Double getPrice() {
    return price;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
}