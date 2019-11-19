package alunosJSF.alunosJSF;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class TesteSPA implements Serializable{
	private String pagina = "testeLogin.xhtml";
	
	public void entrarLogin() {
		pagina = "testeLogin.xhtml";
	}
	
	public void entrarLayout() {
		pagina = "testeLayout.xhtml";
	}
	
	public String getPagina() {
		return pagina;
	}
	
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
}
