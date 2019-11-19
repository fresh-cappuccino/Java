/**
 * 
 */
function CustomAlert(){
	this.render = function(dialog){
		var winW = window.innerWidth;
		var winH = window.innerHeight;
		var dialogoverlay = document.getElementById('dialogoverlay');
		var dialogbox = document.getElementById('dialogbox');
		dialogoverlay.style.display = "block";
		dialogoverlay.style.height = winH+"px";
		dialogbox.style.left = (winW/2) - (550 * .5)+"px";
		dialogbox.style.top = "100px";
		dialogbox.style.display = "block";
		document.getElementById('dialogboxhead').innerHTML = "** ERRO **";
		document.getElementById('dialogboxbody').innerHTML = dialog;
		document.getElementById('dialogboxfoot').innerHTML = '<button onclick="cAlert.ok()" id="btnAlert">OK</button>';
	}
	this.ok = function(){
		document.getElementById('dialogbox').style.display = "none";
		document.getElementById('dialogoverlay').style.display = "none";
	}
}

var cAlert = new CustomAlert();

function validaSubmit(frm){
	var a = document.getElementById("codigo").value.split("");
	var b = [];
	
	for (var index = 0; index < a.length; index++){
		if (!(a[index] == "." || a[index] == " ")){
			b.push(a[index]);
		}
	}
	
	//Verifica se o número de caracteres lidos é 44. Se não, o código de barras lido não está no padrão.
	if (b.length == 44 || b.length == 47) {
		if (b.length == 47) {
			var cod = [];
			
			for (var i = 0; i < 4; i++) {
				cod.push(b[i]);
			}
			
			cod.push(b[32]);
			
			for (i = 33; i < 47; i++) {
				cod.push(b[i]);
			}
			
			for (i = 4; i < 9; i++) {
				cod.push(b[i]);
			}
			
			for (i = 10; i < 20; i++) {
				cod.push(b[i]);
			}
			
			for (i = 21; i < 31; i++) {
				cod.push(b[i]);
			}
			b = cod;
		}
		var soma = 0;
		var mult = 2;
		var numCount = 0;
		
		//Cria um vetor de caracteres c, que recebe o valor do código de barras (sem o DV).
		//Tem o objetivo de re-calcular o DV e verificar sua autenticidade.
		var c = [];
		for (i = b.length - 1; i >= 0; i--) {
			if (i != 4) {
				c.push(b[i]);
			}
		}
		
		//Recalculando o DV geral (MOD 11).
		for (i = 0; i < c.length; i++) {
			var er = /^[0-9]+$/;
			if (er.test(c[i]))
            {
                soma = soma + (mult * parseInt(c[i]));
                numCount = numCount + 1;
            }
			mult = mult + 1;
			if (mult == 10) {
				mult = 2;
			}
		}
		soma = 11 - soma%11;
		
		if (soma > 9)
		{
			soma = 0;
		}
		
		//Verifica se o DV calculado bate com o DV apresentado. Se sim, faz a decomposição do código de barras.
		//A segunda comparação, verifica se existe um caractere inválido no código.
		if (!(b[4] == (soma.toString()) && numCount == 43)){
			cAlert.render('** CODIGO INVALIDO **');
			//alert("KKKKKKKK");
			//CustomAlert().render('****');
			//bootbox.alert("Testando");
			return false;
		}
	}else {
		cAlert.render('** NUMERO DE CARACTERES INVALIDO **');
		//alert("bb");
		//bootbox.alert("Testando");
		return false;
	}
	frm.submit();
}