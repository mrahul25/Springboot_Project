<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="style.css" rel="stylesheet" />
</head>
<body>
	<h2 class="display-5 my-5 text-center">User Login</h2>
	<div class="d-flex justify-content-center my-5">
		<div class="d-flex justify-content-center p-3 w-50">
		
			<form action="login" method="post">
			<% if(request.getAttribute("errorMessage") !=null){ %>
			<p class="text-danger"><%= request.getAttribute("errorMessage") %></p>
			<% }%>
				<div class="username">
					<span>Username:</span> <input type="text" name="username"
						class="form-control my-2" />
				</div>
				<div>
					<span>Password:</span> <input type="text" name="password"
						class="form-control my-2" />
				</div>
				<div>
				<a href="#">Forgotten your password?</a>
				</div>
				<button class="btn w-100 btn-primary my-2">Login</button>
			</form>
		</div>
	</div>
</body>
</html>