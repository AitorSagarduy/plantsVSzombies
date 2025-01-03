package gui;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.Planta;
import domain.Zombie;

public class Batalla extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	private HashMap<ArrayList<Integer>, Planta> mapaFinalPlantas;
	private HashMap<ArrayList<Integer>, Zombie> mapaFinalZombies;
	
	private boolean detener;
	public static void main(String[] args) {
		new Simulacionv1();
	}
	public Batalla(HashMap<ArrayList<Integer>, Planta> mapaFinal1, HashMap<ArrayList<Integer>, Zombie> mapaFinal2) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ventana de batalla");
		this.mapaFinalPlantas = mapaFinal1;
		this.mapaFinalZombies = mapaFinal2;
		setLayout(new GridLayout(5, 20));
		
		ArrayList<JButton> botones = new ArrayList<JButton>();
		for(int i = 0; i<100;i++) {
			int fila = i / 20; // 10 columnas por fila
		    int columna = i % 20;
			JButton botonCesped = new JButton();
			botonCesped.putClientProperty("fila", fila);
			botonCesped.putClientProperty("columna", columna);
			ArrayList<Integer> coordenadas = new ArrayList<Integer>();
		    coordenadas.add(fila);
		    coordenadas.add(columna);
		    
		    if(mapaFinalPlantas.get(coordenadas) instanceof Planta) {
		    	try {
					botonCesped.setIcon(new ImageIcon(Simulacionv1.getBuferedimagePlanta(mapaFinalPlantas.get(coordenadas)).getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
					botonCesped.putClientProperty("planta", mapaFinalPlantas.get(coordenadas));
		    	} catch (IOException e) {
					e.printStackTrace();
				}
		    } else if (mapaFinalZombies.get(coordenadas) instanceof Zombie) {
		    	try {
					botonCesped.setIcon(new ImageIcon(Simulacionv2.getBuferedimagePlanta(mapaFinalZombies.get(coordenadas)).getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
					botonCesped.putClientProperty("planta", mapaFinalZombies.get(coordenadas));
		    	} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    
			botonCesped.addActionListener(e -> System.out.println(botonCesped.getClientProperty("fila")+","+ botonCesped.getClientProperty("columna")));
			
			botones.add(botonCesped);
			add(botonCesped);
		}
		
		
		
		pack();
		setVisible(true);
		setSize(Ajustes.resolucionx(),Ajustes.resoluciony());
		setLocationRelativeTo(null);
		setResizable(false);
		
		Thread hilo = new Thread();
		detener = false;
		hilo =  new Thread(() -> {
			while(!detener) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i = 0; i<100;i++) {
					JButton boton = botones.get(i);
					if(boton.getClientProperty("planta") instanceof Zombie) {
						if((int)(boton.getClientProperty("columna")) == 0) {
							detener = true;
							System.out.format("El zombie %s gana", ((Zombie)boton.getClientProperty("planta")).getTipo());
						}else if(botones.get(i-1).getClientProperty("planta") instanceof Planta) {
							((Planta)botones.get(i-1).getClientProperty("planta")).setVida(((Planta)botones.get(i-1).getClientProperty("planta")).getVida()-((Zombie)boton.getClientProperty("planta")).getDanyo());
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(((Planta)botones.get(i-1).getClientProperty("planta")).getVida()<=0) {
								botones.get(i-1).setIcon(null);
								botones.get(i-1).putClientProperty("planta", null);
							}
						}else{
							botones.get(i-1).setIcon(boton.getIcon());
							boton.setIcon(null);
							botones.get(i-1).putClientProperty("planta", boton.getClientProperty("planta"));
							boton.putClientProperty("planta", null);

						}
					}
				}
				
			}
		});
		Thread hilo1 = new Thread(() -> {
			while (!detener) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < 100; i++) {
					JButton boton = botones.get(i);
					if (boton.getClientProperty("planta") instanceof Planta) {
						int numeroZomb = 0;
						for (int j = 0; j < 100; j++) {
							if (botones.get(j).getClientProperty("planta") instanceof Zombie) {
								numeroZomb++;
							}
						}
						if (numeroZomb == 0){
							detener = true;
							System.out.println("Las plantas ganan");
						}else{
							for(int u = i ; u%20 <= 19;u++ ){
								if(botones.get(u).getClientProperty("planta") instanceof Zombie) {
									System.out.println("Planta ataca a zombie"+u+i);	
									try {
										((Zombie)botones.get(u).getClientProperty("planta")).setVida(((Zombie)botones.get(u).getClientProperty("planta")).getVida()-((Planta)boton.getClientProperty("planta")).getDanyo());

									} catch (Exception e) {
										// TODO Auto-generated catch block
										((Zombie)botones.get(u-1).getClientProperty("planta")).setVida(((Zombie)botones.get(u-1).getClientProperty("planta")).getVida()-((Planta)boton.getClientProperty("planta")).getDanyo());
									}
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									try {
										if(((Zombie)botones.get(u).getClientProperty("planta")).getVida()<=0) {
											botones.get(u).setIcon(null);
											botones.get(u).putClientProperty("planta", null);
										}
									} catch (Exception e) {
										// TODO: handle exception
										if(((Zombie)botones.get(u-1).getClientProperty("planta")).getVida()<=0) {
											botones.get(u-1).setIcon(null);
											botones.get(u-1).putClientProperty("planta", null);
										}
									}
									break;
								}
							}
						}

					}
				}
			}
		});
		hilo1.start();
		hilo.start();
	}
	@Override
	public void run() {
		SwingUtilities.invokeLater(()->new Simulacionv1());
		
	}

}