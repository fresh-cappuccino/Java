package br.com.alunosJSF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "dtFilterView")
@ViewScoped
public class FilterView implements Serializable {
	
	private Validador validador =  new Validador();

	private List<Pessoa> viewAlunos;

	private List<Pessoa> filteredAlunos;
	
	@Inject private PessoaMB pessoa;

	@SuppressWarnings("restriction")
	@PostConstruct
	public void init() {
		viewAlunos = pessoa.getPessoas();
	}
/*
	public boolean filterByAluno(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
	}
*/

	public List<String> getIds() {
		List <String> ids = new ArrayList<String>();
		for (Pessoa index : pessoa.getPessoas()) {
			ids.add(index.getId());
		}
		return validador.login(pessoa.getLogin().getUsuario(), pessoa.getLogin().getConfirmaUsuario(), pessoa.getLogin().getConfirmaSenha(), pessoa.getLogin().getDefaultPass()) == true ? ids : new ArrayList<String>();
	}

	public List<String> getNomes() {
		List<String> nomes = new ArrayList<String>();
		for (Pessoa index : pessoa.getPessoas()) {
			nomes.add(index.getNome());
		}
		return validador.login(pessoa.getLogin().getUsuario(), pessoa.getLogin().getConfirmaUsuario(), pessoa.getLogin().getConfirmaSenha(), pessoa.getLogin().getDefaultPass()) == true ? nomes : new ArrayList<String>();
	}

	public List<Pessoa> getViewAlunos() {
		return validador.login(pessoa.getLogin().getUsuario(), pessoa.getLogin().getConfirmaUsuario(), pessoa.getLogin().getConfirmaSenha(), pessoa.getLogin().getDefaultPass()) == true ? viewAlunos : new ArrayList<Pessoa>();
	}

	public List<Pessoa> getFilteredAlunos() {
		return validador.login(pessoa.getLogin().getUsuario(), pessoa.getLogin().getConfirmaUsuario(), pessoa.getLogin().getConfirmaSenha(), pessoa.getLogin().getDefaultPass()) == true ? filteredAlunos : new ArrayList<Pessoa>();
	}

	public void setFilteredAlunos(List<Pessoa> filteredAlunos) {
		this.filteredAlunos = filteredAlunos;
	}
}