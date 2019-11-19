package com.calculos;
import com.Bancos.Bancos;
import java.util.regex.Pattern;

public class Calcular{
	private static final int TOTAL_CARACTERES_IPTE_SEM_MASCARA = 47;
	private static final int DIA = 7;
	private static final int MES = 10;
	private static final int ANO = 1997;
	Bancos nomeBanco = new Bancos();
	private int codBanco;
	private int agencia;
	private double valor;
	private int moeda;
	private String dataVencimento;
	private int intVencimento = 0;
	private String nossoNum;
	private String cliente;
	private String ipte;
	
	public int getCodBanco() {
		return codBanco;
	}
	
	public void setCodBanco(String codigo) {
		initNomeBanco();
		
		final int FIRST_COD_NUMBER = 0;
		final int LAST_COD_NUMBER = 2;
		
		char[] b = garanteValorComoCodigoBarras(codigo);
		
		StringBuilder strBuildCodBanco = new StringBuilder();
        
        for (int i = FIRST_COD_NUMBER; i <= LAST_COD_NUMBER; i++) {
			strBuildCodBanco.append(b[i]);
		}
		codBanco = Integer.parseInt(strBuildCodBanco.toString());
	}
	
	public String getNomeBanco(int codigo) {
		return nomeBanco.getBanco(Integer.toString(codigo));
	}
	
	private void initNomeBanco() {
		nomeBanco.initialyzeBanco();
	}
	
	public int getAgencia() {
		return agencia;
	}
	
	public void setAgencia(String codigo) {
		char[] b = garanteValorComoCodigoBarras(codigo);
		final int POSICAO_PRIMEIRO_NUMERO_DA_AGENCIA = 19;
		final int POSICAO_ULTIMO_NUMERO_DA_AGENCIA = 22;
		
		StringBuilder strBuildAgencia = new StringBuilder();
		
		for (int i = POSICAO_PRIMEIRO_NUMERO_DA_AGENCIA; i <= POSICAO_ULTIMO_NUMERO_DA_AGENCIA; i++) {
			strBuildAgencia.append(b[i]);
		}
		
		agencia = Integer.parseInt(strBuildAgencia.toString());
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(String codigo) {
		char[] b = garanteValorComoCodigoBarras(codigo);
		final int POSICAO_PRIMEIRO_NUMERO_DO_VALOR = 9;
		final int POSICAO_ULTIMO_NUMERO_DO_VALOR = 18;
		
		StringBuilder strBuildValor = new StringBuilder();
		
		//Pega-se a parte do campo que representa o valor do boleto.
		for (int i = POSICAO_PRIMEIRO_NUMERO_DO_VALOR; i <= POSICAO_ULTIMO_NUMERO_DO_VALOR; i++) {
			strBuildValor.append(b[i]);
		}
		
		valor = Double.parseDouble(strBuildValor.toString())/100;
	}
	
	public int getMoeda() {
		return moeda;
	}
	
	public void setMoeda(String codigo) {
		char[] b = garanteValorComoCodigoBarras(codigo);
		final int COIN_POSITION = 3;
		
		moeda = Integer.parseInt(b[COIN_POSITION] + "");
	}
	
	public String getDataVencimento() {
		return dataVencimento;
	}
	
	private String padString(String string, char charComplement, int stringSize) {
		while (string.length() < stringSize) {
			string = (charComplement + "").concat(string);
		}
		
		return string;
	}
	
	public void setVencimento(String codigo) {
		initIntVencimento(getStrFatVencimento(codigo));
		
		String data = buildData(DIA, MES, ANO);
		
		String strDay = data.split(",")[0];
		String strMonth = data.split(",")[1];
		String strYear = data.split(",")[2];
		
		final int DATAFORMAT_DIA_E_MES = 2;
		final int DATAFORMAT_ANO = 4;
		
		strDay = padString(strDay, '0', DATAFORMAT_DIA_E_MES);
		strMonth = padString(strMonth, '0', DATAFORMAT_DIA_E_MES);
		strYear = padString(strYear, '0', DATAFORMAT_ANO);
		
		dataVencimento = strDay + "/" + strMonth + "/" + strYear;
	}
	
	public int getIntVencimento() {
		return intVencimento;
	}
	
	private void initIntVencimento(int intVencimento) {
		this.intVencimento = intVencimento;
	}
	
	public String getNossoNum() {
		return nossoNum;
	}
	
	public void setNossoNum(String codigo) {
		char[] b = garanteValorComoCodigoBarras(codigo);
		final int POSICAO_PRIMEIRO_VALOR_NOSSO_NUMERO = 23;
		final int POSICAO_VALOR_ANTERIOR_A_BARRA = 24;
		final int POSICAO_POSTERIOR_A_BARRA = 25;
		final int POSICAO_ULTIMO_VALOR_NOSSO_NUMERO = 35;
		
		StringBuilder strBuildNossoNum = new StringBuilder();
		
        for (int i = POSICAO_PRIMEIRO_VALOR_NOSSO_NUMERO; i <= POSICAO_VALOR_ANTERIOR_A_BARRA; i++) {
            strBuildNossoNum.append(b[i]);
        }
        
        strBuildNossoNum.append(" / ");
        
        for (int i = POSICAO_POSTERIOR_A_BARRA; i <= POSICAO_ULTIMO_VALOR_NOSSO_NUMERO; i++) {
            strBuildNossoNum.append(b[i]);
        }
        
        nossoNum = strBuildNossoNum.toString();
	}
	
	public String getIpte() {
		return ipte;
	}
	
	private char[] criaPrimeiroCampoIpte(char[] b) {
		StringBuilder strBuildCampo1 = new StringBuilder();
		
		final int PRIMEIRA_POSICAO_COD_BANCO = 0;
		final int ULTIMA_POSICAO_COD_BANCO = 2;
		final int POSICAO_MOEDA = 3;
		final int PRIMEIRA_POSICAO_AGENCIA = 19;
		final int ULTIMA_POSICAO_AGENCIA = 23;
		
		for (int i = PRIMEIRA_POSICAO_COD_BANCO; i <= ULTIMA_POSICAO_COD_BANCO; i++){
            strBuildCampo1.append(b[i]);
        }
        
        strBuildCampo1.append(b[POSICAO_MOEDA]);
        
        for (int i = PRIMEIRA_POSICAO_AGENCIA; i <= ULTIMA_POSICAO_AGENCIA; i++){
            strBuildCampo1.append(b[i]);
        }
        //Iniciando as variáveis para calcular o DAC (Dígito Verificador de Campo) do campo 1.
        int mult = 2;
        int soma = 0;
        
        char[] campo1 = strBuildCampo1.toString().toCharArray();
        
        //Calculando o DAC
        for (int i = campo1.length - 1; i >= 0; i--){
            int temp = mult*Integer.parseInt(campo1[i] + "");
            mult--;
            if (mult == 0){
                mult=2;
            }
            if (temp > 9){
                soma += temp/10 + temp%10;
            }else{
                soma += temp;
            }
        }
        if (soma%10 != 0){
            strBuildCampo1.append(Integer.toString(10-(soma%10)).charAt(0));
        }else{
            strBuildCampo1.append('0');
        }
        
        return strBuildCampo1.toString().toCharArray();
	}
	
	private char[] criaSegundoCampoIpte(char[] b) {
		StringBuilder strBuildCampo2 = new StringBuilder();
		
		final int PRIMEIRA_POSICAO_NOSSO_NUM = 24;
		final int POSICAO_NOSSO_NUM_PRE_CALCULO_DAC = 33;
		
		for (int i = PRIMEIRA_POSICAO_NOSSO_NUM; i <= POSICAO_NOSSO_NUM_PRE_CALCULO_DAC; i++){
            strBuildCampo2.append(b[i]);
        }
        
		char[] campo2 = strBuildCampo2.toString().toCharArray();
		
        //Calcula-se o DAC do campo 2.
        int mult = 2;
        int soma = 0;
        for (int i = campo2.length - 1; i >= 0; i--){
            int temp = mult*Integer.parseInt(campo2[i] + "");
            mult--;
            if (mult == 0){
                mult = 2;
            }
            if (temp > 9){
                soma += temp/10 + temp%10;
            }else{
                soma += temp;
            }
        }
        if (soma%10 != 0){
            strBuildCampo2.append(Integer.toString(10-(soma%10)).charAt(0));
        }else{
            strBuildCampo2.append('0');
        }
		
		return strBuildCampo2.toString().toCharArray();
	}
	
	private char[] criaTerceiroCampoIpte(char[] b) {
		StringBuilder strBuildCampo3 = new StringBuilder();
		
		final int POSICAO_NOSSO_NUM_POS_CALCULO_DAC = 34;
		final int ULTIMA_POSICAO_NOSSO_NUM = 35;
		final int PRIMEIRA_POSICAO_CAMPO_LIVRE = 36;
		final int ULTIMA_POSICAO_CAMPO_LIVRE = 43;
		final int POSICAO_DIGITO_VERIFICADOR_GERAL = 4;
		final int PRIMEIRA_POSICAO_FATOR_VENCIMENTO = 5;
		final int ULTIMA_POSICAO_FATOR_VENCIMENTO = 8;
		final int PRIMEIRA_POSICAO_VALOR = 9;
		final int ULTIMA_POSICAO_VALOR = 18;
		
        for (int i = POSICAO_NOSSO_NUM_POS_CALCULO_DAC; i <= ULTIMA_POSICAO_NOSSO_NUM; i++){
            strBuildCampo3.append(b[i]);
        }
        
        for (int i = PRIMEIRA_POSICAO_CAMPO_LIVRE; i <= ULTIMA_POSICAO_CAMPO_LIVRE; i++) {
        	strBuildCampo3.append(b[i]);
        }
        
        char[] campo3 = strBuildCampo3.toString().toCharArray();
        
        //Calcula o DAC do campo 3.
        int mult = 2;
        int soma = 0;
        for (int i = campo3.length - 1; i >= 0; i--){
            int temp = mult*Integer.parseInt(campo3[i]+"");
            mult--;
            if (mult == 0){
                mult = 2;
            }
            if (temp>9){
                soma += temp/10 + temp%10;
            }else{
                soma += temp;
            }
        }
        if (soma%10 != 0){
            strBuildCampo3.append(Integer.toString(10-(soma%10)).charAt(0));
        }else{
            strBuildCampo3.append('0');
        }
        
        //Acrescenta 1 a variável pos, para inserir o restante dos valores no campo.
        
        strBuildCampo3.append(b[POSICAO_DIGITO_VERIFICADOR_GERAL]);
        
        for (int i = PRIMEIRA_POSICAO_FATOR_VENCIMENTO; i <= ULTIMA_POSICAO_FATOR_VENCIMENTO; i++) {
        	strBuildCampo3.append(b[i]);
        }
        
        for (int i = PRIMEIRA_POSICAO_VALOR; i <= ULTIMA_POSICAO_VALOR; i++) {
        	strBuildCampo3.append(b[i]);
        }
		
		return strBuildCampo3.toString().toCharArray();
	}
	
	private String formatIpte(String ipte) {
		StringBuilder strBuildIpte = new StringBuilder();
		char[] b = ipte.toCharArray();
		
		//Acrescenta o primeiro campo a variável, já o formatando visualmente com espaços e pontos.
        for (int i = 0; i < b.length; i++){
            strBuildIpte.append(b[i]);
            if (i == 4 || i == 14 || i == 25){
                strBuildIpte.append(".");
            }else if (i == 9 || i == 20 || i == 31 || i == 32){
            	strBuildIpte.append(" ");
            }
        }
		return strBuildIpte.toString();
	}
	
	public void setIpte(String codigo) {
		char[] b = garanteValorComoCodigoBarras(codigo);
		
        char[] campo1 = criaPrimeiroCampoIpte(b);
        char[] campo2 = criaSegundoCampoIpte(b);
        char[] campo3 = criaTerceiroCampoIpte(b);
        
        StringBuilder strBuildIpte = new StringBuilder();
        
        for (int i = 0; i < campo1.length; i++) {
        	strBuildIpte.append(campo1[i]);
        }
        for (int i = 0; i < campo2.length; i++) {
        	strBuildIpte.append(campo2[i]);
        }
        for (int i = 0; i < campo3.length; i++) {
        	strBuildIpte.append(campo3[i]);
        }
        
        ipte = formatIpte(strBuildIpte.toString());
	}
	
	public String getCliente() {
		return cliente;
	}
	
	public void setCliente(String codigo) {
		char[] b = garanteValorComoCodigoBarras(codigo);
		
		final int PRIMEIRA_POSICAO_CLIENTE = 36;
		final int ULTIMA_POSICAO_CLIENTE = 42;
		
		StringBuilder strBuildCliente = new StringBuilder();
		
		for (int i = PRIMEIRA_POSICAO_CLIENTE; i <= ULTIMA_POSICAO_CLIENTE; i++) {
			strBuildCliente.append(b[i]);
		}
		
		cliente = strBuildCliente.toString();
	}
	
	private boolean mesCom31Dias(int mes) {
		if (mes < 8) {
			if (mes%2 == 1) {
				return true;
			}
		} else if (mes%2 == 0) {
			return true;
		}
		return false;
	}
	
	private String buildData(int dia, int mes, int ano) {
		final int QNT_DIAS_EM_UM_ANO_NAO_BISSEXTO = 365;
		final int QNT_DIAS_EM_UM_ANO_BISSEXTO = 366;
		final int QNT_DIAS_PARA_COMPLETAR_O_ANO_NA_DATABASE_07_10_1997 = 86;
		final int FEVEREIRO = 2;
		
        if (intVencimento >= QNT_DIAS_PARA_COMPLETAR_O_ANO_NA_DATABASE_07_10_1997)
        {
            intVencimento -= QNT_DIAS_PARA_COMPLETAR_O_ANO_NA_DATABASE_07_10_1997;
            dia = 1;
            mes = 1;
            ano += 1;
        }
		
		while (intVencimento > QNT_DIAS_EM_UM_ANO_NAO_BISSEXTO)
        {
        	ano += 1;
            if (naoEhBissexto(ano))
            {
            	intVencimento -= QNT_DIAS_EM_UM_ANO_NAO_BISSEXTO;
            }else{
            	intVencimento -= QNT_DIAS_EM_UM_ANO_BISSEXTO;
            }
        }
		
		while (intVencimento>0)
        {
        	if (mesCom31Dias(mes)) {
        		while (dia != 31 && intVencimento>0){
                    dia++;
                    intVencimento--;
                }
                
                if (dia==31 && intVencimento>0){
                    dia = 1;
                    mes++;
                    intVencimento--;
                }
        	} else {
        		if (mes == FEVEREIRO) {
        			while (dia!=28 && intVencimento>0){
                        dia++;
                        intVencimento--;
                    }
                	
                	if (naoEhBissexto(ano) && intVencimento>0){
                    	if (dia==28 && intVencimento>0){
                            dia = 1;
                            mes++;
                            intVencimento--;
                        }
                    }else{
                    	dia++;
                        intVencimento--;
                        
                        if (dia == 29 && intVencimento > 0){
                            dia = 1;
                            mes++;
                            intVencimento--;
                        }
                    }
        		} else {
        			while (dia != 30 && intVencimento>0){
                        dia++;
                        intVencimento--;
                    }
        			
                    if (dia==30 && intVencimento>0){
                        dia = 1;
                        mes++;
                        intVencimento--;
                    }
        		}
        	}
            
        	if (mes == 13) {
        		dia = 1;
        		mes = 1;
        		ano++;
        	}
        }
		return Integer.toString(dia) + "," + Integer.toString(mes) + "," + Integer.toString(ano);
	}

	private boolean naoEhBissexto(int ano) {
		return ano%4!=0 || (ano%100==0 && ano%400!=0);
	}
	
	public boolean validarCod(String codigo) {
		char[] b = garanteValorComoCodigoBarras(codigo);
		
		final int TAMANHO_COD_BARRAS_SEM_O_DV = 43;
		final int POSICAO_DV = 4;
		
		int soma = 0;
		int mult = 2;
		int numCount = 0;
		
		char[] c = new char[TAMANHO_COD_BARRAS_SEM_O_DV];
		for (int i = b.length - 1, j = 0; i >= 0; i--) {
			if (i != POSICAO_DV) {
				c[j] = b[i];
				j++;
			}
		}
		
		//Recalculando o DV geral (MOD 11).
		for (char index : c) {
			if (Pattern.compile("[0-9]{1}").matcher(index+"").matches())
            {
                soma += mult * Integer.parseInt(index+"");
                numCount++;
            }
			mult++;
			if (mult == 10) {
				mult = 2;
			}
		}
		soma = 11 - soma%11;
		
		if (soma > 9)
		{
			soma = 0;
		}
		
		if ((b[POSICAO_DV]+"").equals(Integer.toString(soma)) && numCount == TAMANHO_COD_BARRAS_SEM_O_DV){
			return true;
		}
		return false;
	}
	
	private int getStrFatVencimento(String codigo) {
		char[] b = garanteValorComoCodigoBarras(codigo);
		
		final int POSICAO_INICIAL_FAT_VENCIMENTO = 5;
		final int POSICAO_FINAL_FAT_VENCIMENTO = 8;
		
		int fatVencimento = 0;
		
		for (int i = POSICAO_INICIAL_FAT_VENCIMENTO; i <= POSICAO_FINAL_FAT_VENCIMENTO; i++) {
            fatVencimento *= 10;
            fatVencimento += Integer.parseInt(b[i]+"");
        }
        
        return fatVencimento;
	}

	private char[] garanteValorComoCodigoBarras(String codigo) {
		if ( codigo.length() == TOTAL_CARACTERES_IPTE_SEM_MASCARA ) {
			return getCodBarrasFromIpte(codigo);
		}
		return codigo.toCharArray();
	}
	
	public char[] getCodBarrasFromIpte(String codigo) {
		final int POSICAO_INICIAL_COD_BANCO = 0;
		final int POSICAO_FINAL_COD_BANCO = 2;
		final int POSICAO_MOEDA = 3;
		final int POSICAO_DV_GERAL = 32;
		final int POSICAO_INICIAL_FAT_VENCIMENTO = 33;
		final int POSICAO_FINAL_FAT_VENCIMENTO = 36;
		final int POSICAO_INICIAL_VALOR = 37;
		final int POSICAO_FINAL_VALOR = 46;
		final int POSICAO_INICIAL_AGENCIA = 4;
		final int POSICAO_FINAL_AGENCIA = 7;
		final int POSICAO_PRIMEIRO_DIGITO_NOSSO_NUM = 8;
		final int POSICAO_SEGUNDO_DIGITO_NOSSO_NUM = 10;
		final int POSICAO_ANTEPENULTIMO_DIGITO_NOSSO_NUM = 19;
		final int POSICAO_PENULTIMO_DIGITO_NOSSO_NUM = 21;
		final int POSICAO_ULTIMO_DIGITO_NOSSO_NUM = 22;
		final int POSICAO_INICIAL_CL = 23;
		final int POSICAO_FINAL_CL = 30;
		
		char[] a = codigo.toCharArray();
		
		StringBuilder strBuildCod = new StringBuilder();
		
		for (int i = POSICAO_INICIAL_COD_BANCO; i <= POSICAO_FINAL_COD_BANCO; i++) {
			strBuildCod.append(a[i]);
		}
		
		strBuildCod.append(a[POSICAO_MOEDA]);
		
		strBuildCod.append(a[POSICAO_DV_GERAL]);
		
		for (int i = POSICAO_INICIAL_FAT_VENCIMENTO; i <= POSICAO_FINAL_FAT_VENCIMENTO; i++) {
			strBuildCod.append(a[i]);
		}
		
		for (int i = POSICAO_INICIAL_VALOR; i <= POSICAO_FINAL_VALOR; i++) {
			strBuildCod.append(a[i]);
		}
		
		for (int i = POSICAO_INICIAL_AGENCIA; i <= POSICAO_FINAL_AGENCIA; i++) {
			strBuildCod.append(a[i]);
		}

		strBuildCod.append(a[POSICAO_PRIMEIRO_DIGITO_NOSSO_NUM]);
		
		for (int i = POSICAO_SEGUNDO_DIGITO_NOSSO_NUM; i <= POSICAO_ANTEPENULTIMO_DIGITO_NOSSO_NUM; i++) {
			strBuildCod.append(a[i]);
		}
		
		for (int i = POSICAO_PENULTIMO_DIGITO_NOSSO_NUM; i <= POSICAO_ULTIMO_DIGITO_NOSSO_NUM; i++) {
			strBuildCod.append(a[i]);
		}
		
		for (int i = POSICAO_INICIAL_CL; i <= POSICAO_FINAL_CL; i++) {
			strBuildCod.append(a[i]);
		}
		
		return strBuildCod.toString().toCharArray();
	}
	
	public String getIpteNumOnly(String ipte) {
		String[] a = ipte.split("");

		StringBuilder strBuildAux = new StringBuilder();
		for (String index : a){
			if (!(index.equals(".") || index.equals(" "))){
				strBuildAux.append(index);
			}
		}
		return strBuildAux.toString();
	}
}
