package com.ctvit.flex.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
	private static Configuration config = null;
	private static SessionFactory sessionFactory = null;
	private static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	static {
		config =  new AnnotationConfiguration().configure();
		sessionFactory = config.buildSessionFactory();
	}

	public static Session getSession() {
		Session session = threadLocal.get();
		if (session == null || session.isOpen() == false) {
			session = sessionFactory.openSession();
			threadLocal.set(session);
		}
		return session;
	}

	public static void closeSession() {
		Session session = threadLocal.get();
		if (session != null && session.isOpen() == true) {
			session.close();
		}
	}
}
