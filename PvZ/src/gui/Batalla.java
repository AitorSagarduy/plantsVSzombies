package gui;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.attribute.AclEntry;
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
		
		detener = false;
		ArrayList<Thread> hilosZombies = new ArrayList<Thread>();
		ArrayList<Thread> hilosPlantas = new ArrayList<Thread>();
		for(int i = 0; i<100;i++) {
			if (botones.get(i).getClientProperty("planta") instanceof Zombie) {
				Thread hilo = new Thread();
				Zombie zombie = (Zombie) botones.get(i).getClientProperty("planta");
				int finalII = i;
				hilo = new Thread(() -> {
					int finalI = finalII;
					while (!detener) {
						if(finalI%20 == 0) {
							detener = true;
							System.out.println("Los zombies ganan");
						}
                        try {
                            Thread.sleep(zombie.getVelocidad()*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(botones.get(finalI-1).getClientProperty("planta") instanceof Planta) {
                        	Planta planta = (Planta) botones.get(finalI-1).getClientProperty("planta");
                        	planta.setVida(planta.getVida()-zombie.getDanyo());
							if (planta.getVida() <= 0) {
								botones.get(finalI-1).setIcon(null);
								botones.get(finalI-1).putClientProperty("planta", null);
								botones.get(finalI-1).setIcon(botones.get(finalI).getIcon());
								botones.get(finalI-1).putClientProperty("planta", zombie);
								botones.get(finalI).setIcon(null);
								botones.get(finalI).putClientProperty("planta", null);
								finalI--;
							}
                        } else if (botones.get(finalI-1).getClientProperty("planta") instanceof Zombie) {
                        	try {
								hilo.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        } else {
                        	botones.get(finalI-1).setIcon(botones.get(finalI).getIcon());
                        	botones.get(finalI-1).putClientProperty("planta", zombie);
                        	botones.get(finalI).setIcon(null);
                        	botones.get(finalI).putClientProperty("planta", null);
                        	finalI--;
                        }
                        
                    }
				});
				hilosZombies.add(hilo);
			}
		}
		for (int i = 0; i < 100; i++) {
			if (botones.get(i).getClientProperty("planta") instanceof Planta) {
				Thread hilo = new Thread();
				Planta planta = (Planta) botones.get(i).getClientProperty("planta");
				int finalII = i;
				hilo = new Thread(() -> {
					int finalI = finalII;
					while (!detener) {
						try {
							Thread.sleep(planta.getTmp_atac() * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						int numeroZomb = 0;
						for (int j = 0; j < 100; j++) {
							if (botones.get(j).getClientProperty("planta") instanceof Zombie) {
								numeroZomb++;
							}
						}
						if (numeroZomb <= 0){
							detener = true;
							System.out.println("Las plantas ganan");
						}else{
							for(int u = finalI ; u%20 <= 19;u++ ){
								if(botones.get(u).getClientProperty("planta") instanceof Zombie) {
									System.out.println("Planta ataca a zombie en la columna: "+u%20);	
									Zombie zombie = (Zombie) botones.get(u).getClientProperty("planta");
									try {
										zombie.setVida(zombie.getVida()-planta.getDanyo());
									} catch (Exception e) {
										try {
											((Zombie)botones.get(u-1).getClientProperty("planta")).setVida(((Zombie)botones.get(u-1).getClientProperty("planta")).getVida()-planta.getDanyo());
                                            
                                        } catch (Exception e2) {
                                        	
                                        }
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
				});
				hilosPlantas.add(hilo);
			}
		}
		for (Thread thread : hilosZombies) {
			thread.start();
		}
		for (Thread thread : hilosPlantas) {
			thread.start();
		}
	}
	@Override
	public void run() {
		SwingUtilities.invokeLater(()->new Simulacionv1());
		
	}

}