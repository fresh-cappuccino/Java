package br.com.alunosJSF;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named(value = "pdfControlerView")
@RequestScoped
public class PDFControllerView implements Serializable {
	private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//	private String nmArqJrxml = "Pessoas.jrxml";
	private String nmArqJasper = "Pessoas.jasper";
	private String nmArqPDF = "ListagemAlunos.pdf";
	private String caminho = request.getServletContext().getRealPath("/WEB-INF") + "/classes/report/";
	private StreamedContent streamedContent;
	
	@PostConstruct
	void init() {
		geraPDF();
		try {
			FileInputStream fis = new FileInputStream(new File(caminho+nmArqPDF));
			streamedContent = new DefaultStreamedContent(fis, "application/pdf");
		} catch(Exception e) {
			Validador validador = new Validador();
			validador.mostraMensagemERROR("NÃO FOI POSSÍVEL GERAR O PDF", "Contact admin.");
			System.out.println(e.getMessage());
		}
	}
	
	private Connection getConexao() throws SQLException {
		//---------------URL------------------//
		String oracleDir = "jdbc:oracle:thin:@";
		String host = "192.168.20.57";
		String porta = "1521";
		String dataBase = "DESENV";
		//---------------USUARIO--------------//
		String usuario = "leo_andrade";
		//---------------SENHA----------------//
		String senha = "password";
		
		return DriverManager.getConnection(oracleDir + host + ":" + porta + ":" + dataBase, usuario, senha);
	}
	
	private void geraPDF() {
		String diretorio = caminho + nmArqJasper;
		try {
			
			// Cria o mapa de parâmetros que será enviado ao relatório
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			
			// Preenche os dados do relatório
			JasperPrint jasperPrint = JasperFillManager.fillReport(diretorio, parametros, getConexao());
			
			// Gera o arquivo PDF no caminho especificado
			JasperExportManager.exportReportToPdfFile(jasperPrint, caminho+nmArqPDF);
			
		} catch (Exception e) {
//			System.out.println("ERRO AO CRIAR PDF");
			e.printStackTrace();
		}
	}
	
	public StreamedContent getStreamedContent() {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
            return new DefaultStreamedContent();
        } else {
        	System.out.println(caminho+nmArqPDF);
        	return streamedContent;
        }
	}
	
	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}
}
