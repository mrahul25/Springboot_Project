package com.nagarroecom.servlet;

import java.io.IOException;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.nagarroecom.model.User;
import com.nagarroecom.utils.HibernateUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("/ecom/login.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		res.setContentType("text/html");
		
		User user = null;
		try {
			// checking for user and credentials
			Session session = HibernateUtil.sf.openSession();
			session.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = session.createQuery("from User where username=:username and password=:pass");
			query.setParameter("username", username);
			query.setParameter("pass", password);
			user = (User)query.getSingleResult();
			session.getTransaction().commit();
			session.close();
			// checking for user password
			if(user.getPassword().equals(password)) {
				HttpSession httpSession = req.getSession();
				httpSession.setAttribute("user", user);
				res.sendRedirect("/ecom/store-manager.jsp");
			}else {
				req.setAttribute("errorMessage", "Invalid password!");
				req.getRequestDispatcher("/login.jsp").forward(req, res);
			}
			
		}catch (NoResultException e) {
			// dispatching error message
			req.setAttribute("errorMessage", "Invalid credentials! Please try again.");
			req.getRequestDispatcher("/login.jsp").forward(req, res);
		}
		
	}

}
