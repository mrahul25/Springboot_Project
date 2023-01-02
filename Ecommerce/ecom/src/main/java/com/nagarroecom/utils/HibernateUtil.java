package com.nagarroecom.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.nagarroecom.model.Product;
import com.nagarroecom.model.User;

public class HibernateUtil {
	// user session factory
	public static SessionFactory sf = new Configuration().configure().addAnnotatedClass(User.class).buildSessionFactory();
	// product session factory
	public static SessionFactory sfProduct = new Configuration().configure().addAnnotatedClass(Product.class).buildSessionFactory();

}
