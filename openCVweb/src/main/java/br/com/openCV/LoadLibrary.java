package br.com.openCV;

import org.opencv.core.Core;

public class LoadLibrary {
	public static void loadNativeLibrary() {
		try {
			System.out.println("Loading...");
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			System.out.println("Loaded!");
		} catch (Exception e) {
			System.out.println("Did not load!");
//			e.printStackTrace();
		}
	}
}
