<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>

<template:template titulo="Cadastrar Livros - Spring MVC">		
		<h2><spring:message code="titulo.cadastrar"/></h2>
		<div class="jumbotron">
			<spring:hasBindErrors name="produto">
				<ul>
					<c:forEach var="error" items="${errors.allErrors}">
						<li>${error.code}-${error.field}</li>
					</c:forEach>
				</ul>
			</spring:hasBindErrors>
			<form:form action="${spring:mvcUrl('salvarProduto').build()}"
				method="post" commandName="produto" enctype="multipart/form-data">
				<div class="row">
					<div class="col-sm-5">
						<label for="titulo">
							<spring:message code="label.titulo" />						
						</label>
						<form:input class="form-control" path="titulo" />
					</div>
				</div>

				<div class="row">
					<div class="col-sm-5">
						<label for="descricao">
							<spring:message code="label.descricao" />
						</label>
						<form:textarea class="form-control" path="descricao" rows="5"
							cols="20" />
					</div>
				</div>
				<div class="row">
					<div class="col-sm-2">
						<label for="numeroPaginas">
							<spring:message code="label.paginas" />
						</label>
						<form:input class="form-control" path="pagina" />
					</div>
				</div>
				<div class="row">
					<div class="col-sm-3">
						<label for="dataLancamento">
							<spring:message code="label.datalancamento" />
						</label>
						<form:input class="form-control" path="dataLancamento" type="date" />
					</div>
				</div>

				<div class="row">
					<div class="col-sm-4">
						<label for="sumario"><spring:message code="label.sumario" /></label> 
						<input class="form-control" type="file" name="sumario" />
					</div>
				</div>
				<div class="row">
					<div>
						<c:forEach items="${tipos}" var="tipoLivro" varStatus="status">
							<div class="col-sm-2">
								<label for="preco_${tipoLivro}">${tipoLivro}</label> <input
									class="form-control" type="text"
									name="precos[${status.index}].valor" id="preco_${tipoLivro}" />
								<input class="form-control" type="hidden"
									name="precos[${status.index}].tipoLivro" value="${tipoLivro}" />
							</div>
						</c:forEach>
					</div>
				</div>
				<br />
				<div class="row">
					<input class="btn btn-primary" type="submit" value="<spring:message code="botao.salvar"/>">
				</div>
			</form:form>
		</div>
</template:template>