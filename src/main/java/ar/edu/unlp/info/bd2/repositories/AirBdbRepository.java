package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public class AirBdbRepository {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Persiste el objeto en la base de datos, contemplando el uso de transacciones
	 * @param object el objecto a persistir
	 */
	public <T> void save(T object) {
		Session sess = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = sess.beginTransaction();
			sess.saveOrUpdate(object);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
		} finally {
			sess.close();
		}
	}

	/**
	 * Obtiene un usuario por su username (email)
	 * @param username nombre de usuario a buscar
	 * @return el usuario que coincida o null si no hay ninguna coincidencia
	 */
	public User getUserByUsername(String username) {
		Session sess = this.sessionFactory.openSession();

		try {
			return (User) sess.createCriteria(User.class)
					.add(Restrictions.eq("username", username))
					.setMaxResults(1)
					.uniqueResult();
		} finally {
			sess.close();
		}
	}

	/**
	 * Obtiene una propiedad (habitación y departamento) por su nombre
	 * @param name nombre de la propiedad a obtener
	 * @return la propiedad que coincida o null si no hay ninguna coincidencia
	 */
	public Property getPropertyByName(String name) {
		Session sess = this.sessionFactory.openSession();

		try {
			return (Property) sess.createCriteria(Property.class)
					.add(Restrictions.eq("name", name))
					.setMaxResults(1)
					.uniqueResult();
		} finally {
			sess.close();
		}
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
		Session sess = this.sessionFactory.openSession();

		try {
			sess.delete(obj);
		} finally {
			sess.close();
		}
	}

	/**
	 * Metodo generico que obtiene un objeto persistente de cualquier clase
	 * Los metodos publicos invocan a este metodo casteando al tipo que corresponda
	 * @param id id del objeto persistido
	 * @param klass la clase del objeto que se desea buscar
	 * @return el objeto persistido
	 */
	private <T> T findById(Long id, Class<T> klass) {
		T entity = null;
		Session sess = this.sessionFactory.openSession();

		try {
			entity = sess.find(klass, id);
		} finally {
			sess.close();
		}

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
		Session session = this.sessionFactory.openSession();

		String query = "SELECT res FROM Reservation res WHERE res.property = :property"
				+ " AND res.reservationStatus != :statusCanceled AND res.reservationStatus != :statusFinished"
				+ " AND ((res.dateFrom BETWEEN :from1 AND :to1) OR (res.dateTo BETWEEN :from2 AND :to2))";

		Reservation reservation = null;
		try {
			reservation = (Reservation) session.createQuery(query).setParameter("property", property)
				.setParameter("statusCanceled", ReservationStatus.CANCELLED)
				.setParameter("statusFinished", ReservationStatus.FINISHED)
				.setParameter("from1", from)
				.setParameter("to1", to)
				.setParameter("from2", from)
				.setParameter("to2", to)
				.setMaxResults(1)
				.uniqueResult();
		} finally {
			session.close();
		}

		return reservation;
	}
}
