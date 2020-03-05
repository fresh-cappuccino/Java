package br.com.openCV;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class ArquivoUtil extends VideoUtil {
	
	private static HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	private static String caminho = request.getServletContext().getRealPath(File.separator + "video");

	public static File escrever(String name, byte[] contents) throws IOException {
		
		File file = getFile(caminho, name);
		
		OutputStream os = new FileOutputStream(file);
		os.write(contents);
		os.close();
		
		return file;
	}
	
	public static File getFile(String source, String fileName) {
		
		File file = new File(source + File.separator + fileName);
		
		int count = 1;
		
		while (file.exists()) {
			
			file = new File(source + File.separator + getVideoName(fileName) + "(" + count + ")" + getVideoFormat(fileName));
			count ++;
			
		}
		
		return file;
		
	}
	
	public static void deleteFile(String path) {
		
		File file = new File(path);
		
		if (file.exists()) { file.delete(); }
		
	}
	
	public static String getDefaultSource() {
		return caminho;
	}
	
}