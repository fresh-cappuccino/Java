package br.com.openCV;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class setStatus {
	public void status(long idpessoa) {
		Connection con = Conexao.getConexao();
		try {
			if (con != null) {
				Statement st = con.createStatement();
				String sql = "UPDATE rosto_status SET status = 'U' WHERE idpessoa = " + idpessoa;
				st.executeQuery(sql);
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s ", e.getSQLState(), e.getMessage());
			System.out.println("SQL Exception: ");

		} catch (Exception e) {
			System.out.println("oder: ");
			e.printStackTrace();
		} finally {
			try { con.close(); } catch(Exception e) { e.printStackTrace(); }
		}
		
		System.out.println("ID: " + idpessoa);
	}
	
	public static void main(String... args) {
		Connection con = Conexao.getConexao();
		try {
			if (con != null) {
				Statement st = con.createStatement();
				String sql = "SELECT rt.idpessoa " +
							 "FROM rosto_teste rt " +
							 "WHERE (select max(r.nrosto) from rosto_teste r where r.idpessoa = rt.idpessoa) > 1 " +
							 "ORDER BY rt.idpessoa";
				ResultSet rs = st.executeQuery(sql);
				setStatus set = new setStatus();
				
				while (rs.next()) {
					set.status(rs.getLong(1));
				}
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s ", e.getSQLState(), e.getMessage());
			System.out.println("SQL Exception: ");

		} catch (Exception e) {
			System.out.println("oder: ");
			e.printStackTrace();
		} finally {
			try { con.close(); } catch(Exception e) { e.printStackTrace(); }
		}
		
		System.out.println("FINISH");
	}
}
