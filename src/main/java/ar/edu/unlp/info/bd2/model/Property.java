package ar.edu.unlp.info.bd2.model;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PROPERTY_TYPE")
public abstract class Property {
  @Id 
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name, description;
  @ManyToOne
  private City city;
	private Double price;
	private Integer capacity;

	public Property(String name, String description, Double price, Integer capacity, City city) {
		this();
    this.name = name;
		this.description = description;
		this.price = price;
		this.capacity = capacity;
		this.city = city;
  }

  protected Property() {
    super();
  }

  /**
  * @return the Id
  */
  public Long getId() {
    return this.id;
  }

  /**
   * @return the Name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return the Price
   */
  public Double getPrice() {
    return this.price;
  };

  public String getDescription() {
    return this.description;
  };

  public City getCity() {
    return this.city;
  };

  public Integer getCapacity() {
    return this.capacity;
  };

  @Override
  public boolean equals(Object other) {
    if (this == other)
      return true;

    if (!(other instanceof Property))
      return false;

    final Property property = (Property) other;

    return this.getId().equals(property.getId());
  }
}