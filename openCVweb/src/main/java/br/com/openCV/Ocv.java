package br.com.openCV;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Ocv {
//	private List<List> coordenates = new ArrayList<>();
//	private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//	private String caminho = request.getServletContext().getRealPath(File.separator + "img") + File.separator + "img" + File.separator + "arquivos";
	
	public void ocv(byte[] bytes, String idpessoa) throws IOException {
//		List<Integer> coord = new ArrayList<>();
//		coord.add(1);
//		coord.add(2);
		
//		coordenates.add(coord);
		
		double vertical_proportion = 0;
		double horizontal_proportion = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		int rostos_validados = 0;
		int rostos_proporcionais = 0;
		String nome = idpessoa + ".jpg";
		File imagem = new File(nome);
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(imagem);
			fos.write(bytes);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) try { fos.close(); } catch(IOException ioe) {}
		}
		
//		System.out.println(caminho + File.separator + name);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		nu.pattern.OpenCV.loadShared();
//		File imagem = new File(caminho + File.separator + name);
		BufferedImage img = ImageIO.read(imagem);
//		String image = caminho + File.separator + name;
//		Mat src = Imgcodecs.imread(image);
		System.out.println(imagem.getPath());
		Mat src = Imgcodecs.imread(imagem.getPath());
		
//		String xmlFile = request.getServletContext().getRealPath(File.separator + "WEB-INF") + File.separator + "xml" + File.separator + "lbpcascade_frontalface.xml";
		String xmlFile = "WebContent" + File.separator + "WEB-INF" + File.separator + "xml" + File.separator + "lbpcascade_frontalface.xml";
		CascadeClassifier cc = new CascadeClassifier(xmlFile);
		
		MatOfRect faceDetect = new MatOfRect();
		cc.detectMultiScale(src, faceDetect);
		
		System.out.println(String.format("Detect Facex: %d", faceDetect.toArray().length));
		
		System.out.println("Largura da imagem.: " + img.getWidth());
		System.out.println("Altura da imagem..: " + img.getHeight());
		
		int i = 0;
		for (Rect rect : faceDetect.toArray()) {
			i++;
			
			System.out.println();
			System.out.println("-=-=-=-=-=-=-=-=");
			System.out.println("---------------");
			System.out.println("Rosto " + i + ": ");
			System.out.println("---------------");
			System.out.println("Largura da face detectada..: " + rect.width);
			System.out.println("Altura da face detectada...: " + rect.height);
			System.out.println("Margem do rosto a esquerda.: " + rect.x);
			System.out.println("Margem do rosto acima......: " + rect.y);
			System.out.println("Margem do rosto a direita..: " + (img.getWidth() - (rect.x + rect.width)));
			System.out.println("Margem do rosto abaixo.....: " + (img.getHeight() - (rect.y + rect.width)));
			System.out.println();
			System.out.println("Porcentagem da largura do rosto.: " + (horizontal_proportion = Double.parseDouble(df.format(100.0 * rect.width / img.getWidth()).replace(',','.'))));
			System.out.println("Porcentagem da altura do rosto..: " + (vertical_proportion = Double.parseDouble(df.format(100.0 * rect.height / img.getHeight()).replace(',', '.'))));
			System.out.println("-=-=-=-=-=-=-=-=");
			System.out.println();
			
			Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 0, 255), 3);
			
			if (horizontal_proportion > 6 && vertical_proportion > 4.5) {
				rostos_validados++;
				
				Connection con = Conexao.getConexao();
				try {
					if (con != null) {
						PreparedStatement stp = con.prepareStatement("insert into rosto_teste(idpessoa, nrosto, largura, altura, x, y) values(?, ?, ?, ?, ?, ?)");
						stp.setInt(1, Integer.parseInt(idpessoa));
						stp.setInt(2, rostos_validados);
						stp.setDouble(3, rect.width);
						stp.setDouble(4, rect.height);
						stp.setDouble(5, rect.x);
						stp.setDouble(6, rect.y);
						
						stp.executeQuery();
					} else {
						System.out.println("Failed to make connection!");
					}
	
				} catch (SQLException e) {
					System.err.format("SQL State: %s\n%s ", e.getSQLState(), e.getMessage());
					System.out.println("SQL Exception: ");
	
				} catch (Exception e) {
					System.out.println("oder: ");
					e.printStackTrace();
				} finally {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
			}
			
			if (horizontal_proportion < 20.5 || vertical_proportion < 27.0) {
				System.out.println("    -----------------    ");
				System.out.println("Tamanho do rosto inválido");
				System.out.println("    -----------------    ");
			} else {
				rostos_proporcionais++;
			}
		}
		
		String status = "I";
		
		if (rostos_validados == 0) {
			System.out.println();
			System.out.println("------------------------");
			System.out.println("NENHUM ROSTO ENCONTRADO! EM " + idpessoa);
			System.out.println("------------------------");
			System.out.println();
		} else {
			if (rostos_validados > 1) {
				System.out.println();
				System.out.println("---------------------------");
				System.out.println("MAIS DE 1 ROSTO ENCONTRADO!");
				System.out.println("---------------------------");
				System.out.println();
			}
			else if (rostos_proporcionais == 1) {
				if (imagem.exists()) {
					imagem.delete();
				}
				status = "V";
			}
		}
		
		try {
			Connection con = Conexao.getConexao();
			
			if (con != null) {
				PreparedStatement stp = con.prepareStatement("insert into rosto_status(idpessoa, status, nmbr) values(?, ?, 1)");
				stp.setInt(1, Integer.parseInt(idpessoa));
				stp.setString(2, status);
				
				stp.executeQuery();
				
				con.close();
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s ", e.getSQLState(), e.getMessage());
			System.out.println("SQL Exception: ");

		} catch (Exception e) {
			System.out.println("oder: ");
			e.printStackTrace();
		}
		
		/*if (rostos_validados != 1) {
			Imgcodecs.imwrite(nome, src);
		} else {
			
			imagem = new File(nome);
			File file = new File("proportion" + File.separator + nome);
			
			if (imagem.exists()) {
				imagem.delete();
			}
			
			if (horizontal_proportion < 30 || vertical_proportion < 27) {
				Imgcodecs.imwrite("proportion" + File.separator + nome, src);
			} else {
				if (file.exists()) {
					file.delete();
				}
			}
		}*/
	}
	
	public static void main(String... args) {
		try {
			Connection con = Conexao.getConexao();
			
			if (con != null) {
				System.out.println("Connected to the database!");
				Statement st = con.createStatement();
				String sql = "SELECT sf.* " +
							 "FROM simfoto sf, rosto_status rs " +
							 "WHERE sf.idpessoa = rs.idpessoa AND " +
							 		"rs.status = 'N'";
				ResultSet rs = st.executeQuery(sql);
				openCV opcv = new openCV();
				
				while (rs.next()) {
//					System.out.println(rs.getString(2));
					
					opcv.ocv(rs.getBytes(2), rs.getString(1));
				}
				
				con.close();
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s ", e.getSQLState(), e.getMessage());
			System.out.println("SQL Exception: ");

		} catch (Exception e) {
			System.out.println("oder: ");
			e.printStackTrace();
		}
		
		System.out.println("FINISH");
	}
}
