package ar.edu.info.unlp.bd2.etapa2.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ar.edu.info.unlp.bd2.etapa2.model.*;

public class AirBdbRepository {
  @Autowired
  private CityRepository cityRepository;
  @Autowired
  private MongoTemplate mongoTemplate;

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
   * Obtiene un usuario por su id
   *
   * @param id el id del usuario
   *
   * @return Optional<User>
   */
  public Optional<User> findUserById(String userId) {
    User user = this.mongoTemplate.findOne(new Query(Criteria.where("_id").is(userId)), User.class);
    this.loadReservationsForUser(user);

    return Optional.ofNullable(user);
  }

  /**
   * Obtiene un usuario por su username
   *
   * @param name
   *
   * @return Optional<User>
   */
  public Optional<User> findUserByUsername(String username) {
    Optional<User> user =
      Optional.ofNullable(this.mongoTemplate.findOne(new Query(Criteria.where("username").is(username)), User.class));

    if (user.isPresent()) {
      this.loadReservationsForUser(user.get());
    }

    return user;
  }

  private void loadReservationsForUser(User user) {
    this.getReservationsForUser(user.getId()).forEach((reservation) ->
      user.addReservation(reservation)
    );
  }

  public Property createProperty(String name, String description, Double price, Integer capacity, Integer rooms, City city) {
    Property property = new Property(name, description, price, capacity, rooms, city);
    this.mongoTemplate.insert(property);

    return property;
  }

  public List<Reservation> getReservationsForUser(String userId) {
    return this.mongoTemplate.find(new Query(Criteria.where("user.$id").is(new ObjectId(userId))), Reservation.class);
  }

  public List<Reservation> getReservationsForProperty(String propertyId) {
    return this.mongoTemplate.find(new Query(Criteria.where("property.$id").is(new ObjectId(propertyId))), Reservation.class);
  }

  public List<Reservation> getReservationsForProperty(String propertyId, Date from, Date to) {
    Criteria query = Criteria.where("property.$id").is(new ObjectId(propertyId))
      .and("from").lte(to)
      .and("to").gte(from);

    return this.mongoTemplate.find(new Query(query), Reservation.class);
  }

  /**
   * Crea una nueva reserva para un usuario dado en una propiedad puntual
   *
   * @param property        la propiedad en la cual se quiere crear la reserva
   * @param user            usuario para el cual se quiere crear la reserva
   * @param from            fecha desde la cual comienza la reserva
   * @param to              fecha en la cual termina la reserva
   * @param initialStatus   estado inicial de la reserva
   *
   * @return Reservation
   */
  public Reservation createReservation(Property property, User user, Date from, Date to, ReservationStatus initialStatus) {
    Reservation reservation = new Reservation(property, user, from, to, initialStatus);
    this.mongoTemplate.insert(reservation);

    return reservation;
  }

  public Property findPropertyById(String propertyId) {
    return this.mongoTemplate.findOne(new Query(Criteria.where("_id").is(propertyId)), Property.class);
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