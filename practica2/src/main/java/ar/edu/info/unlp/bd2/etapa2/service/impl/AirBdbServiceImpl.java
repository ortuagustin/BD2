package ar.edu.info.unlp.bd2.etapa2.service.impl;

import ar.edu.info.unlp.bd2.etapa2.model.*;
import ar.edu.info.unlp.bd2.etapa2.repository.AirBdbRepository;
import ar.edu.info.unlp.bd2.etapa2.service.AirBdbService;
import ar.edu.info.unlp.bd2.etapa2.utils.ReservationCount;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

public class AirBdbServiceImpl implements AirBdbService {
  private AirBdbRepository repository;

  public AirBdbServiceImpl(AirBdbRepository repository) {
    super();
    this.repository = repository;
  }

  /**
   * Crea un usuario
   *
   * @param username nombre de usuario en el sistema (email)
   * @param name     nombre real del usuario
   * @return el usuario creado
   */
  public User createUser(String username, String name) throws RepeatedUsernameException {
    if (this.repository.findUserByUsername(username).isPresent()) {
      throw new RepeatedUsernameException(MessageFormat.format("Username {0} already exists", username));
    }

    return this.repository.createUser(username, name);
  }

  /**
   * Obtiene un usuario por su username (email)
   *
   * @param email email del usuario a buscar
   * @return el usuario que coincida o null si no hay ninguna coincidencia
   */
  public User getUserByUsername(String email) {
    return this.repository.findUserByUsername(email).orElse(null);
  }

  /**
   * Crea una nueva propiedad, incluyendo la creación lazy de la <code>City</code>
   * con nombre <code>name</code>
   *
   * @param name        nombre de la propiedad
   * @param description description corta de la propiedad
   * @param price       precio por noche de la propiedad
   * @param capacity    cantidad de habitantes que la propiedad puede alojar
   * @param rooms       cantidad de habitaciones de la propiedad
   * @param cityName    nombre de la ciudad en que la propiedad estará localizada
   * @return la propiedad creada
   */
  public Property createProperty(String name, String description, double price, int capacity, int rooms, String cityName) {
    City city = this.findOrCreateCity(cityName);

    return this.repository.createProperty(name, description, price, capacity, rooms, city);
  }

  /**
   * Limpia la base de datos por completo, borrando todas las colecciones
   */
  public void clearDb() {
    this.repository.clearDb();
  }

  /**
   * Crea una nueva reserva para un usuario dado en una propiedad puntual
   *
   * @param propertyId    id de la propiedad en la cual se quiere crear la reserva
   * @param userId        id del usuario para el cual se quiere crear la reserva
   * @param from          fecha desde la cual comienza la reserva
   * @param to            fecha en la cual termina la reserva
   * @param initialStatus estado inicial de la reserva
   * @return la reserva creada
   * @throws ReservationException si ya existe una reserva en ese rango de fechas
   */
  public Reservation createReservation(String propertyId, String userId, Date from, Date to, ReservationStatus initialStatus) throws ReservationException {
    if (! this.isPropertyAvaiable(propertyId, from, to)) {
      throw new ReservationException("Property is already reserved in those dates");
    }

    Property property = this.getPropertyById(propertyId);
    User user = this.getUserById(userId);

    return this.repository.createReservation(property, user, from, to, initialStatus);
  }

  private Boolean isPropertyAvaiable(String propertyId, Date from, Date to) {
    return this.repository.getReservationsForProperty(propertyId, from, to).isEmpty();
  }

/**
   * Obtiene un usuario por su id
   *
   * @param id el id del usuario
   * @return el usuario que coincida o null si no hay ninguna coincidencia
   */
  public User getUserById(String id) {
    return this.repository.findUserById(id).orElse(null);
  }

  /**
   * Obtiene una ciudad por su nombre
   *
   * @param name
   * @return la ciudad que cumpla con el criterio o <code>null</code> de no
   *         existir ninguna
   */
  public City getCityByName(String name) {
    return this.repository.findCityByName(name).orElse(null);
  }

  /**
   * Obtiene todas las reservas de una propiedad dada
   *
   * @param propertyId id de la propiedad
   * @return las reservas que cumplan con el criterio
   */
  public List<Reservation> getReservationsForProperty(String propertyId) {
    return this.repository.getReservationsForProperty(propertyId);
  }

  /**
   * Obtiene las ciudades que contengan en su nombre <code>content</code>,
   * <bold>ordenadas por nomrbes</bold>
   *
   * @param content
   * @return Las ciudades que coincidan con el criterio
   */
  public List<City> getCitiesMatching(String content) {
    return this.repository.getCitiesMatching(content);
  }

  /**
   * Registra una ciudad con nombre <code>name</code> si no está registrada aún
   *
   * @param name
   */
  public City registerCity(String name) {
    return this.findOrCreateCity(name);
  }

  /**
   * Lista todas las ciudades registradas en la aplicación
   *
   * @return List<City>
   */
  public List<City> getAllCities() {
    return this.repository.getAllCities();
  }

  /**
   * Obtiene las cantidad de reservas que hay en el sistema en cada estado en
   * particular
   *
   * @return List<ReservationCount>
   */
  public List<ReservationCount> getReservationCountByStatus() {
    return this.repository.getReservationCountByStatus();
  }

  /**
   * Crea una ciudad con nombre <code>name</code> si no está registrada aún
   *
   * @param name
   */
  private City findOrCreateCity(String name) {
	  return this.repository.findCityByName(name).orElseGet(() -> this.repository.createCity(name));
  }

  /**
   * Obtiene una propiedad por su Id
   *
   * @param propertyId
   */
  private Property getPropertyById(String propertyId) {
    return this.repository.findPropertyById(propertyId);
  }
}
