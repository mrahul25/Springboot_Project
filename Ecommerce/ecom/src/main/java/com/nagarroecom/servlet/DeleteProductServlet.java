package com.nagarroecom.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.nagarroecom.model.Product;
import com.nagarroecom.utils.HibernateUtil;

@WebServlet("/product/delete-product")
public class DeleteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// deleting product by product id
		int id = Integer.parseInt(req.getParameter("id"));
		Product product = new Product();
		product.setId(id);
		Session session = HibernateUtil.sfProduct.openSession();
		session.beginTransaction();
		session.delete(product);
		session.getTransaction().commit();
		resp.sendRedirect("/ecom/store-manager.jsp");
	}

}
