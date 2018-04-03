package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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

}
