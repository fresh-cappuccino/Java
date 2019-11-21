package alunosJSF.alunosJSF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

public class TesteJasper {
	private Connection getConexao() throws SQLException, ClassNotFoundException {
		Connection conexao = DriverManager.getConnection("jdbc:oracle:thin:@192.168.20.57:1521:DESENV", "leoferreira", "1234-leo");
		return conexao;
	}
	
	public void geraPdf() throws JRException {
		System.out.println("Inicio");
		TesteJasper geraRelatorio = new TesteJasper();
		String path = "src/main/java/report/";
		geraRelatorio.gerarPDF(path);
		System.out.println("Fim");
	}
	
	private byte[] gerarPDF(String diretorio) {
		byte[] retorno = null;
		String relatorio = diretorio + "Pessoas.jrxml";
//		String imagem = diretorio + "wood.jpg";
		try {
			// Faz a compilação do relatório
			JasperReport jasperReport = JasperCompileManager.compileReport(relatorio);
			
			// Cria o mapa de parâmetros que será enviado ao relatório
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			
			// Faz o apontamento para a imagem que fce no top do relatório
			// paramatros.put("logo", imagem);
			
			// Preenche os dados do relatório
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, getConexao());
			
			// Objeto para a ser retornado
			retorno = JasperRunManager.runReportToPdf(jasperReport, parametros, getConexao());
			
			// Gera o arquivo PDF no caminho especificado
			JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/java/report/ListagemAlunos.pdf");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
