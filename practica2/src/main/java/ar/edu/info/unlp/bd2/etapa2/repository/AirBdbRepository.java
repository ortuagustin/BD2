package ar.edu.info.unlp.bd2.etapa2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.info.unlp.bd2.etapa2.model.City;

public class AirBdbRepository {
  @Autowired
  private CityRepository cityRepository;

  /**
   * Devuelve la ciudad dado el nombre
   *
   * @param name
   *
   * @return Optional<City>
   */
  public Optional<City> findCityByName(String name) {
    return this.cityRepository.findByName(name);
  }

  /**
   * Crea la nueva ciudad con el nombre indicado y la persiste en la BD
   *
   * @param name
   *
   * @return City la ciudad creada
   */
  public City createCity(String name) {
    City city = new City(name);

    return this.cityRepository.save(city);
  }

  /**
   * Obtiene las ciudades que contengan en su nombre <code>content</code>,
   * <bold>ordenadas por nomrbes</bold>
   *
   * @param content
   * @return Las ciudades que coincidan con el criterio
   */
  public List<City> getCitiesMatching(String content) {
    return this.cityRepository.findByNameContainingOrderByNameAsc(content);
  }

  /**
   * Limpia la base de datos por completo, borrando todas las colecciones
   */
  public void clearDb() {
  }
}