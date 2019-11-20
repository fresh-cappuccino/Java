package br.com.alunosJSF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ObterConexao {
	public static Connection conexao() {
		Connection conexao = null;
		
		String server = "localhost";
		String porta = "1521";
		String database = "";
		
		String usuario = "leoandrade";
		String senha = "password";
		
		String url = "jdbc:oracle:thin:@192.168.20.57:1521:DESENV";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conexao = DriverManager.getConnection(url, usuario, senha);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return conexao;
	}
}
