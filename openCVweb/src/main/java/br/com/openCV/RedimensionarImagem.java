package br.com.openCV;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class RedimensionarImagem {
	
    private static final Logger logger = Logger.getLogger(RedimensionarImagem.class.getName());
    private static final String EXTENSAO = "JPG";
    private int largura = 100;
    private int altura = 100;
    
    public RedimensionarImagem() { }
    
    public RedimensionarImagem(int largura,int  altura) {
        super();
        this.altura = altura;
        this.largura = largura;
    }
    
    /**
     * Redimensiona imagem e retorna um array de bytes
     * @param imagem
     * @return
     */
    public byte[] redimensionar(InputStream imagem) {
    	
    	byte[] bytesImagem = null;
    	Graphics2D graphisImagem = null;
    	ByteArrayOutputStream baos = null;
    	
        try {
            //transforma InputStream em uma BufferedImage
            BufferedImage bufImagemLida =  ImageIO.read(imagem);
            
            //cria imagem para
            BufferedImage imagemRedimensionada = new BufferedImage(largura,altura , BufferedImage.TYPE_INT_RGB);
            
            //realiza o redimensionamento da imagem
            graphisImagem = imagemRedimensionada.createGraphics();
            graphisImagem.drawImage(bufImagemLida,0,0, largura, altura,null);
            baos = new ByteArrayOutputStream();
            
            //escreve a imagem no OutputStream
            ImageIO.write(imagemRedimensionada, EXTENSAO, baos);
            
            //transforma o OutputStream em array de bytes e retorna
            bytesImagem = baos.toByteArray();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            //libera recursos
            if(graphisImagem != null) {
                graphisImagem.dispose();
            }
            
            if(baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
            }
        }
        
        return bytesImagem;
    }
    
    public int getLargura() {
        return largura;
    }
    
    public int getAltura() {
        return altura;
    }
    
}
