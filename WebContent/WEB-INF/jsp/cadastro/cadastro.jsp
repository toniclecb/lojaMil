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
	
<!-- barra de navegacao esquerda -->
<%@ include file="../index/sidenav.jsp"%>

<div class="w3-container content500" style="margin-top: 10px;">
<div class="content500" style="margin-left: 146px;">

<div class="w3-container" ng-controller="cadcontrol">
	<!-- os cards de produto serao adicionados dentro da div a seguir -->
	<div id="corpoprod" class="w3-row w3-border cadfont content500" >
		<div class='w3-container showprod'>
			<!-- FORM de cadastro -->
			<form name="cadform" ng-submit="sendcad()">
			<div class='w3-card-12 w3-margin' style='width: 96%; padding: 10px;'>
			<h2><fmt:message key="cadastro"/></h2>
			<h4><fmt:message key="cad.preencha"/></h4>
			<div class="w3-row marginbot">
  				<div class="w3-container w3-quarter">
    				<label><fmt:message key="cad.login" /></label>
  				</div>
  				<div class="w3-container w3-quarter">
    				<input type="text" ng-model="caduser.login" 
    				ng-minlength="3" ng-maxlength="20" required></input>
  				</div>
  				<div class="w3-container w3-half">
  					<span><fmt:message key="entre3" /></span>
  				</div>
			</div>
			<div class="w3-row marginbot">
  				<div class="w3-container w3-quarter">
    				<label><fmt:message key="cad.password" /></label>
  				</div>
  				<div class="w3-container w3-quarter">
    				<input type="password" ng-model="caduser.password"  
    				ng-minlength="6" ng-maxlength="20" required></input>
  				</div>
  				<div class="w3-container w3-half">
  					<span><fmt:message key="entre620" /></span>
  				</div>
			</div>
			
			<div class="w3-row marginbot">
  				<div class="w3-container w3-quarter">
    				<label><fmt:message key="cad.nome" /></label>
  				</div>
  				<div class="w3-container w3-quarter">
    				<input type="text" ng-model="caduser.nome"  
    				ng-minlength="3" ng-maxlength="80" required></input>
  				</div>
  				<div class="w3-container w3-half">
  					<span><fmt:message key="entre3" /></span>
  				</div>
			</div>
			
			<div class="w3-row marginbot">
  				<div class="w3-container w3-quarter">
    				<label><fmt:message key="cad.email" /></label>
  				</div>
  				<div class="w3-container w3-quarter">
    				<input type="email" ng-model="caduser.email"  
    				ng-minlength="3" ng-maxlength="20" required></input>
  				</div>
  				<div class="w3-container w3-half">
  					<span><fmt:message key="entre3" /></span>
  				</div>
			</div>
			
			</div>
			
			<div class="w3-col">
					<button ng-disabled="cadform.$invalid" type="submit" class='w3-btn w3-right w3-margin-right w3-pale-blue w3-border w3-border-blue w3-round'>
						<fmt:message key='cadastrar' />
					<button type="button" onclick="window.location='index'" class='w3-btn w3-right w3-margin-right w3-pale-red w3-border w3-border-red w3-round'>
						<fmt:message key='cancelar' />
			</div>
			</form>
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