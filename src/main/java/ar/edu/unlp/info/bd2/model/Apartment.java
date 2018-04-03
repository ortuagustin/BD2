package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "apartments")
public class Apartment extends Property {
  @Override
  public Double getPrice() {
    return 0.0;
  }
}