package br.com.openCV;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class UploadAvancadoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject Handler handler;

	public void upload(FileUploadEvent event) {
		
		UploadedFile uf = event.getFile();

		try {
			
			File arquivo = ArquivoUtil.escrever(uf.getFileName(), uf.getContents());
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Upload completo", "O arquivo " + arquivo.getName() + " foi salvo!"));
			
			String name = ArquivoUtil.getVideoName(arquivo.getName());
			String format = ArquivoUtil.getVideoFormat(arquivo.getName());
			
			if (!(format.equals(".mp4"))) { ConvertVideo.convert(name, format, name, ".mp4"); }
			
			handler.setVideoHandler(name + ".mp4");
			
		} catch (IOException e) {
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
			
		}
		
		handler.showVideo();
		
	}
}