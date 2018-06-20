package ar.edu.info.unlp.bd2.etapa2.service.impl;

import ar.edu.info.unlp.bd2.etapa2.model.*;
import ar.edu.info.unlp.bd2.etapa2.repository.AirBdbRepository;
import ar.edu.info.unlp.bd2.etapa2.service.AirBdbService;
import ar.edu.info.unlp.bd2.etapa2.utils.ReservationCount;

import java.util.Date;
import java.util.List;

public class AirBdbServiceImpl implements AirBdbService {
  private AirBdbRepository repository;

  public AirBdbServiceImpl(AirBdbRepository repository) {
    super();
    this.repository = repository;
  }

  /**
   * @return the repository
   */
  public AirBdbRepository getRepository() {
    return repository;
  }

  /**
   * Crea un usuario
   *
   * @param username nombre de usuario en el sistema (email)
   * @param name     nombre real del usuario
   * @return el usuario creado
   */
  public User createUser(String username, String name) throws RepeatedUsernameException {
    return null;
  }

  /**
   * Obtiene un usuario por su username (email)
   *
   * @param email email del usuario a buscar
   * @return el usuario que coincida o null si no hay ninguna coincidencia
   */
  public User getUserByUsername(String email) {
    return null;
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
  public Property createProperty(String name, String description, double price, int capacity, int rooms,
      String cityName) {
    return null;
  }

  /**
   * Limpia la base de datos por completo, borrando todas las colecciones
   */
  public void clearDb() {

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
  public Reservation createReservation(String propertyId, String userId, Date from, Date to,
      ReservationStatus initialStatus) throws ReservationException {
    return null;
  }

  /**
   * Obtiene un usuario por su id
   *
   * @param id el id del usuario
   * @return el usuario que coincida o null si no hay ninguna coincidencia
   */
  public User getUserById(String id) {
    return null;
  }

  /**
   * Obtiene una ciudad por su nombre
   *
   * @param name
   * @return la ciudad que cumpla con el criterio o <code>null</code> de no
   *         existir ninguna
   */
  public City getCityByName(String name) {
    return null;
  }

  /**
   * Obtiene todas las reservas de una propiedad dada
   *
   * @param propertyId id de la propiedad
   * @return las reservas que cumplan con el criterio
   */
  public List<Reservation> getReservationsForProperty(String propertyId) {
    return null;
  }

  /**
   * Obtiene las ciudades que contengan en su nombre <code>content</code>,
   * <bold>ordenadas por nomrbes</bold>
   *
   * @param content
   * @return Las ciudades que coincidan con el criterio
   */
  public List<City> getCitiesMatching(String content) {
    return null;
  }

  /**
   * Registra una ciudad con nombre <code>name</code> si no está registrada aún
   *
   * @param name
   */
  public City registerCity(String name) {
    return null;
  }

  /**
   * Lista todas las ciudades registradas en la aplicación
   *
   * @return
   */
  public List<City> getAllCities() {
    return null;
  }

  /**
   * Obtiene las cantidad de reservas que hay en el sistema en cada estado en
   * particular
   *
   * @return
   */
  public List<ReservationCount> getReservationCountByStatus() {
    return null;
  }
}
