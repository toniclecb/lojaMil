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

<body class="w3-content" style="max-width:1000px">
<div ng-app="myApp" ng-controller="control" >



<!-- linha superior de login -->
<%@ include file="../index/menusuperior.jsp"%>
	
<%@ include file="../index/busca.jsp"%>
	
<!-- barra de navegacao esquerda  e Erros modals-->
<%@ include file="../index/sidenav.jsp"%>

<div class="w3-container content500" style="margin-top: 10px;">
<div class="content500" style="margin-left: 146px;">

<div class="w3-container" >
	<!-- os dados de produto serao adicionados dentro da div a seguir -->
	<div ng-controller="prodcontrol" id="corpoprod" class="w3-row w3-border prodfont content500" >
	
		<div class='w3-container showprod'>
			<div class='w3-card-12 w3-margin' style='width: 96%'>
			<c:if test="${empty noproduto}">
			<div class="w3-row">
		  		<div class="w3-col" style="width:50%">
		  			<img src='img/${empty produto.imagem ? "noimg.png" : produto.imagem}' alt='${produto.titulo}'>
				</div>
		  		<div class="w3-col proddesc">
		  		<form ng-submit="compraProd()" ng-init="pid = ${produto.idProduto}">
		  			<h2 class="w3-text-blue">${produto.titulo}</h2>
		  			<span class="w3-tiny"> <fmt:message key='cod' /> ${produto.codigo}</span>
		  			<br/><br/>
		  			<p><fmt:message key='quantidade' /> <input min="1" type="number" ng-model="quant" /> </p>
		  			<br/>
					<p ng-init="preco = ${produto.precoVenda}" id="campo1" ><fmt:message key='moeda' /> {{total()}}</p>
					<br/>
					<button type="submit" class='w3-btn w3-pale-blue w3-border w3-border-blue w3-round'>
						<fmt:message key='comprar' />
					</button>
				</form>
				</div>
			</div>
			<div class="w3-row">
		  		<p>${produto.descricao}</p>
			</div>
			</c:if>
			<c:if test="${empty produto}">
			<div class='w3-container showprod'>
					<p class="textcar"> <fmt:message key='nenhumproduto' /> </p> 
			</div>
			</c:if>
			</div>
		</div>
	
	</div>
</div>

<!-- rodape abaixo do corpo de produtos -->
<%@ include file="../index/footer.jsp"%>

</div>
</div>

	

</div>


</body>
</html>