<script>
    var textnenhum = "<fmt:message key='nenhumproduto' />";
    var finalizadosucesso = "<fmt:message key='finalizadosucesso' />";
    var cadsucesso = "<fmt:message key='cad.sucesso' />";
    var moeda = "<fmt:message key='moeda' />";
</script>
<div class="w3-container">
	<ul class="w3-navbar w3-card-8 w3-light-grey">
		<li class="w3-light-blue">
			<a href="<c:url value="/"/>"><fmt:message key="project.name" /></a>
		</li>
		
		<li id="cadmenu" class="${ empty username ? 'visivel' : 'invisivel'}">
			<a href="<c:url value="/cadastro"/>"><fmt:message key="cadastrese" /></a>
		</li>
		<li id="pedmenu" class="${ empty username ? 'invisivel' : 'visivel'}">
			<a href="<c:url value="pedidos"/>"><fmt:message key="meuspedidos" /></a>
		</li>
		
		<ul class="w3-right">
			<div id="login" class="${ empty username ? 'visivel' : 'invisivel'}">
			<form ng-submit="sendLogin()">
			<li class="liinput">
				<input ng-model="usuario.login" type="text" placeholder="<fmt:message key="nomedeusuario" />">
			</li>
			<li class="liinput">
				<input ng-model="usuario.password" type="password" placeholder="<fmt:message key="senha" />">
			</li>
			<li class="liinput">
				<button type="submit" class="w3-btn w3-white w3-border w3-border-blue w3-round"><fmt:message key="entrar" /></button>
			</li>
			</form>
			</div>
			<div id="logout" class="${empty username ? 'invisivel' : 'visivel'}">
				<li class="liinput">
					<form ng-submit="sendLogout()">
					<fmt:message key="bemvindo" /><span id="username">${username}</span>
					<button type="submit" class="w3-btn w3-white w3-border w3-border-blue w3-round"><fmt:message key="sair" /></button>
					</form>
				</li>
			</div>
		</ul>
	</ul>
</div>

