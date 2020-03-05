package br.com.openCV;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Handler implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String pageHandler;
	private String videoHandler;
	
	@PostConstruct
	void init() {
		pageHandler = "inputVideo.xhtml";
		videoHandler = "video.mp4";
	}

	public String getPageHandler() {
		return pageHandler;
	}

	public void setPageHandler(String pageHandler) {
		this.pageHandler = pageHandler;
	}
	
	public String getVideoHandler() {
		return videoHandler;
	}
	
	public void setVideoHandler(String videoHandler) {
		this.videoHandler = videoHandler;
	}
	
	public void inputVideo() {
		setPageHandler("inputVideo.xhtml");
	}
	
	public void showVideo() {
		setPageHandler("showVideo.xhtml");
	}
	
	public String redirectInputVideo() {
		setPageHandler("inputVideo.xhtml");
		return "videoHandler";
	}
	
	public String redirectShowVideo() {
		setPageHandler("showVideo.xhtml");
		return "videoHandler";
	}
	
}
