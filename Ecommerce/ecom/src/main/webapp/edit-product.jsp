<%@page import="com.nagarroecom.utils.Size"%>
<%@page import="com.nagarroecom.model.Product"%>
<%@page import="com.nagarroecom.utils.ProductDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Product</title>
<!-- CSS only -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="style.css" rel="stylesheet" />
</head>
<body>
	<div class="my-4">
		<h2 class="display-5 text-center">Edit Product</h2>
	</div>
	<%
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = ProductDao.getProduct(id);
	%>
	<div class="d-flex justify-content-center">
		<form action="product/edit-product?id=<%=id%>" method="post"
			class="w-25 mx-3" enctype="multipart/form-data">
			<%
				if (request.getAttribute("errorMessage") != null) {
			%>
			<p class="text-danger"><%=request.getAttribute("errorMessage")%></p>
			<%
				}
			%>
			<input type="hidden" value=<%= id %> name="id" />
			<div class="mb-2">
				<label>Title</label> <input type="text" name="title"
					class="form-control" value=<%=product.getTitle()%> />
			</div>
			<div class="mb-2">
				<label>Quantity</label> <input type="text" name="quantity"
					class="form-control" value=<%=product.getQuantity()%> />
			</div>
			<div class="mb-2">
				<label>Size</label> <select class="form-control" name="size">
					<%
						for (Size size : Size.values()) {
					%>
					<option value=<%=size%> <%=product.getSize().equalsIgnoreCase(size.toString())? "selected":"" %>><%=size%></option>
					<%
						}
					%>
				</select>
			</div>
			<div class="mb-2">
				<label>Image</label> <input type="file" class="form-control"
					value=<%=product.getImage()%> name="image" />
			</div>
			<div class="my-4">
				<button type="submit" class="btn btn-primary">Update Item</button>
			</div>
		</form>
		<div
			class="w-25 mx-3 d-flex justify-content-center align-items-center">
			<img src="images/<%=product.getImage()%>" class="edit-image" />
		</div>
	</div>

</body>
</html>