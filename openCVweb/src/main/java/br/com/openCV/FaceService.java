package br.com.openCV;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ApplicationScoped
public class FaceService {
	private static final Logger logger = Logger.getLogger(FaceService.class.getName());
	private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	private String caminho = request.getServletContext().getRealPath(File.separator + "img");
	
	public List<Face> createFaces() {
		List<Face> list = new ArrayList<Face>();
		long id = 0;
		
		Connection con = Conexao.getConexao();
		try {
			if (con != null) {
				Statement st = con.createStatement();
				String sql = "SELECT rt.nrosto, " +
									"rt.x, " +
									"rt.y, " +
									"rt.largura, " +
									"rt.altura, " +
									"rt.idpessoa, " +
									"sf.largura, " +
									"sf.altura " +
							 "FROM rosto_teste rt, " +
								  "simfoto sf " +
							 "WHERE rt.idpessoa = sf.idpessoa AND " +
								   "rt.idpessoa = (SELECT MIN(rs.idpessoa) " +
							 					  "FROM rosto_status rs " +
							 					  "WHERE UPPER(rs.status) = 'U')";
				ResultSet rs = st.executeQuery(sql);
				
				while (rs.next()) {
					id = rs.getLong(6);
					list.add(new Face(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), id, rs.getInt(7), rs.getInt(8)));
				}
				
				PreparedStatement pst = con.prepareStatement("SELECT foto, largura, altura FROM simfoto WHERE idpessoa = ?");
				
				pst.setLong(1, id);
				rs = pst.executeQuery();
				
				while(rs.next()) {
					String nome = caminho + File.separator + "1.jpg";
					File imagem = new File(nome);
					FileOutputStream fos = null;
					
					try {
						fos = new FileOutputStream(imagem);
						fos.write(rs.getBytes(1));
					} catch(Exception e) {
						e.printStackTrace();
					} finally {
						if (fos != null) try { fos.close(); } catch(IOException ioe) { ioe.printStackTrace(); }
					}
					
					//cria o redimensionar imagem
			        RedimensionarImagem redImagem = new RedimensionarImagem(500, ((int) (rs.getInt(3) / (rs.getInt(2) / 500.0))));
			        
			        fos = null;
			        try {
			            //realizar o redimensionamento obtendo o array de byte para salver
			            byte[] bytesImagem = redImagem.redimensionar(new FileInputStream(caminho+File.separator+"1.jpg"));
			            fos = new FileOutputStream(caminho+File.separator+"2.jpg");
			            fos.write(bytesImagem);
			        } catch (FileNotFoundException e) {
			            logger.log(Level.SEVERE, e.getMessage());
			        } catch (IOException e) {
			            logger.log(Level.SEVERE, e.getMessage());
			        } finally {
			            //libera recursos
			            if(fos != null){
			                try {
			                    fos.close();
			                } catch (IOException e) {
			                    logger.log(Level.SEVERE, e.getMessage());
			                }
			            }
			        }
				}
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s ", e.getSQLState(), e.getMessage());
			System.out.println("SQL Exception: ");
			
		} catch (Exception e) {
			System.out.println("oder: ");
			e.printStackTrace();
		} finally { try { con.close(); } catch(Exception e) { e.printStackTrace(); } }
		
		return list;
	}
	
	public void newMB() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("ManagerBean");
	}
}
