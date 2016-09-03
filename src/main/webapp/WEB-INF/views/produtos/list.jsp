<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>

<template:template titulo="Consultar Livros - Spring MVC">
		<h2><spring:message code="titulo.consultar"/></h2>
		<div class="jumbotron">
			<div>${msg}</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<td><spring:message code="label.titulo"/></td>
						<td><spring:message code="label.preco"/></td>
						<td><spring:message code="label.imagem"/></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${produtos}" var="produto">
						<tr>
							<td><a
								href="${spring:mvcUrl('PC#show').arg(0,produto.id).build()}">${produto.titulo}</a></td>
							<td><c:forEach items="${produto.precos}" var="preco">
						${preco.tipoLivro} - R$:${preco.valor} <br />
								</c:forEach></td>
							<td><a href="<c:url value='${produto.sumarioPath}'/>"
								target="_blank">[imagem]</a> <!-- <img src="${produto.sumarioPath}" class="img-rounded" width="304" height="236"></td> -->
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
</template:template>
