<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%-- <%@ taglib uri="http://www.casadocodigo.com.br/tags/form" prefix="customForm" %> --%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login - Spring MVC</title>
<!-- <link rel="stylesheet"  href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"> -->
<!-- <script src="https://ajax.googleapis.comajax/libs/jquery/1.12.4c/jquery.min.js"></script> -->
<!-- <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> -->
<link rel="stylesheet"  href="resource/css/bootstrap.min.css">
<script src="resource/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="jumbotron">
<%-- 			<spring:hasBindErrors name="produto"> --%>
<!-- 				<ul> -->
<%-- 					<c:forEach var="error" items="${errors.allErrors}"> --%>
<%-- 						<li>${error.code}-${error.field}</li> --%>
<%-- 					</c:forEach> --%>
<!-- 				</ul> -->
<%-- 			</spring:hasBindErrors> --%>
			<form:form servletRelativeAction="/login">
				<div class="row">
					<div class="col-sm-5">
						<label for="login">User:</label>
						<input type="text" class="form-control" name="username" />
					</div>
				</div>

				<div class="row">
					<div class="col-sm-5">
						<label for="password">Password:</label>
						<input type="password" class="form-control" name="password"/>
					</div>
				</div>
				<br />
				<div class="row">
					<input class="btn btn-primary" name="submit" type="submit" value="Entrar">
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>