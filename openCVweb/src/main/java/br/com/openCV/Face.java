package br.com.openCV;

import java.io.Serializable;

public class Face implements Serializable {
	private int rosto;
	private int x;
	private int y;
	private int lar;
	private int alt;
	private long id;
	private int larFoto;
	private int altFoto;
    
    public Face() { }
    
    public Face(int rosto, int x, int y, int lar, int alt, long id, int larFoto, int altFoto) {
    	this.rosto = rosto;
        this.x = x;
        this.y = y;
        this.lar = lar;
        this.alt = alt;
        this.id = id;
        this.larFoto = larFoto;
        this.altFoto = altFoto;
    }
    
    public int getRosto() {
    	return rosto;
    }
    public void setRosto(int rosto) {
    	this.rosto = rosto;
    }
    
    public int getX() {
    	return x;
    }
    public void setX(int x) {
    	this.x = x;
    }
    
    public int getY() {
    	return y;
    }
    public void setY(int y) {
    	this.y = y;
    }
    
    public int getLar() {
    	return lar;
    }
    
    public void setLar(int lar) {
    	this.lar = lar;
    }
    
    public int getAlt() {
    	return alt;
    }
    
    public void setAlt (int alt) {
    	this.alt = alt;
    }
    
    public long getId() {
    	return id;
    }
    
    public void setId(long id) {
    	this.id = id;
    }
    
    public int getLarFoto() {
    	return larFoto;
    }
    
    public void setLarFoto(int larFoto) {
    	this.larFoto = larFoto;
    }
    
    public int getAltFoto() {
    	return altFoto;
    }
    
    public void setAltFoto(int altFoto) {
    	this.altFoto = altFoto;
    }
    
    /*@Override
    public int hashCode() {
    	int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj == null) {
        	return false;
        }
        if (getClass() != obj.getClass()) {
        	return false;
        }
        final Car other = (Car) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
        	return false;
        }
        return true;
    }*/
}
