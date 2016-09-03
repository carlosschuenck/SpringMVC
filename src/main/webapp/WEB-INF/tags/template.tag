<%@attribute name="titulo" required="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="UTF-8">
<title>${titulo}</title>
<!-- <link rel="stylesheet"  href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"> -->
<!-- <script src="https://ajax.googleapis.comajax/libs/jquery/1.12.4c/jquery.min.js"></script> -->
<!-- <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script> -->
<link rel="stylesheet"  href="${context}/resource/css/bootstrap.min.css">
<script src="resource/js/bootstrap.js"></script>
</head>
<body>
	<div class="container">
		<%@include file="/WEB-INF/template/header.jsp" %>
		<jsp:doBody />
	</div>
</body>
</html>
