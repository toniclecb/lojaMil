/*metodo utilizado para preencher os dados de produto no html*/
function refleshProdutos(produtos){
var allhtml = "";
if (produtos == 'undefined' || produtos.length == 0 ){
	/*se nao existem produtos exibe mensagem de nenhum produto*/
	document.getElementById('corpoprod').innerHTML = "<div class='w3-container'>" +
		"<div class='w3-card-12 w3-margin cardnoprod'>" +
		"<span>"+textnenhum+"</span>" +
		"</div></div>";	
} else {
  for (x in produtos){
	var prod = produtos[x];
	if (prod.imagem)
		var img = prod.imagem;
	else
		var img = 'noimg.png';
	/* inseri o codigo relativo a cada produto na lista de produtos */
	var html = "<div class='w3-third w3-container' >" +
		"<div class='w3-card-12 w3-margin' style='width:84%'>" +
  		  "<a href='produto?prod="+prod.idProduto+"'><img src='img/"+img+"' alt='"+prod.titulo+"' style='width:100%; height:100%;'></a>" +
			  "<div class='w3-row w3-center w3-light-blue'>" +
  		    "<a class='w3-twothird' href='produto?prod="+prod.idProduto+"' style='text-decoration: none;'>"+prod.titulo+"</a>" +
        	"<a class='w3-third w3-blue precofont'> "+moeda+" "+prod.precoVenda+"</a></div>" +
    	"</div> </div>";
    allhtml = allhtml + html;
  }
  document.getElementById('corpoprod').innerHTML = allhtml;
}
}

// ANGULAR
var app = angular.module('myApp', []);

//Controller de CADASTRO
app.controller('cadcontrol', function($scope, $http, $window, $timeout) {
	$scope.caduser = {};
    $scope.sendcad = function() {
    	
    	var usuario = angular.toJson({usuario : $scope.caduser});
    	$http.post('cadastrar', usuario).then(function(response){
    		document.getElementById('modal02').style.display='block';
			document.getElementById('modal02text').innerHTML = cadsucesso;
			$timeout(function () {
				$window.location.href = "index";
		    }, 3000);

        },function(response){ //falha
	    	document.getElementById('modal01').style.display='block';
			document.getElementById('modal01text').innerHTML = response.data.errors[0].message;
        });
    };
});

//Controller de PEDIDO
app.controller('pedcontrol', function($scope, $http, $window, $timeout) {
	$scope.finaliza = function() {
		$http.get('finalizaPedido').then(function mySucces(response) {
			document.getElementById('modal02').style.display='block';
			document.getElementById('modal02text').innerHTML = finalizadosucesso;
			$timeout(function () {
				$window.location.href = "index";
		    }, 3000);
	    }, function myError(response) {
	    	document.getElementById('modal01').style.display='block';
			document.getElementById('modal01text').innerHTML = response.data.errors[0].message;
		});
	};
});


// Controller de PRODUTO
app.controller('prodcontrol', function($scope, $http, $window) {
	$scope.quant = 1;
	$scope.total = function(){
		return parseFloat($scope.quant * $scope.preco).toFixed(2);
	};
	$scope.compraProd = function() {
		var urll = "compra";
		var request = {
	            "pedidoItem" : {
	            	"produto" : {"idProduto": $scope.pid},
	            	"quantidade" : $scope.quant
	            }
	        };
		$http.post(urll, request).then(function(response){
			$window.location.href = "carrinho";
		},function(response){ // falha
    		document.getElementById('modal01').style.display='block';
    		document.getElementById('modal01text').innerHTML = response.data.errors[0].message;
        });
	};
});


// Controller GERAL
app.controller('control', function($scope, $http, $window, $location) {
	$scope.usuario = {};
	$scope.tipo = "";
	$scope.dep = "";
	
	$scope.tipoDepart = function(tipo_, idDep){
		$scope.tipo = tipo_;
		$scope.dep = idDep;
		$scope.buscarInicial();
	};
	
	$scope.busca = "";
	$scope.buscar = function() {
		var buscajson = angular.toJson({busca : $scope.busca});
		$http.post('buscar', buscajson).then(function(response){
			refleshProdutos(response.data.produtos);
		});
	};

	$scope.buscarInicial = function() {
		var urll = "buscarInicial";
		if ($scope.tipo == 1){
			urll = urll + "?departamento=" + $scope.dep;
		} else if ($scope.tipo == 2){
			urll = urll + "?categoria=" + $scope.dep;
		} else if ($scope.tipo == 3){
			urll = urll + "?subCategoria=" + $scope.dep;
		}
		$http.post(urll).then(function(response){
			refleshProdutos(response.data.produtos);
		});
	};
	
    $scope.sendLogin = function() {
   		var usuario = angular.toJson({usuario : $scope.usuario});
    	$http.post('login', usuario).then(function(response){
			document.getElementById('login').className = 'invisivel';
			document.getElementById('cadmenu').className = 'invisivel';
			document.getElementById('pedmenu').className = 'visivel';
    		document.getElementById('logout').className = 'visivel';
    		document.getElementById('username').innerHTML=response.data.nomeusuario_carrinho[0];
    		document.getElementById('carrinhosize').innerHTML=response.data.nomeusuario_carrinho[1];
    		// Se o usuario estiver na pagina de cadastro redireciona para o index
    		if ($location.absUrl().contains('cadastro'))
    			$window.location.href = "index";
        },function(response){ //falha
    		document.getElementById('modal01').style.display='block';
    		document.getElementById('modal01text').innerHTML = response.data.errors[0].message;
        });
    };
    $scope.sendLogout = function() {
        var req = {
        		 method: 'POST',
        		 url: 'logout',
        		 headers: {
        		   'Content-Type': '',
        		   'Accept': ''
        		 },
        		};

        		$http(req).then(function(response){
        			$scope.usuario = {};
    				document.getElementById('login').className = 'visivel';
    				document.getElementById('cadmenu').className = 'visivel';
    				document.getElementById('pedmenu').className = 'invisivel';
    				document.getElementById('logout').className = 'invisivel';
    				$window.location.href = "index";
        		});
        };
        
//    $scope.removeProduto = 
});