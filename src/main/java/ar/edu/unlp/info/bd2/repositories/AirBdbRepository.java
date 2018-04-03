package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import javax.persistence.criteria.CriteriaQuery;

public class AirBdbRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public User findById(Long id) {
		User user;
		try {
			user = this.sessionFactory.getCurrentSession().find(User.class, id);
		} catch (IllegalArgumentException ex) {
			user = null;
		}
		return user;
	}

	public <T> void save(T object) {
		Session sess = sessionFactory.getCurrentSession();
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
}
