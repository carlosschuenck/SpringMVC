<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Spring MVC</a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<security:authorize access="hasRole('ROLE_ADMIN')">
						<li><a href="${spring:mvcUrl('PC#form').build()}"> <spring:message
									code="menu.cadastrar" />
						</a></li>
					</security:authorize>
					<li><a href="${spring:mvcUrl('PC#list').build()}"> <spring:message
								code="menu.consultar" />
					</a></li>
					<li>
						<a href="${spring:mvcUrl('SCC#items').build()}"> 
						   <spring:message code="menu.carrinho" arguments="${sessionScope['scopedTarget.shoppingCart'].quantidade}"/>
						</a>
					</li>
					<li>
						<a href="<c:url value='/livraria/produtos?locale=pt'/>">PT</a>
					</li>
					<li>
						<a href="<c:url value='/livraria/produtos?locale=en_US'/>">EN</a>
						
					</li>
					<li>
						
						<form:form servletRelativeAction="/logout">
							<security:authorize access="isAuthenticated()">
							<security:authentication property="principal" var="user" />
							<spring:message code="menu.boasvindas" arguments="${user.nome}" />
						</security:authorize>
							<input class="btn btn-primary" name="submit" type="submit"
								value='<spring:message code="botao.sair"/>'>
						</form:form>
					</li>
				</ul>
			</div>
			</div>
		</nav>
</body>
</html>