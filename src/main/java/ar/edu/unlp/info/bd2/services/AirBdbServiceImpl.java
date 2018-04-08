package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.AirBdbRepository;

import java.util.Date;


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
	public User createUser(String username, String name) {
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
	public User getUserByUsername(String email) {
		return this.repository.getUserByUsername(email);
	}

	/**
	 * Obtiene un usuario por su id
	 * @param id el id del usuario
	 * @return el usuario que coincida o null si no hay ninguna coincidencia
	 */
	@Override
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
	public Apartment createAparment(String name, String description, double price, int capacity, int rooms,
			String cityName) {
		Apartment apartment = new Apartment(name, description, price, capacity, cityName, rooms);
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
	public PrivateRoom createRoom(String name, String description, double price, int capacity, int beds,
			String cityName) {
		PrivateRoom privateRoom = new PrivateRoom(name, description, price, capacity, cityName, beds);
		this.repository.save(privateRoom);
		return privateRoom;
	}

	/**
	 * Obtiene una propiedad (habitación y departamento) por su nombre
	 * @param name nombre de la propiedad a obtener
	 * @return la propiedad que coincida o null si no hay ninguna coincidencia
	 */
	@Override
	public Property getPropertyByName(String name) {
		return this.repository.getPropertyByName(name);
	}

	/**
	 * Obtiene una propiedad (habitación y departamento) por su id
	 * @param id el id de la propiedad a obtener
	 * @return la propiedad que coincida o null si no hay ninguna coincidencia
	 */
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
	public Reservation createReservation(long propertyId, long userId, Date from, Date to) throws ReservationException {
		Reservation reservation = null;
		if (this.isPropertyAvailable(propertyId, from, to)) {
			// Solo paso si la propiedad esta disponible
			Property property = this.getPropertyById(propertyId);
			User user = this.getUserById(userId);
			reservation = user.rent(property, from, to);

			this.repository.save(reservation);

			return reservation;
		} else {
			throw new ReservationException();
		}
	}

	/**
	 * Devuelve si una propiedad está disponible para ser reservada en un rango de fechas dado
	 * @param id id de la propiedad que se quiere consultar
	 * @param from fecha desde la cual se quiere comprobar disponibilidad
	 * @param to fecha hasta la cual se quiere comprobar disponibilidad
	 * @return <code>true</code> si la propiedad está disponible para ser reservada o <code>false</code> en caso contrario
	 */
	@Override
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
	public void cancelReservation(Long reservationId) {
		Reservation reservation = this.repository.getReservationById(reservationId);
		reservation.setStatus( ReservationStatus.CANCELLED);
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
	public ReservationRating getRatingForReservation(Long reservationId) {
		return this.getReservationById(reservationId).getReservationRating();
	}

	/**
	 * Obtiene una reserva por su id
	 * @param id id de la reserva que se busca
	 * @return la reserva que coincida con ese id o <code>null</code> en caso contrario
	 */
	@Override
	public Reservation getReservationById(Long id) {
		return this.repository.getReservationById(id);
	}


}
