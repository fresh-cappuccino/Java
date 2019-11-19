package alunosJSF.alunosJSF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

public class TesteOracle{
	public static void main(String[] args) throws Exception{
		Connection conexao = ObterConexaoTeste.conexao();
		int i = 1;
		try {
			String sql = "SELECT * from pessoas";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				System.out.println(i + " " + rs.getString(2));
				i++;
			}
			
			rs.close();
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(generateCPF());
		System.out.println(validaCPF(generateCPF()));
	}
	
	private static String calcDigVerif(String num) {
		Integer primDig, segDig;
		int soma = 0, peso = 10;
		for (int i = 0; i < num.length(); i++)
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		
		if (soma % 11 == 0 | soma % 11 == 1)
	        primDig = new Integer(0);
	    else
	        primDig = new Integer(11 - (soma % 11));
		
	    soma = 0;
	    peso = 11;
	    for (int i = 0; i < num.length(); i++)
	            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
	    
	    soma += primDig.intValue() * 2;
	    if (soma % 11 == 0 | soma % 11 == 1)
	        segDig = new Integer(0);
	    else
	        segDig = new Integer(11 - (soma % 11));
	    
	    return primDig.toString() + segDig.toString();
	}
	
	private static String generateCPF() {
		String iniciais = "";
	    Integer numero;
	    for (int i = 0; i < 9; i++) {
	        numero = new Integer((int) (Math.random() * 10));
	        iniciais += numero.toString();
	    }
	    return iniciais + calcDigVerif(iniciais);
	}
	
	public static boolean validaCPF(String cpf) {  
	    if (cpf.length() != 11)  
	        return false;

	    String numDig = cpf.substring(0, 9);  
	    return calcDigVerif(numDig).equals(cpf.substring(9, 11));  
	}
}
