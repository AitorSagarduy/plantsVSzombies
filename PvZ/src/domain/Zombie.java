package domain;

public class Zombie {
	
	protected String tipo;
	protected String nombre;
	protected int vida;
	protected int tmp_atac;
	protected int danyo;
	protected int velocidad;
	protected int nivel;
	protected boolean colocado;
	
	public Zombie(String tipo, String nombre, int vida, int tmp_atac, int danyo, int velocidad, int nivel) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.vida = vida;
		this.tmp_atac = tmp_atac;
		this.danyo = danyo;
		this.velocidad = velocidad;
		this.nivel = nivel;
	}

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

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public boolean isColocado() {
		return colocado;
	}

	public void setColocado(boolean colocado) {
		this.colocado = colocado;
	}

	@Override
	public String toString() {
		return "Zombie [tipo=" + tipo + ", nombre=" + nombre + ", vida=" + vida + ", tmp_atac=" + tmp_atac + ", danyo="
				+ danyo + ", velocidad=" + velocidad + ", nivel=" + nivel + ", colocado=" + colocado + "]";
	}
	
}
