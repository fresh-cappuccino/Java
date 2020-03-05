package br.com.openCV;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class VideoHandler extends VideoUtil {
	
	private String pageView;
	private String video;
	private String selectedVideo;
	
	@Inject BasicView basicView;
	
	@PostConstruct
	void init() {
		
		pageView = "";
		video = "";
		selectedVideo = "";
		
	}

	public String getPageView() {
		
		return pageView;
		
	}

	public void setPageView(String pageView) {
		
		this.pageView = pageView;
		
	}
	
	public String getVideo() {
		
		return video;
		
	}

	public void setVideo(String video) {
		
		this.video = video;
		
	}
	
	public String getSelectedVideo() {
		
		return selectedVideo;
		
	}

	public void setSelectedVideo(String selectedVideo) {
		
		this.selectedVideo = selectedVideo;
		
	}

	public void showSelectedVideo(String source) {
		
		setPageView("showSelectedVideo.xhtml");
		setVideo("video" + File.separator + source);
		setSelectedVideo(source);
		
	}
	
	public String getSelectedVideoName() {
		
		return VideoUtil.getVideoName(getSelectedVideo());
		
	}
	
	public void alterName(String oldName) {
		
		String newName = basicView.getName();
		
		File file = ArquivoUtil.getFile(ArquivoUtil.getDefaultSource(), newName + ".mp4");
		
		basicView.getFiles().set(basicView.getFiles().indexOf(oldName + ".mp4"), VideoUtil.getVideoName(file.getAbsolutePath()));
		
		file = new File(ArquivoUtil.getDefaultSource(), oldName + ".mp4");
		
		file.renameTo(new File(newName + ".mp4"));
		
		System.out.println(file);
		
	}
	
	public String deleteFile(String file) {
		
		ArquivoUtil.deleteFile(ArquivoUtil.getDefaultSource() + File.separator + file);
		setSelectedVideo("");
		setPageView("");
		setVideo("");
		
		basicView.getFiles().remove(basicView.getFiles().indexOf(file));
		
		return null;
	}
	
	public void reset() {
		
		setPageView("");
		setVideo("");
		
	}
	
}
