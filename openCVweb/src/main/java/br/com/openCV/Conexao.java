package br.com.openCV;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	public static Connection getConexao() {
		Connection conexao = null;
		
		//---------------URL------------------//
		String oracleDir = "jdbc:oracle:thin:@";
		String host = "192.168.20.57";
		String porta = "1521";
		String dataBase = "DESENV";
		String url = oracleDir + host + ":" + porta + ":" + dataBase;
		//---------------USUARIO--------------//
		String usuario = "leoferreira";
		//---------------SENHA----------------//
		String senha = "1234-leo";
		
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conexao = DriverManager.getConnection(url, usuario, senha);
		}/* catch(ClassNotFoundException e) {
			System.out.println("--- CLASS NOT FOUND");
			e.printStackTrace();
		} */catch(SQLException e) {
			System.out.println("--- SQL EXCEPTION");
			e.printStackTrace();
		}
		return conexao;
	}
}
