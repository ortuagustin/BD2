package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.AirBdbRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public class AirBdbServiceImpl implements AirBdbService {
	private AirBdbRepository repository;

	public AirBdbServiceImpl(AirBdbRepository repository) {
		this.repository = repository;
	}

	/**
	 * Crea un usuario
	 * @param username nombre de usuario en el sistema (email)
	 * @param name nombre real del usuario
	 * @return el usuario creado
	 */
	@Override
	@Transactional
	public User createUser(String username, String name) throws RepeatedUsernameException {
		if (this.getUserByUsername(username) != null) {
			throw new RepeatedUsernameException();
		}

		User user = new User(username, name);
		this.repository.save(user);

		return user;
	}

	/**
	 * Obtiene un usuario por su username (email)
	 * @param email email del usuario a buscar
	 * @return el usuario que coincida o null si no hay ninguna coincidencia
	 */
	@Override
	@Transactional(readOnly = true)
	public User getUserByUsername(String email) {
		return this.repository.getUserByUsername(email);
	}

	/**
	 * Obtiene un usuario por su id
	 * @param id el id del usuario
	 * @return el usuario que coincida o null si no hay ninguna coincidencia
	 */
	@Override
	@Transactional(readOnly = true)
	public User getUserById(Long id) {
		return this.repository.getUserById(id);
	}

	/**
	 * Crea un departamento nuevo
	 * @param name nombre de la propiedad
	 * @param description description corta de la propiedad
	 * @param price precio por noche de la propiedad
	 * @param capacity cantidad de habitantes que la propiedad puede alojar
	 * @param rooms cantidad de habitaciones de la propiedad
	 * @param cityName nombre de la ciudad en la que está localizada la propiedad
	 * @return la propiedad creada
	 */
	@Override
	@Transactional
	public Apartment createAparment(String name, String description, double price, int capacity, int rooms, String cityName) {
		City city = this.getCityByCityName(cityName);
		Apartment apartment = new Apartment(name, description, price, capacity, city, rooms);
		this.repository.save(apartment);

		return apartment;
	}

	/**
	 * Crea una nueva habitación dentro de una propiedad
	 * @param name nombre de la propiedad
	 * @param description description corta de la propiedad
	 * @param price precio por noche de la propiedad
	 * @param capacity cantidad de habitantes que la propiedad puede alojar
	 * @param beds cantidad de camas de las cuales dispone la propiedad
	 * @param cityName nombre de la ciudad en la que está localizada la propiedad que contiene la habitación
	 * @return la habitación creada
	 */
	@Override
	@Transactional
	public PrivateRoom createRoom(String name, String description, double price, int capacity, int beds, String cityName) {
		City city = this.getCityByCityName(cityName);
		PrivateRoom privateRoom = new PrivateRoom(name, description, price, capacity, city, beds);
		this.repository.save(privateRoom);

		return privateRoom;
	}

	/**
	 * Obtiene una propiedad (habitación y departamento) por su nombre
	 * @param name nombre de la propiedad a obtener
	 * @return la propiedad que coincida o null si no hay ninguna coincidencia
	 */
	@Override
	@Transactional(readOnly = true)
	public Property getPropertyByName(String name) {
		return this.repository.getPropertyByName(name);
	}

	/**
	 * Obtiene una propiedad (habitación y departamento) por su id
	 * @param id el id de la propiedad a obtener
	 * @return la propiedad que coincida o null si no hay ninguna coincidencia
	 */
	@Transactional(readOnly = true)
	private Property getPropertyById(Long id) {
		return this.repository.getPropertyById(id);
	}

	/**
	 * Crea una nueva reserva para un usuario dado en una propiedad puntual
	 * @param propertyId id de la propiedad en la cual se quiere crear la reserva
	 * @param userId id del usuario para el cual se quiere crear la reserva
	 * @param from fecha desde la cual comienza la reserva
	 * @param to fecha en la cual termina la reserva
	 * @return la reserva creada
	 * @throws ReservationException si ya existe una reserva en ese rango de fechas
	 */
	@Override
	@Transactional
	public Reservation createReservation(long propertyId, long userId, Date from, Date to) throws ReservationException {
		if (!this.isPropertyAvailable(propertyId, from, to)) {
			throw new ReservationException();
		}

		Property property = this.getPropertyById(propertyId);
		User user = this.getUserById(userId);
		Reservation reservation = user.rent(property, from, to);
		this.repository.save(reservation);

		return reservation;
	}

	/**
	 * Devuelve si una propiedad está disponible para ser reservada en un rango de fechas dado
	 * @param id id de la propiedad que se quiere consultar
	 * @param from fecha desde la cual se quiere comprobar disponibilidad
	 * @param to fecha hasta la cual se quiere comprobar disponibilidad
	 * @return <code>true</code> si la propiedad está disponible para ser reservada o <code>false</code> en caso contrario
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean isPropertyAvailable(Long id, Date from, Date to) {
		Property property = this.repository.getPropertyById(id);
		Reservation reservation = this.repository.getReservationByProperty(property, from, to);

		return reservation == null;
	}

	/**
	 * Cancela una reserva creada
	 * @param reservationId id de la reserva a cancelar
	 */
	@Override
	@Transactional
	public void cancelReservation(Long reservationId) {
		Reservation reservation = this.repository.getReservationById(reservationId);
		reservation.setStatus(ReservationStatus.CANCELLED);

		this.repository.save(reservation);
	}

	/**
	 * Crea una evaluación para una reserva dada
	 * @param reservationId id de la reserva a evaluar
	 * @param points puntaje asignado en la evaluación
	 * @param comment comentarios adicionales a la evaluación
	 * @throws RateException si la reserva no fue completada exitosamente (es decir, no esta en estado
	 * <code>ReservationStatus.FINISHED</code>
	 */
	@Override
	public void rateReservation(Long reservationId, int points, String comment) throws RateException {
		Reservation reservation = this.getReservationById(reservationId);

		reservation.rate(points, comment);
		this.repository.save(reservation);
	}

	/**
	 * Pasa una reserva a estado terminado (<code>ReservationStatus.FINISHED</code>)
	 * @param id
	 */
	@Override
	@Transactional
	public void finishReservation(Long id) {
		Reservation reservation = this.getReservationById(id);

		reservation.finish();
		this.repository.save(reservation);
	}

	/**
	 * Obtiene la evaluación de una reserva dada
	 * @param reservationId id de la reserva
	 * @return la evaluación que concida para esa propiedad
	 */
	@Override
	@Transactional(readOnly = true)
	public ReservationRating getRatingForReservation(Long reservationId) {
		return this.getReservationById(reservationId).getReservationRating();
	}

	/**
	 * Obtiene una reserva por su id
	 * @param id id de la reserva que se busca
	 * @return la reserva que coincida con ese id o <code>null</code> en caso contrario
	 */
	@Override
	@Transactional(readOnly = true)
	public Reservation getReservationById(Long id) {
		return this.repository.getReservationById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Property> getAllPropertiesReservedByUser(String userEmail) {
		User user = this.repository.getUserByUsername(userEmail);
		return this.repository.getAllPropertiesReservedByUser(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getUsersSpendingMoreThan(double amount) {
		return this.repository.getUsersSpendingMoreThan(amount);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getApartmentTop3Ranking() {
		return this.repository.getApartmentTop3Ranking();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getUsersThatReservedMoreThan1PropertyDuringASpecificYear(int year) {
		return this.repository.getUsersThatReservedMoreThan1PropertyDuringASpecificYear(year);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Property> getPropertiesThatHaveBeenReservedByMoreThanOneUserWithCapacityMoreThan(int capacity) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Reservation> getReservationsInCitiesForUser(String username, String... cities) {
		return this.repository.getReservationsInCitiesForUser(username, cities);
	}

	@Override
	@Transactional(readOnly = true)
	public List<City> getCitiesThatHaveReservationsBetween(Date from, Date to) {
		return this.repository.getCitiesThatHaveReservationsBetween(from, to);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getUsersThatReservedOnlyInCities(String... cities) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Reservation getMostExpensivePrivateRoomReservation() {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getHotmailUsersWithAllTheirReservationsFinished() {
		return this.repository.getHotmailUsersWithAllTheirReservationsFinished();
	}

	@Override
	@Transactional(readOnly = true)
	public Double getTotalRevenueForFinishedReservationsDuringYear(int year) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getMatchingUsersThatOnlyHaveReservationsInCities(String usernamePart, String... cities) {
		return null;
	}

	private City getCityByCityName(String cityName) {
		return this.repository.getOrCreateCityByCityName(cityName);
	}
}
