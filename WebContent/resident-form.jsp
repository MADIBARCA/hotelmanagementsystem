

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>Hotel Management Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: black">
			<div>
				<a href="https://www.epam.net" class="navbar-brand"> Resident Management Application </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Residents</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-6">
		<div class="card">
			<div class="card-body">
				<c:if test="${resident != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${resident == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${resident != null}">
            			Edit Resident
            		</c:if>
						<c:if test="${resident == null}">
            			Add New Resident
            		</c:if>
					</h2>
				</caption>

				<c:if test="${resident != null}">
					<input type="hidden" name="id" value="<c:out value='${resident.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Resident Name</label> <input type="text"
						value="<c:out value='${resident.name}' />" class="form-control"
						name="name" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Resident Email</label> <input type="text"
						value="<c:out value='${resident.email}' />" class="form-control"
						name="email">
				</fieldset>

				<fieldset class="form-group">
					<label>Resident Country</label> <input type="text"
						value="<c:out value='${resident.country}' />" class="form-control"
						name="country">
				</fieldset>
				
				<fieldset class="form-group">
					<label>Resident Room</label> <input type="text"
						value="<c:out value='${resident.room}' />" class="form-control"
						name="room">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>