<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.*" %>
<%@ page import="com.calculos.Calcular" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>calculo</title>
	<link rel="stylesheet" type="text/css" href="css/calculo.css"/>
	
	<script type="text/javascript">
	var _userway_config = {
	account: 'a9kaaJFEQW'
	};
	</script>
	<script type="text/javascript" src="https://cdn.userway.org/widget.js"></script>
</head>
<body class="body_calculo">
	
	<!--L�gica do c�digo para decomposi��o de informa��es do c�digo de barras: ln 117 - 251-->
	<%
		//Cria��o de todas as vari�veis utilizadas
		Calcular cbInfo = new Calcular();
	  	String codigo = request.getParameter("codigo");
		char[] a = codigo.toCharArray();
		String banco = "Banco: ";
		String agencia = "Agencia: ";
		String nossoNum = "Nosso Numero: ";
		String strValor = "Valor: ";
		String dataVencimento = "Vencimento: ";
		String moeda = "Moeda: ";
		String ipte = "IPTE: ";
		String erro = new String();
		int mult = 2, soma = 0;
		int numCount = 0;
		String validado = new String();
		String cliente = "Cliente: ";
		String codBarras = "Codigo de Barras: ";
		
		StringBuilder strBuildAux = new StringBuilder();
		for (char index : a){
			if (!(index == '.'|| index == ' ')){
				strBuildAux.append(index);
			}
		}
		
		codigo = strBuildAux.toString().trim();
		
		
		//Verifica se o n�mero de caracteres lidos � 44. Se n�o, o c�digo de barras lido n�o est� no padr�o.
		if (codigo.length() == 44 || codigo.length() == 47) {
			
			if (cbInfo.validarCod(codigo)){
				//Vari�vel vencimento que obt�m a diferen�a entre 07/10/1997 e a data de vencimento em dias.
				//Vari�veis dia, mes tem o objetivo de separar o valor de vencimento em uma data v�lida.
				
				cbInfo.setCodBanco(codigo);
				cbInfo.setAgencia(codigo);
				cbInfo.setValor(codigo);
				cbInfo.setMoeda(codigo);
				cbInfo.setVencimento(codigo);
				cbInfo.setNossoNum(codigo);
				cbInfo.setCliente(codigo);
				cbInfo.setIpte(codigo);
				
				banco = banco.concat(Integer.toString(cbInfo.getCodBanco()) + " - (" + cbInfo.getNomeBanco(cbInfo.getCodBanco()) + ")");
				
				agencia = agencia.concat(Integer.toString(cbInfo.getAgencia()));
				
				//Verifica se o c�digo da moeda � 9. Caso seja, significa que a moeda em quest�o � o Real e faz-se, ent�o, uma formata��o R$ na String.
				if ("9".equals(Integer.toString(cbInfo.getMoeda()))) {
					strValor = strValor.concat("R$ ");
				}
				//Atribui para a String strValor o conte�do da vari�vel valor (Double), trocando o ponto por v�rgula.
				//Essa troca de atribui��es de String para Double e vice-versa � feita para retirada dos 0 a esquerda do valor.
				strValor = strValor.concat(Double.toString(cbInfo.getValor()).replace(".", ","));
				
				//Cria uma vari�vel em forma de lista, guardando cada caractere de strValor e verificando a posi��o exata para se por a v�rgula.
				String[] d = strValor.split("");
				if (d[d.length-2].equals(",")) {
				    strValor = strValor.concat("0");
				} else if (!",".equals(d[d.length-3])) {
				    strValor = strValor.concat(",00");
				}
		              
				moeda = moeda.concat(Integer.toString(cbInfo.getMoeda()));
				if (Integer.toString(cbInfo.getMoeda()).equals("9")) {
					moeda = moeda.concat(" (Real)");
				}else{
					moeda = moeda.concat("(Outra)");
				}
				dataVencimento = dataVencimento.concat(cbInfo.getDataVencimento());
				
				nossoNum = nossoNum.concat(cbInfo.getNossoNum());
				
				cliente = cliente.concat(cbInfo.getCliente());
				
				ipte = ipte.concat(cbInfo.getIpte());
				
				if (codigo.length() == 47) {
					char aux[] = cbInfo.getCodBarrasFromIpte(codigo);
					for (char index : aux){
				    	codBarras = codBarras.concat(index+"");
				    }
				} else {
					codBarras =  codBarras.concat(codigo);
				}
		              
				//Atribui o valor de "** BOLETO VALIDADO ** para a vari�vel validado. Mostrando que a convers�o e decomposi��o dos valores foi um sucesso.
				validado = "** BOLETO VALIDADO **";
			}else{
				//Caso entre nesse else, significa que o valor digitado tem 44 caracteres, mas tem um caractere inv�lido em seu meio.
				//Esse caractere inv�lido pode ser um n�mero que n�o condiz com o DV geral ou um caractere n�o num�rico.
				erro = "** C�DIGO INV�LIDO **";
				
				banco = "Banco: ***";
				agencia = "Agencia: ****";
				strValor = "Valor: () ***,**";
				moeda = "Moeda: * ()";
				dataVencimento = "Vencimento: **/**/**";
				cliente = "*******";
				nossoNum = "Nosso Numero: **/ ***********";
				ipte = "IPTE: *****.***** *****.****** *****.****** * **************";
				codBarras = "Codigo de Barras: ********************************************";
			}
		}else{
			//Caso esse else seja acionado, significa que o n�mero n�o possui 44 caracteres.
			erro = "** N�MERO DE CARACTERES INV�LIDO **";
			
			banco = "Banco: ***";
			agencia = "Agencia: ****";
			strValor = "Valor: () ***,**";
			moeda = "Moeda: * ()";
			dataVencimento = "Vencimento: **/**/**";
			cliente = "*******";
			nossoNum = "Nosso Numero: **/ ***********";
			ipte = "IPTE: *****.***** *****.****** *****.****** * **************";
			codBarras = "Codigo de Barras: ********************************************";
		}
	%>
	
	<!--Sa�da de dados para o browser-->
	<div class="conteiner" align="center">
		<br /><br /><br /><br /><br /><br />
		<table>
			<tr>
				<td>
					<h2 class="txt_validado">
					<%
					out.println(validado);
					%>
					</h2>
					<h2 class="txt_erro">
					<%
					out.println(erro);
					%>
					</h2>
				</td>
			</tr>
		</table>
		<br /><br />
		
		<table class="tabela">
			<tr>
				<td width="33%">
					<h2 class="txt">
					<%
					out.print(banco);
					%>
					</h2>
				</td>
				<td>
					<h2 class="txt">
					<%
					out.println(agencia);
					%>
					</h2>
				</td>
				<td width="33%">
					<h2 class="txt">
					<%
	                out.println(cliente);
					%>
					</h2>
				</td>
			</tr>
			<tr>
				<td>
					<h2 class="txt">
					<%
					out.println(strValor);
					%>
					</h2>
				</td>
				<td>
					<h2 class="txt">
					<%
					out.println(moeda);
					%>
					</h2>
				</td>
				<td>
					<h2 class="txt">
					<%
	                out.println(dataVencimento);
					%>
					</h2>
				</td>			
			</tr>
			<tr>
				<td colspan="3">
					<h2 class="txt">
					<%
					out.println(nossoNum);
					%>
					</h2>
				</td>
			</tr>
			<tr >
				<td colspan="3">
					<h2 class="ipte">
					<%
					out.println(ipte);
					%>
					</h2>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<h2 class="ipte">
					<%
					out.println(codBarras);
					%>
					</h2>
				</td>
			</tr>
		</table><br><br>
		<a  id="link" href="index.jsp">Ler outro c�digo de barras</a>
	</div>
	<br />

</body>
</html>