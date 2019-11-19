<%@ page import="com.calculos.Calcular" language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Decomp. Código de Barras</title>
	<link rel="stylesheet" type="text/css" href="css/index.css"/>
	<script type="text/javascript" src="js/index.js"></script>
	<link rel="Icone Codigo de Barras" href="img/icon_barcode.ico"/>
	
	<script type="text/javascript">
	var _userway_config = {
	account: 'a9kaaJFEQW'
	};
	</script>
	<script type="text/javascript" src="https://cdn.userway.org/widget.js"></script>
</head>
<body>
	<div id="body">
		<div align="center" class="container"><br/><br/>
			<table>
				<tr>
					<td>
						<img class="image" src="https://i.imgur.com/Wff2BCy.gif"/>
						<img class="image" src="https://i.imgur.com/Wff2BCy.gif"/>
					</td>
				</tr>
				<tr>
					<td>
						<form id="form" action="calculo.jsp" onsubmit="validaSubmit(this); return false;"><br>
							<input type="text" name="codigo" size="90%" placeholder="Leia seu Código de Barras ou digite o IPTE" id="codigo" required/><br/>
							<br/><input type="reset" name="btnLimpar" value="Limpar Campo" class="buttonLeft"/>
							<input type="submit" value="Decompor Cód. barras" class="buttonRight">
						</form>
					</td>
				</tr>
			</table><br/><br/>
		</div>
		<div id="dialogoverlay">
		</div>
		<div id="dialogbox" role="alert">
			<div>
				<div id="dialogboxhead">
				</div>
				<div id="dialogboxbody">
					<img alt="Error.png" src="img/image_error.png">
				</div>
				<div id="dialogboxfoot">
				</div>
			</div>
		</div>
	</div>
</body>
</html>
