package gui;

import java.awt.Component;

public class SimulacionZ extends Simulacionv1{
	public static void main(String[] args) {
		SimulacionZ ventana = new SimulacionZ();
	}
	public SimulacionZ() {
		for(Component componente : this.getComponents()) {
			System.out.println(componente);
		}
	}
}
