package gui;

import java.util.ArrayList;

import javax.swing.JTable;

public class Logros {
	private ArrayList<Logro> logroslist = new ArrayList<Logro>();
	private JTable logrosTable;
	private Logro logro;
	public void JFrameLogros(ArrayList<Logro> logroslist){
		this.logroslist = logroslist;
		
		initTable();
		
		
		
	}
	private void initTable() {
		this.logrosTable = new JTable(new LogroTablaModelo(this.logroslist));
		
	}

}
class Logro{
	private String nombre;
	private String definicion;
	private int dificultad;
	
	public Logro(String nombre, String definicion, int dificultad) {
		super();
		this.nombre = nombre;
		this.definicion = definicion;
		this.dificultad = dificultad;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDefinicion() {
		return definicion;
	}
	public void setDefinicion(String definicion) {
		this.definicion = definicion;
	}
	public int getDificultad() {
		return dificultad;
	}
	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}
	
}
