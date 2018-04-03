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
			sess.save(object);
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
	 * Actualiza un objeto persistente en la base de datos
	 * @param object el objecto a actualizar
	 */
	public <T> void update(T object) {
		Session sess = sessionFactory.getCurrentSession();
		sess.merge(object);
	}

	/**
	 * Obtiene un usuario por su username (email)
	 * @param email email del usuario a buscar
	 * @return el usuario que coincida o null si no hay ninguna coincidencia
	 */
	public User getUserByUsername(String email) {
		return (User) sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("username", email))
				.list()
				.get(0);
	}

	/**
	 * Obtiene un usuario por su id
	 * @param id el id del usuario
	 * @return el usuario que coincida o null si no hay ninguna coincidencia
	 */
	public User getUserById(Long id) {
		User user;

		try {
			user = this.sessionFactory.getCurrentSession().find(User.class, id);
		} catch (IllegalArgumentException ex) {
			user = null;
		}

		return user;
	}

	/**
	 * Obtiene una propiedad (habitación y departamento) por su nombre
	 * @param name nombre de la propiedad a obtener
	 * @return la propiedad que coincida o null si no hay ninguna coincidencia
	 */
	public Property getPropertyByName(String name) {
		return (Property) sessionFactory.getCurrentSession().createCriteria(Property.class)
				.add(Restrictions.eq("name", name))
				.list()
				.get(0);
	}

	/**
	 * Obtiene una propiedad (habitación y departamento) por su id
	 * @param id el id de la propiedad a obtener
	 * @return la propiedad que coincida o null si no hay ninguna coincidencia
	 */
	public Property getPropertyById(Long id) {
		Property property;

		try {
			property = this.sessionFactory.getCurrentSession().find(Property.class, id);
		} catch (IllegalArgumentException ex) {
			property = null;
		}

		return property;
	}
}
