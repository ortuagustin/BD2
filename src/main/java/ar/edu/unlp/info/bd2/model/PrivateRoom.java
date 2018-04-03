package ar.edu.unlp.info.bd2.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "private_rooms")
public class PrivateRoom extends Property {
  @Override
  public Double getPrice() {
    return 0.0;
  }
}