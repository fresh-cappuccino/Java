package br.com.openCV;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
/*import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;*/

public class OCVUpdate implements Serializable {
	/*static {
		LoadLibrary.loadNativeLibrary();
	}*/
	
	public void ocv(byte[] bytes, String idpessoa, int x, int y, int width, int height) throws IOException {
		String caminho = "C:" + File.separator + "Users" + File.separator + "leoferreira" + File.separator + "Pictures" + File.separator + idpessoa + ".jpg";
		File imagem = new File(caminho);
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(caminho);
			
			fos.write(bytes);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		BufferedImage img = ImageIO.read(imagem);
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(img,  0,  0,  null);
		
		float[] scales = { 1f, 1f, 1f, 0.5f };
		float[] offsets = new float[4];
		RescaleOp rop = new RescaleOp(scales, offsets, null);
		
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawRect(x, y, width, height);
		g2d.dispose();
		
		g2d.drawImage(bi, rop, 0, 0);
	}
}
