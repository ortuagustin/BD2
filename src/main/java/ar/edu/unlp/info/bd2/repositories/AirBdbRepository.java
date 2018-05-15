package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class AirBdbRepository {
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Persiste el objeto en la base de datos, contemplando el uso de transacciones
	 * @param object el objecto a persistir
	 */
	public <T> void save(T object) {
		Session sess = sessionFactory.getCurrentSession();

		sess.saveOrUpdate(object);
	}

	/**
	 * Obtiene un usuario por su username (email)
	 * @param username nombre de usuario a buscar
	 * @return el usuario que coincida o null si no hay ninguna coincidencia
	 */
	public User getUserByUsername(String username) {
		Session sess = this.sessionFactory.getCurrentSession();

		return (User) sess.createCriteria(User.class)
				.add(Restrictions.eq("username", username))
				.uniqueResult();
	}

	/**
	 * Obtiene una propiedad (habitación y departamento) por su nombre
	 * @param name nombre de la propiedad a obtener
	 * @return la propiedad que coincida o null si no hay ninguna coincidencia
	 */
	public Property getPropertyByName(String name) {
		Session sess = this.sessionFactory.getCurrentSession();

		return (Property) sess.createCriteria(Property.class)
				.add(Restrictions.eq("name", name))
				.uniqueResult();
	}

	/**
	 * Obtiene un usuario por su id
	 * @param id el id del usuario
	 * @return el usuario que coincida o null si no hay ninguna coincidencia
	 */
	public User getUserById(Long id) {
		return (User) this.findById(id, User.class);
	}

	/**
	 * Obtiene una propiedad (habitación y departamento) por su id
	 * @param id el id de la propiedad a obtener
	 * @return la propiedad que coincida o null si no hay ninguna coincidencia
	 */
	public Property getPropertyById(Long id) {
		return (Property) this.findById(id, Property.class);
	}

	/**
	 * Obtiene una reserva por su id
	 * @param id id de la reserva que se busca
	 * @return la reserva que coincida con ese id o <code>null</code> en caso contrario
	 */
	public Reservation getReservationById(Long id) {
		return (Reservation) this.findById(id, Reservation.class);
	}

	/**
	 * Metodo generico que elimina un objeto de cualquier clase
	 * Los metodos publicos invocan a este metodo casteando al tipo que corresponda
	 * @param object objeto que se desea borrar
	 */
	public <T> void remove(T obj) {
		Session sess = this.sessionFactory.getCurrentSession();
		sess.delete(obj);
	}

	/**
	 * Metodo generico que obtiene un objeto persistente de cualquier clase
	 * Los metodos publicos invocan a este metodo casteando al tipo que corresponda
	 * @param id id del objeto persistido
	 * @param klass la clase del objeto que se desea buscar
	 * @return el objeto persistido
	 */
	private <T> T findById(Long id, Class<T> klass) {
		Session sess = this.sessionFactory.getCurrentSession();

		T entity = sess.find(klass, id);

		return entity;
	}

	/**
	* Obtiene una reserva a partir de su property entre determinadas fechas
	* @param property Property por la que se buscara la reserva
	* @param from fecha de inicio de reserva que se buscara
	* @param to fecha de fin de reserva que se buscara
	* @return la reserva que coincida con ese id o <code>null</code> en caso contrario
	*/
	public Reservation getReservationByProperty(Property property, Date from, Date to) {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "SELECT res FROM Reservation res WHERE res.property = :property"
				+ " AND res.reservationStatus != :statusCanceled AND res.reservationStatus != :statusFinished"
				+ " AND ((res.dateFrom BETWEEN :from1 AND :to1) OR (res.dateTo BETWEEN :from2 AND :to2))";

		Reservation reservation = (Reservation) session.createQuery(query)
			.setParameter("property", property)
			.setParameter("statusCanceled", ReservationStatus.CANCELLED)
			.setParameter("statusFinished", ReservationStatus.FINISHED)
			.setParameter("from1", from)
			.setParameter("to1", to)
			.setParameter("from2", from)
			.setParameter("to2", to)
			.uniqueResult();

		return reservation;
	}

	/**
	 * Obtiene desde la DB la ciudad con el nombre correspondiente.
	 * En caso de que no exista la crea
	 * @param cityName Nombre de la ciudad a buscar/crear
	 */
	public City getOrCreateCityByCityName(String cityName) {
		Session sess = this.sessionFactory.getCurrentSession();

		City res = (City) sess.createCriteria(City.class).add(Restrictions.eq("name", cityName)).uniqueResult();
		if (res == null) {
			// Si no existe al creo
			res = new City(cityName);
			this.save(res);
		}
		return res;
	}

	/**
	 * Obtiene de la DB las propiedades que han sido reservadas por
	 * el usuario pasado por parametro
	 * @param user Usuario a buscar
	 * @return Lista de Properties
	 */
	public List<Property> getAllPropertiesReservedByUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT property FROM Reservation res WHERE res.user = :user";
		return (List<Property>) session.createQuery(query).setParameter("user", user).list();
	}

	/**
	 * Devuelve los 3 departamentos mejor ranqueados
	 * @return Lista con un arreglo de objetos como se describe en el
	 * javadoc de la interfaz
	 */
	public List<Object[]> getApartmentTop3Ranking() {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "SELECT ap, AVG(res.rating.points) FROM Apartment ap"
		+ "INNER JOIN Reservation res"
		+ "ORDER BY AVG(res.rating.points) DESC";

		return session.createQuery(query).setMaxResults(3).list();
	}

  /**
   * Obtiene todos los usuarios que han gastado más de <code>amount</code> en reservas en la plataforma
   * @param amount
   * @return Una lista de usuarios que satisfagan la condición
   */
	public List<User> getUsersSpendingMoreThan(double amount) {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "SELECT DISTINCT user FROM Reservation WHERE price >= :amount";

		return (List<User>) session.createQuery(query)
									.setParameter("amount", amount)
									.list();
	}

	public List<User> getUsersThatReservedMoreThan1PropertyDuringASpecificYear(int year) {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "SELECT DISTINCT user FROM Reservation WHERE year(dateFrom) = :year GROUP BY user_id HAVING count(user_id) > 1";

		return (List<User>) session.createQuery(query)
									.setParameter("year", year)
									.list();

	}

  /**
   * Obtiene las ciudades que han tenido reservas entre las fechas <code>from</code> y <code>to</code>
   * @param from
   * @param to
   * @return Las ciudades que satisfagan la condición
   */
	public List<City> getCitiesThatHaveReservationsBetween(Date from, Date to) {
		Session session = this.sessionFactory.getCurrentSession();

		String query = "SELECT DISTINCT res.property.city FROM Reservation res WHERE res.dateTo BETWEEN :from AND :to";
		// tambien funca asi
		// String query = "SELECT res.property.city FROM Reservation res WHERE res.dateTo BETWEEN :from AND :to GROUP BY res.property.city";

		return (List<City>) session.createQuery(query)
									.setParameter("from", from)
									.setParameter("to", to)
									.list();
	}
}