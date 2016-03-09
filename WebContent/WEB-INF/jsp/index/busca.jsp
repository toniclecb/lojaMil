<!-- linha de pesquisa de produtos -->
<div class="w3-container w3-row w3-border buscainput" >
  <form ng-submit="buscar()">
  <ul class="navbar">
    <li style="margin-right: 10px;"><span><fmt:message key='busque' /></span>
    </li>
	<li><input ng-model="busca" type="text" placeholder="<fmt:message key="nomeproduto" />" />
	</li>
	<li><button type="submit" class="w3-btn w3-white w3-border w3-border-blue w3-round"><fmt:message key="buscar" /></button>
	</li>
	<li>
		<span>
			<!-- ng-options="x for (x, y) in ordens" -->
			<select ng-model="selectedOrdem" class="w3-select">
				<option value="1"> <fmt:message key='maiorpreco' /> </option>
				<option value="2"> <fmt:message key='menorpreco' /> </option>
				<option value="3"> <fmt:message key='alfabetico' /> </option>
			</select>
		</span>
	</li>
	<li class="w3-right">
	    <a href="javascript:window.location = 'carrinho'"><span id="carrinhosize" class="w3-badge w3-blue">${carrinhoquant}</span></a>
	</li>
	<li class="w3-right">
		<a href="javascript:window.location = 'carrinho'"><img alt="" src="img/cart.png"  style="width: 23px"></a>
	</li>
  </ul>
  </form>
  
</div>
