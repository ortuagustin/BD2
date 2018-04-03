package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;

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
			throw e;
		} finally {
			sess.close();
		}
	}

	/**
	 * Obtiene un usuario por su username (email)
	 * @param email email del usuario a buscar
	 * @return el usuario que coincida o null si no hay ninguna coincidencia
	 */
	public User getUserByUsername(String email) {
		Session sess = this.sessionFactory.openSession();

		try {
			return (User) sess.createCriteria(User.class)
												.add(Restrictions.eq("username", email))
												.setMaxResults(1)
												.uniqueResult();
		}
		finally {
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
		}
		finally {
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
	 * Metodo generico que obtiene un objeto persistente de cualquier clase
	 * Los metodos publicos invocan a este metodo casteando al tipo que correspoda
	 * @param id id del objeto persistido
	 * @param klass la clase del objeto que se desea buscar
	 * @return el objeto persistido
	 */
	private <T> T findById(Long id, Class<T> klass) {
		T entity;

		Session sess = this.sessionFactory.openSession();

		try {
			try {
				entity = sess.find(klass, id);
			} catch (IllegalArgumentException ex) {
				entity = null;
			}

			return entity;
		}
		finally {
			sess.close();
		}
	}
}
