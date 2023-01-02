package com.nagarroecom.utils;

import java.util.List;
import org.hibernate.Session;

import com.nagarroecom.model.Product;
import com.nagarroecom.model.User;


public class ProductDao {
	
	// method to fetch all products of user by use id
	public static List<Product> getAllProduct(int id){
		Session session = HibernateUtil.sfProduct.openSession();
		User user = session.get(User.class, id);
		List<Product> allProducts = user.getProducts();
		for(Product p: allProducts) {
			System.out.println(p.getTitle());
		}
//		session.getTransaction().commit();
		session.close();
		return allProducts;
	}
	
	// method to fetch a product by its id
	public static Product getProduct(int id) {
		Session session = HibernateUtil.sfProduct.openSession();
		session.beginTransaction();
		Product product = session.get(Product.class, id);
		session.getTransaction().commit();
		session.close();
		return product;
	}

}
