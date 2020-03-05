package br.com.openCV;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("dtBasicView")
@ViewScoped
public class BasicView implements Serializable {
	
	private List<String> files;
	private String name;
	
	@Inject
	private FileService service;
	
	@PostConstruct
	public void init() {
		files = service.fileService("file");
	}
	
	public List<String> getFiles() {
		return files;
	}
	
	public void setService(FileService service) {
		this.service = service;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
