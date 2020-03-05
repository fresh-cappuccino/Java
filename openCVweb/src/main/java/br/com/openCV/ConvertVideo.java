package br.com.openCV;

import java.io.File;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.VideoAttributes;
import ws.schild.jave.VideoAttributes.X264_PROFILE;
import ws.schild.jave.VideoSize;

public class ConvertVideo {
	private static HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	private static String caminho = request.getServletContext().getRealPath(File.separator + "video");
	
	static File source = null;
	static File target = null;
	
	public static void convert(String originalName, String originalFormat, String newName, String newFormat) {
		
		source = new File(caminho + File.separator + originalName + originalFormat);
		target = ArquivoUtil.getFile(caminho, newName + newFormat);

		//------------------------------------------------------//
		System.out.println("Set Audio Attributes For Conversion");
		//------------------------------------------------------//
		
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("aac");
		// here 64kbit/s is 64000/
		audio.setBitRate(64000);
		audio.setChannels(2);
		audio.setSamplingRate(44100);
		

		//------------------------------------------------------//
		System.out.println("Set Video Attributes For Conversion");
		//------------------------------------------------------//
		
		VideoAttributes video = new VideoAttributes();
		video.setCodec("h264");
		video.setX264Profile(X264_PROFILE.BASELINE);
		// Here 160 kbps video is 160000
		Long l = source.length();
		System.out.println(l);
		video.setBitRate(l.intValue());
		// More the frames more quality and size, but keep it low based on devices like mobile
		video.setFrameRate(60);
		video.setSize(new VideoSize(1280, 720));

		//------------------------------------------------------//
		System.out.println("Set Enconding Attributes");
		//------------------------------------------------------//
		
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp4");
		attrs.setAudioAttributes(audio);
		attrs.setVideoAttributes(video);

		//------------------------------------------------------//
		System.out.println("Do The Enconding");
		//------------------------------------------------------//
		
		try {
			
			Encoder encoder = new Encoder();
			
			//------------------------------------------------------//
			System.out.println("Encoding...");
			//------------------------------------------------------//
			
			encoder.encode(new MultimediaObject(source), target, attrs);

			//------------------------------------------------------//
			System.out.println("Encoded");
			//------------------------------------------------------//
			
			if (source.exists()) { source.delete(); }
			
		} catch (Exception e) {
			
			/*Handle here the video failure*/
			e.printStackTrace();
			
		}
	}
}
