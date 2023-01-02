package com.nagarroecom.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.nagarroecom.model.Product;
import com.nagarroecom.model.User;
import com.nagarroecom.utils.HibernateUtil;
import javax.servlet.http.Part;

// limiting the maximum image size
@MultipartConfig(maxFileSize = 1024 * 1024 * 1 // 1MB
		, maxRequestSize = 1024 * 1024 * 10 // 10MB
)
@WebServlet("/product/add-product")
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// getting user from session
		HttpSession httpSession = req.getSession();
		User user = (User) httpSession.getAttribute("user");
		String title = req.getParameter("title");
		String size = req.getParameter("size");
		
		// checking if title and size is entered
		if(title == "" || size == "") {
			req.setAttribute("errorMessage", "Please enter title and size!");
			req.getRequestDispatcher("/store-manager.jsp").forward(req, res);
			return;
		}
		int quantity;
		// error if quantity field is invalid
		try {
			quantity = Integer.parseInt(req.getParameter("quantity"));
		}catch (NumberFormatException e) {
			req.setAttribute("errorMessage", "Invalid quantity!");
			req.getRequestDispatcher("/store-manager.jsp").forward(req, res);
			return;
		}
		
		Part filePart = req.getPart("image");
		// setting folder path
		String appPath = req.getServletContext().getRealPath("");
		String fileName = filePart.getSubmittedFileName();
		String filePath = appPath + "Images" + File.separator + fileName;
		
		try {
			// saving the image
			FileOutputStream outputStream = new FileOutputStream(filePath);
			InputStream inputStream = filePart.getInputStream();
			byte[] data = new byte[inputStream.available()];
			inputStream.read(data);
			outputStream.write(data);
			outputStream.close();
			inputStream.close();
			
			//saving the product to the database
			Product product = new Product();
			product.setTitle(title);
			product.setQuantity(quantity);
			product.setSize(size.toUpperCase());
			product.setImage(fileName);
			List<Product> products = new ArrayList<Product>();
			// adding product to the user products list (one to many relationship)
			products.add(product);
			// setting the user for the product (many to one relationship)
			product.setUser(user);
			// setting the product for the user
			user.setProducts(products);
			Session session = HibernateUtil.sfProduct.openSession();
			session.beginTransaction();
//			session.update(user);
			session.save(product);
			session.getTransaction().commit();
			session.close();
			res.sendRedirect("/ecom/store-manager.jsp");
		}catch(FileNotFoundException e) {
			// dispatching error message if image is not found
			req.setAttribute("errorMessage", "Please select product photo!");
			req.getRequestDispatcher("/store-manager.jsp").forward(req, res);
			return;
		}catch(Exception e) {
			// dispatching error message in case of any other exception
			req.setAttribute("errorMessage", "Something went wrong! Please try again.");
			req.getRequestDispatcher("/store-manager.jsp").forward(req, res);
			return;
		}

	}
}
