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
	
	<!--Lógica do código para decomposição de informações do código de barras: ln 117 - 251-->
	<%
		//Criação de todas as variáveis utilizadas
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
		
		
		//Verifica se o número de caracteres lidos é 44. Se não, o código de barras lido não está no padrão.
		if (codigo.length() == 44 || codigo.length() == 47) {
			
			if (cbInfo.validarCod(codigo)){
				//Variável vencimento que obtém a diferença entre 07/10/1997 e a data de vencimento em dias.
				//Variáveis dia, mes tem o objetivo de separar o valor de vencimento em uma data válida.
				
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
				
				//Verifica se o código da moeda é 9. Caso seja, significa que a moeda em questão é o Real e faz-se, então, uma formatação R$ na String.
				if ("9".equals(Integer.toString(cbInfo.getMoeda()))) {
					strValor = strValor.concat("R$ ");
				}
				//Atribui para a String strValor o conteúdo da variável valor (Double), trocando o ponto por vírgula.
				//Essa troca de atribuições de String para Double e vice-versa é feita para retirada dos 0 a esquerda do valor.
				strValor = strValor.concat(Double.toString(cbInfo.getValor()).replace(".", ","));
				
				//Cria uma variável em forma de lista, guardando cada caractere de strValor e verificando a posição exata para se por a vírgula.
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
		              
				//Atribui o valor de "** BOLETO VALIDADO ** para a variável validado. Mostrando que a conversão e decomposição dos valores foi um sucesso.
				validado = "** BOLETO VALIDADO **";
			}else{
				//Caso entre nesse else, significa que o valor digitado tem 44 caracteres, mas tem um caractere inválido em seu meio.
				//Esse caractere inválido pode ser um número que não condiz com o DV geral ou um caractere não numérico.
				erro = "** CÓDIGO INVÁLIDO **";
				
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
			//Caso esse else seja acionado, significa que o número não possui 44 caracteres.
			erro = "** NÚMERO DE CARACTERES INVÁLIDO **";
			
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
	
	<!--Saída de dados para o browser-->
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
		<a  id="link" href="index.jsp">Ler outro código de barras</a>
	</div>
	<br />

</body>
</html>