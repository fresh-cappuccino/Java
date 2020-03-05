package br.com.openCV;

public class VideoUtil {
	
	public static String getVideoName(String source) {
		
		int posDot = source.length() - 1;
		int posBar = source.length() - 1;
		
		for (; posDot >= 0; posDot--) {
			if (source.charAt(posDot) == '.') { break; }
		}
		
		for (; posBar >= 0; posBar--) {
			if (source.charAt(posBar) == '\\') { break; }
		}
		
		return source.substring(posBar != 0 ? posBar + 1 : posBar, posDot);
		
	}
	
	public static String getVideoFormat(String source) {
		
		int pos = source.length() - 1;
		
		for (; pos >= 0; pos--) {
			
			if (source.charAt(pos) == '.') { break; }
			
		}
		
		return source.substring(pos);
		
	}
	
}
