<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link type="text/css" href="<c:url value="/css/w3.css" />"
	rel="Stylesheet" />
<link type="text/css" href="<c:url value="/css/mil.css" />"
	rel="Stylesheet" />

<script type="text/javascript"
	src="<c:url value="/js/angular.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/js/indexjs.js" />"></script>

<title><fmt:message key="project.name" /></title>
</head>
<!-- inicializa messages -->

<body class="w3-content" style="max-width:1000px">
<div ng-app="myApp" ng-controller="control" >



<!-- linha superior de login -->
<%@ include file="menusuperior.jsp"%>

	
<%@ include file="busca.jsp"%>
	
<!-- barra de navegacao esquerda e Erros modals -->
<%@ include file="sidenav.jsp"%>

<div class="w3-container content500" style="margin-top: 10px;">
<div class="content500" style="margin-left: 146px;">

<div class="w3-container" >
	<!-- os cards de produto serao adicionados dentro da div a seguir -->
	<div id="corpoprod" class="w3-row w3-border prodfont content500" data-ng-init="buscarProds('false')" >
		<!-- div que vai ser preenchida pelo javascript para exibir os produtos -->	
	</div>
</div>

<!-- rodape abaixo do corpo de produtos -->
<%@ include file="footer.jsp"%>
</div>
</div>
</div>


</body>
</html>
