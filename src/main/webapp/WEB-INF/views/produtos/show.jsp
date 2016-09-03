<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="template"%>

<template:template titulo="Mostrar Livro - Spring MVC">	
		<header id="layout-header">
			<div class="clearfix container">
				<a href="/" id="logo"> </a>
				<div id="header-content"></div>
			</div>
		</header>

		<div class="row">
			<div class="col-md-9">
				<h1>${produto.titulo}</h1>
				<p><spring:message code="label.titulo"/>: ${produto.titulo}</p>
				<p><spring:message code="label.paginas"/>: ${produto.pagina}</p>
				<p><spring:message code="label.descricao"/>: ${produto.descricao}</p>

				<p>
					Veja o <a href="<c:url value='/${produto.sumarioPath}'/>"
						target="_blank">sum&#225;rio</a> completo do livro!
				</p>

			</div>
			<div class="col-md-3">
				<img itemprop="image" width="180px" height="295px" src='' alt="${produto.titulo}">
			</div>
		</div>

		<div class="row">
			<div class="col-md-4">
				<form:form servletRelativeAction="/shopping" cssClass="container">
					<br />
					<div class="row">
						<input type="hidden" value="${produto.id}" name="produtoId" />
						<c:forEach items="${produto.precos}" var="preco">
							<div class="col-md-2">
								<input type="radio" name="tipoLivro" class="variant-radio"
									id="${produto.id}-${preco.tipoLivro}"
									value="${preco.tipoLivro}"
									${preco.tipoLivro.name() == 'COMBO' ? 'checked' : ''}>
								<label class="variant-label"
									for="${produto.id}-${preco.tipoLivro}">
									${preco.tipoLivro}: R$${preco.valor}</label>
							</div>
						</c:forEach>
					</div>

					<br /> <input type="submit" class="btn btn-primary"
						alt="Compre agora" title="Compre agora '${produto.titulo}'!"
						value="<spring:message code="botao.comprar"/>" />

					<!-- EXIBE A QUANTIDADE DE LIVROS ADICIONADOS AO CARRIDO DESTA MANEIRA DEVIDO A CONFIGURAÇÃO DE PROXY 
				<a href="" rel="nofollow">Seu carrinho (${sessionScope['scopedTarget.shoppingCart'].quantidade})</a>-->
					<a href="" rel="nofollow">
						<spring:message code="label.seucarrinho" arguments="${shoppingCart.quantidade}"></spring:message>
					</a>
				</form:form>
			</div>
		</div>
</template:template>