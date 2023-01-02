package com.nagarroecom.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.hibernate.Session;

import com.nagarroecom.model.Product;
import com.nagarroecom.model.User;
import com.nagarroecom.utils.HibernateUtil;

@MultipartConfig
@WebServlet("/product/edit-product")
public class updateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// getting user from session
		HttpSession httpSession = req.getSession();
		User user = (User) httpSession.getAttribute("user");
		int productId = Integer.parseInt(req.getParameter("id"));
		String title = req.getParameter("title");
		String size = req.getParameter("size");

		// checking if title and size is not empty
		if (title == "" || size == "") {
			req.setAttribute("errorMessage", "Please enter title and size!");
			req.getRequestDispatcher("/store-manager.jsp").forward(req, res);
			return;
		}
		int quantity;
		// error if quantity field is invalid
		try {
			quantity = Integer.parseInt(req.getParameter("quantity"));
		} catch (NumberFormatException e) {
			req.setAttribute("errorMessage", "Invalid quantity!");
			req.getRequestDispatcher("/store-manager.jsp").forward(req, res);
			return;
		}
		// initializing and setting product
		Product product = new Product();
		product.setId(productId);
		product.setTitle(title);
		product.setQuantity(quantity);
		product.setSize(size.toUpperCase());
		Part filePart = req.getPart("image");
		String fileName = filePart.getSubmittedFileName();
		if (fileName != "") {
			String appPath = req.getServletContext().getRealPath("");
			String filePath = appPath + "Images" + File.separator + fileName;
			try {
				// saving image
				FileOutputStream outputStream = new FileOutputStream(filePath);
				InputStream inputStream = filePart.getInputStream();
				byte[] data = new byte[inputStream.available()];
				inputStream.read(data);
				outputStream.write(data);
				outputStream.close();
				inputStream.close();
				product.setImage(fileName);

				// persisting data to the database
				Session session = HibernateUtil.sfProduct.openSession();
				session.beginTransaction();
				product.setUser(user);
//				user.getProducts().add(product);
				System.out.println("checkpoint0");
				session.update(product);
				System.out.println("checkpoint1");
				session.getTransaction().commit();
				System.out.println("checkpoint2");
				session.close();
				System.out.println("Product updated");
				res.sendRedirect("/ecom/store-manager.jsp");
			} catch (FileNotFoundException e) {
				// dispatching error message if image is not found
				req.setAttribute("errorMessage", "Please select product photo!");
				req.getRequestDispatcher("/edit-product.jsp").forward(req, res);
				return;
			} catch (Exception e) {
				// dispatching error message in case of any other exception
				System.out.println(e.getMessage());
				req.setAttribute("errorMessage", "Something went wrong! Please try again.");
				req.getRequestDispatcher("/edit-product.jsp").forward(req, res);
				return;
			}
		}

	}

}
