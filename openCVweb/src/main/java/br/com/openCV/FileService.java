package br.com.openCV;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ApplicationScoped
public class FileService {
	
	private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	private String caminho = request.getServletContext().getRealPath(File.separator + "video");
	
	public List<String> fileService(String form) {
		
		File[] files = new File(caminho).listFiles();
		List<String> arqs = new ArrayList<>();
		
		if ("file".equals(form)) {
			
			for (File file : files) {
				arqs.add(getFileName(file.getAbsolutePath()));
			}
			
		} else if ("path".equals(form)) {
			
			for (File file : files) {
				arqs.add(file.getAbsolutePath());
			}
			
		}
		
		return arqs;
		
	}
	
	public List<String> pathService() {
		
		File[] files = new File(caminho).listFiles();
		List<String> arqs = new ArrayList<>();
		
		for (File file : files) {
			arqs.add(file.getAbsolutePath());
		}
		
		return arqs;
		
	}
	
	private static String getFileName(String path) {
		int pos = path.length() - 1;
		
		for (; pos >= 0; pos--) {
			if (path.charAt(pos) == '\\') {
				break;
			}
		}
		
		return path.substring(pos + 1);
	}
	
}
