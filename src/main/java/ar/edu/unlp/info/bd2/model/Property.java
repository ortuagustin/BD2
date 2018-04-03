package ar.edu.unlp.info.bd2.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Property {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
	private String name, description, cityName;
	private Double price;
	private Integer capacity;

	public Property(String name, String description, Double price, Integer capacity, String cityName) {
		this();
    this.name = name;
		this.description = description;
		this.price = price;
		this.capacity = capacity;
		this.cityName = cityName;
  }

  protected Property() {
    super();
  }

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
  public Double getPrice() {
    return price;
  };
}