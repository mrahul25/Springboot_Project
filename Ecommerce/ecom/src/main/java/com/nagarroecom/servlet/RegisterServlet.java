package com.nagarroecom.servlet;

import java.io.IOException;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import com.nagarroecom.model.User;
import com.nagarroecom.utils.HibernateUtil;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/ecom/register.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// fetching and setting user values from the form
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User();
		user.setName(name);
		user.setUsername(username);
		user.setPassword(password);
		
		// error if values are not filled by the user
		if(name == "" || username == "" || password == "") {
			response.setContentType("text/html");
			request.setAttribute("errorMessage", "Please enter all values.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		try {
			// persisting data to database
			Session session = HibernateUtil.sf.openSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			session.close();
			HttpSession httpSession = request.getSession();
			// setting user session
			httpSession.setAttribute("user", user);
			response.sendRedirect("/ecom/store-manager.jsp");
		}catch(PersistenceException e) {
			// dispatching error message
			response.setContentType("text/html");
			request.setAttribute("errorMessage", "Username already exist! Try other username.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}

}
