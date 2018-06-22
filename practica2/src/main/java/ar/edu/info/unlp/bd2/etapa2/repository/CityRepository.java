package ar.edu.info.unlp.bd2.etapa2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.info.unlp.bd2.etapa2.model.City;

public interface CityRepository extends MongoRepository<City, String> {
  Optional<City> findByName(String name);

  List<City> findByNameContainingOrderByNameAsc(String name);
}