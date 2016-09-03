<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<template:template titulo="Carrinho de Compras - Spring MVC">	
		<h2><spring:message code="titulo.carrinho"/></h2>
		<div class="jumbotron">
			<div>${msg}</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<td><spring:message code="label.titulo"/></td>
						<td><spring:message code="label.preco"/></td>
						<td><spring:message code="label.quantidade"/></td>
						<td><spring:message code="label.total"/></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${shoppingCart.list}" var="item">
						<tr>
							<td>${item.produto.titulo} - ${item.tipoLivro}</td>
							<td>${item.preco}</td>
							<td>
								${shoppingCart.getQuantidade(item)}
							</td>
							<td>${shoppingCart.getTotal(item)}</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="3">
							<form:form action="${spring:mvcUrl('PC#checkout').build()}" method="post">
									<input type="submit" class="btn btn-primary" name="finalizarCompra"
										   value="<spring:message code="botao.finalizar"/>" id="finalizarCompra" />
							</form:form>
						</td>
						<td class="numeric-cell"><spring:message code="label.total"/>:${shoppingCart.total}</td>
						<td></td>
					</tr>
				</tfoot>
			</table>
		</div>
</template:template>