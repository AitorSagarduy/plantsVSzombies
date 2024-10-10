package main;

public class Planta {
	
	protected String tipo;
	protected String nombre;
	protected int vida;
	protected int tmp_atac;
	protected int danyo;
	protected int rango;
	protected int nivel;
	
	public Planta(String tipo, String nombre, int vida, int tmp_atac, int danyo, int rango, int nivel) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.vida = vida;
		this.tmp_atac = tmp_atac;
		this.danyo = danyo;
		this.rango = rango;
		this.nivel = nivel;
	}

	// TAL VEZ QUITAR RANGO Y DEMAS COSAS...
	//
	//
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getTmp_atac() {
		return tmp_atac;
	}

	public void setTmp_atac(int tmp_atac) {
		this.tmp_atac = tmp_atac;
	}

	public int getDanyo() {
		return danyo;
	}

	public void setDanyo(int danyo) {
		this.danyo = danyo;
	}

	public int getRango() {
		return rango;
	}

	public void setRango(int rango) {
		this.rango = rango;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	@Override
	public String toString() {
		return "Plantas [tipo=" + tipo + ", nombre=" + nombre + ", vida=" + vida + ", tmp_atac=" + tmp_atac + ", danyo="
				+ danyo + ", rango=" + rango + ", nivel=" + nivel + "]";
	}
	
}

