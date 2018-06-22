package ar.edu.info.unlp.bd2.etapa2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ar.edu.info.unlp.bd2.etapa2.config.MongoConfiguration;
import ar.edu.info.unlp.bd2.etapa2.model.*;

public class AirBdbRepository {
  @Autowired
  private CityRepository cityRepository;
  private MongoTemplate mongoTemplate;

  public AirBdbRepository() {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
    this.mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");
  }

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
   * Devuelve la ciudad dado el nombre
   *
   * @param name
   *
   * @return Optional<City>
   */
  public Optional<User> findUserByUsername(String username) {
    return Optional.ofNullable(this.mongoTemplate.findOne(new Query(Criteria.where("username").is(username)), User.class));
  }

  /**
   * Crea un nuevo usuario con el username y nombre indicados y lo persiste en la BD
   *
   * @param username
   * @param name
   *
   * @return User el usuario creado
   */
  public User createUser(String username, String name) {
    User user = new User(username, name);
    this.mongoTemplate.insert(user);

    return user;
  }

  /**
   * Limpia la base de datos por completo, borrando todas las colecciones
   */
  public void clearDb() {
    this.mongoTemplate.getCollectionNames().forEach(
        (each) -> this.mongoTemplate.dropCollection(each)
    );
  }
}