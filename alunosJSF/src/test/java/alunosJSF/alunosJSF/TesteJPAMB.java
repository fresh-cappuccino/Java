package alunosJSF.alunosJSF;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class TesteJPAMB {
	@EJB
	private TesteJPA tJPA;
	
	public String getNome() throws Exception {
		return tJPA.getNome();
	}
}
