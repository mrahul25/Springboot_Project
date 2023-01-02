<%@page import="com.nagarroecom.model.User"%>
<%@page import="com.nagarroecom.utils.Size"%>
<%@page import="com.nagarroecom.model.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.nagarroecom.utils.ProductDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<!-- CSS only -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css">
<link href="style.css" rel="stylesheet" />
</head>
<body>
<%
HttpSession httpSession = request.getSession();
User user = (User) httpSession.getAttribute("user");
%>
		<h2 class="display-5 text-center my-2">Store Manager</h2>
	<p style="float:right" class="mx-3"><a href="logout">Logout</a>&nbsp;&nbsp;(<%= user.getName() %>)</p>


	<div class="d-flex justify-content-center">
		<div class="w-25 my-4">
			<form action="product/add-product" method="post"
				enctype="multipart/form-data">
				<%
					if (request.getAttribute("errorMessage") != null) {
				%>
				<p class="text-danger"><%=request.getAttribute("errorMessage")%></p>
				<%
					}
				%>
				<div class="mb-2">
					<label>Title</label> <input type="text" name="title"
						class="form-control" />
				</div>
				<div class="mb-2">
					<label>Quantity</label> <input type="text" name="quantity"
						class="form-control" />
				</div>
				<div class="mb-2">
					<label>Size</label> <select class="form-control" name="size">
						<%
							for (Size size : Size.values()) {
						%>
						<option value=<%=size%>><%=size%></option>
						<%
							}
						%>
					</select>
				</div>
				<div class="mb-2">
					<label>Image</label> <input type="file" name="image"
						class="form-control" />
				</div>
				<div class="mb-2">
					<button type="submit" class="btn btn-primary my-4">Add
						Item</button>

				</div>
			</form>
		</div>
	</div>
	<div class="d-flex justify-content-center">
		<%
			int count = 1;
			List<Product> products = ProductDao.getAllProduct(user.getId());
			if (products.size() < 1) {
		%>
		<div>
			<h2>Store is Empty!</h2>
		</div>
		<%
			} else {
		%>
		<table class="table table-bordered px-5 w-75">
			<tr>
				<td>S. No.</td>
				<td>Title</td>
				<td>Quantity</td>
				<td>Size</td>
				<td>Image</td>
				<td>Actions</td>
			</tr>
			<%
				for (Product product : products) {
			%>

			<tr>
				<td><%=count%></td>
				<%
					count++;
				%>
				<td><%=product.getTitle()%></td>
				<td><%=product.getQuantity()%></td>
				<td><%=product.getSize()%></td>
				<td><img src="images/<%=product.getImage()%>" class="image" /></td>
				<td class="d-flex justify-content-center"><a
					href="edit-product.jsp?id=<%=product.getId()%>" class="mx-3"><i
						class="bi bi-pencil-fill"></i></a> <a
					href="product/delete-product?id=<%=product.getId()%>" class="mx-3"><i
						class="bi bi-trash-fill"></i></a></td>
			</tr>
			<%
				}
				}
			%>
		</table>

	</div>
</body>
</html>