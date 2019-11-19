package br.com.alunosJSF;

import java.io.ByteArrayOutputStream;
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

public class JasperPdfGenerator {
	private Connection getConexao() throws SQLException, ClassNotFoundException {
		Connection conexao = DriverManager.getConnection("jdbc:oracle:thin:@192.168.20.57:1521:DESENV", "leoferreira", "1234-leo");
		return conexao;
	}
	
	public ByteArrayOutputStream geraPdf() throws JRException {
		System.out.println("Inicio");
		JasperPdfGenerator geraRelatorio = new JasperPdfGenerator();
		String path = "src/main/java/report/";
		return geraRelatorio.gerarPDF(path);
//		System.out.println("Fim");
	}
	
	private ByteArrayOutputStream gerarPDF(String diretorio) {
		ByteArrayOutputStream retorno = null;
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
			
			// Gera o arquivo PDF no caminho especificado
			JasperExportManager.exportReportToPdfStream(jasperPrint, retorno);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

}
