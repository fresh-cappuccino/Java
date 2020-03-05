package br.com.openCV;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("dtContextMenuView")
@ViewScoped
public class ContextMenuView implements Serializable {
	private List<Face> faces;
    private Face selectedFace;
    
    @Inject
    private FaceService service;
    
    @PostConstruct
    public void init() {
    	faces = service.createFaces();
    }
    
    public int getHeightCanvas() {
    	if (faces.get(0).getId() == 40806) {
    		return (int) (1228.0 / (921.0 / 500.0));
    	} else if(faces.get(0).getId() == 68044) {
    		return (int) (768.0 / (576.0 / 500.0));
    	} else {
    		return (int) (faces.get(0).getAltFoto() / (faces.get(0).getLarFoto() / 500.0));
    	}
    }
    
    public List<Face> getFaces() {
    	return faces;
    }
    
    public Face getSelectedFace() {
    	return selectedFace;
    }
    
    public void setSelectedFace(Face selectedFace) {
    	this.selectedFace = selectedFace;
    }
    
    public void setService(FaceService service) {
    	this.service = service;
    }
    
    public String deleteFace(long id, int rosto) {
    	Connection con = Conexao.getConexao();
    	try {
			if (con != null) {
				Statement st = con.createStatement();
				String sql = "DELETE FROM rosto_teste " +
							 "WHERE idpessoa = " + id + " AND nrosto = " + rosto;
				st.executeQuery(sql);
			} else {
				System.out.println("Failed to make connection!");
			}
			
			faces.remove(selectedFace);
	        selectedFace = null;
	        
	        ordenacaoRosto(id, rosto);
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s ", e.getSQLState(), e.getMessage());
			System.out.println("SQL Exception: ");
			
		} catch (Exception e) {
			System.out.println("oder: ");
			e.printStackTrace();
		} finally {
			try { con.close(); } catch(SQLException e) { e.printStackTrace(); }
		}
    	return "image";
    }
    
    private void ordenacaoRosto(long id, int rosto) {
    	rosto ++;
    	
    	while ((rosto-2) < faces.size()) {
    		faces.get(rosto-2).setRosto(rosto-1);
    		
    		Connection con = Conexao.getConexao();
        	try {
    			if (con != null) {
    				Statement st = con.createStatement();
    				String sql = "UPDATE rosto_teste " +
    							 "SET nrosto = " + (rosto-1) + " " +
    							 "WHERE idpessoa = " + id + " AND nrosto = " + rosto;
    				st.executeQuery(sql);
    			} else {
    				System.out.println("Failed to make connection!");
    				rosto = faces.size();
    			}
    		} catch (SQLException e) {
    			System.err.format("SQL State: %s\n%s ", e.getSQLState(), e.getMessage());
    			System.out.println("SQL Exception: ");
    			
    			rosto = faces.size();
    		} catch (Exception e) {
    			System.out.println("oder: ");
    			e.printStackTrace();
    			
    			rosto = faces.size();
    		} finally {
    			try { con.close(); } catch(SQLException e) { e.printStackTrace(); }
    		}
    		
    		rosto++;
    	}
    }
    
    public String nextFace() {
    	if (faces.size() == 0 || faces.size() > 1) {
    		Connection con = Conexao.getConexao();
        	try {
    			if (con != null) {
    				Statement st = con.createStatement();
    				String sql = "UPDATE rosto_status " +
    							 "SET status = 'I' " +
    							 "WHERE idpessoa = " + faces.get(0).getId();
    				st.executeQuery(sql);
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
    			try { con.close(); } catch(SQLException e) { e.printStackTrace(); }
    		}
    	} else {
    		int largura = 0, altura = 0;
    		
    		Connection con = Conexao.getConexao();
        	try {
    			if (con != null) {
    				Statement st = con.createStatement();
    				String sql = "SELECT largura, altura " +
    							 "FROM simfoto " +
    							 "WHERE idpessoa = " + faces.get(0).getId();
    				ResultSet rs = st.executeQuery(sql);
    				
    				while(rs.next()) {
    					largura = rs.getInt(1);
    					altura = rs.getInt(2);
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
    		} finally {
    			try { con.close(); } catch(SQLException e) { e.printStackTrace(); }
    		}
        	
        	double hor_prop = 100.0 * faces.get(0).getLar() / largura;
        	double ver_prop = 100.0 * faces.get(0).getAlt() / altura;
        	
        	con = Conexao.getConexao();
        	if (hor_prop > 20.5 && ver_prop > 27) {
            	try {
        			if (con != null) {
        				Statement st = con.createStatement();
        				String sql = "UPDATE rosto_status " +
        							 "SET status = 'V' " +
        							 "WHERE idpessoa = " + faces.get(0).getId();
        				st.executeQuery(sql);
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
        			try { con.close(); } catch(SQLException e) { e.printStackTrace(); }
        		}
        	} else {
            	try {
        			if (con != null) {
        				Statement st = con.createStatement();
        				String sql = "UPDATE rosto_status " +
        							 "SET status = 'I' " +
        							 "WHERE idpessoa = " + faces.get(0).getId();
        				st.executeQuery(sql);
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
        			try { con.close(); } catch(SQLException e) { e.printStackTrace(); }
        		}
        	}
    	}
    	
    	return "image";
    }
}
