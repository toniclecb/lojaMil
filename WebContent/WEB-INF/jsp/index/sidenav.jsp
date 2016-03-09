

<nav class="navfont w3-sidenav w3-white w3-card-2" style="width:140px; position: absolute !important;">
  <c:forEach var="dep" items="${departamentos}">
	<a  href="javascript:angular.noop();" ng-click="tipoDepart(1,${dep.idDepartamento})"> ${dep.descricao}</a>
	<c:forEach var="cat" items="${dep.categorias}">
		<a  href="javascript:angular.noop();" ng-click="tipoDepart(2,${cat.idCategoria})"><span>&nbsp;&nbsp;</span>${cat.descricao}</a>
		<c:forEach var="sub" items="${cat.subCategorias}">
		   <a  href="javascript:angular.noop();" ng-click="tipoDepart(3,${sub.idSubCategoria})"><span>&nbsp;&nbsp;&nbsp;&nbsp;</span>${sub.descricao}</a>
		</c:forEach>	
	</c:forEach>
  </c:forEach>
</nav>


<!-- modal de erro para erros de login -->
<div id="modal01" class="w3-modal">
  <div class="w3-modal-content">
    <div class="w3-container w3-pale-red w3-leftbar w3-rightbar w3-border-red">
      <span onclick="document.getElementById('modal01').style.display='none'" class="w3-closebtn">×</span>
      <p id="modal01text"></p>
    </div>
  </div>
</div>
<div id="modal02" class="w3-modal">
  <div class="w3-modal-content">
    <div class="w3-container w3-pale-green w3-leftbar w3-rightbar w3-border-green">
      <span onclick="document.getElementById('modal02').style.display='none'" class="w3-closebtn">×</span>
      <p id="modal02text"></p>
    </div>
  </div>
</div>