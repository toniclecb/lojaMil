<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<body class="w3-content" style="max-width:1000px">
<div ng-app="myApp" ng-controller="control" >

<script type="text/javascript">
var dataLocale = function(data, id){
	document.getElementById('p_'+id).innerHTML = new Date(data).toLocaleDateString();
};
</script>

<!-- linha superior de login -->
<%@ include file="../index/menusuperior.jsp"%>
	
<%@ include file="../index/busca.jsp"%>
	
<!-- barra de navegacao esquerda  e Erros modals-->
<%@ include file="../index/sidenav.jsp"%>

<div class="w3-container content500" style="margin-top: 10px;">
<div class="content500" style="margin-left: 146px;">

<div class="w3-container" >
	<!-- os dados de produto serao adicionados dentro da div a seguir -->
	<div  id="corpoprod" class="w3-row w3-border prodfont content500" >
	
	<c:if test="${pedidoquant > 0}">
		<div class='w3-container showprod'>
			<div class='w3-card-12 w3-margin' style='width: 96%'>
       		<ul class="w3-ul w3-card-4" >
       		<h2><fmt:message key="pedidostotal" /> ${pedidoquant} </h2>
			<c:forEach items="${pedidos}" var="ped">
			  <li>
			    <%-- <span>${ped.data}</span> --%>
			    <span id='p_${ped.idPedido}'>
			    <!-- Transforma a data do servidor para a data do locale do usuario -->
			    <script type="text/javascript">
			    	var d = ${ped.data.time};
			    	var i = '${ped.idPedido}';
			    	dataLocale(d,i);
			    </script>
			    </span>
			  	<span class="w3-right w3-margin-right"> ${ped.valorTotal} </span>
			  	<span class="w3-right w3-margin-right"> <fmt:message key="valor.total" /> </span>
			  	<span class="w3-right mil-margin-right"> <fmt:message key="itens" /> ${fn:length(ped.pedidoItems)} </span>
			  </li>
			</c:forEach>
			</ul>
			</div>
		</div>
	</c:if>
	<c:if test="${pedidoquant <= 0}">
		<div class='w3-container showprod'>
			<div class='w3-card-12 w3-margin' style='width: 96%'>
				<p class="textcar"> <fmt:message key='nenhumpedido' /> </p> 
			</div>
		</div>
	</c:if>
	</div>
</div>

<!-- rodape abaixo do corpo de produtos -->
<%@ include file="../index/footer.jsp"%>

</div>
</div>
</div>


</body>
</html>