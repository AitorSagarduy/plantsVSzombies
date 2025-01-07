package gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.Planta;
import domain.Zombie;

public class Batalla extends JFrame{
	private static final long serialVersionUID = 1L;
	private HashMap<ArrayList<Integer>, Planta> mapaFinalPlantas;
	private HashMap<ArrayList<Integer>, Zombie> mapaFinalZombies;
	
	private static boolean detener;
	
	public class BackgroundPanel extends JPanel {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image backgroundImage;

	    public BackgroundPanel(String filePath) {
	        try {
	            backgroundImage = ImageIO.read(new File(filePath));
	        } catch (IOException e) {
	        }
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (backgroundImage != null) {
	            int width = getWidth();
	            int height = getHeight();
	            g.drawImage(backgroundImage, 0, 0, width, height, this);
	        }
	    }
	}
	
	public Batalla(HashMap<ArrayList<Integer>, Planta> mapaFinal1, HashMap<ArrayList<Integer>, Zombie> mapaFinal2) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ventana de batalla");
		this.mapaFinalPlantas = mapaFinal1;
		this.mapaFinalZombies = mapaFinal2;
		
		BackgroundPanel backgroundPanel = new BackgroundPanel("src/imagenes/PvZ-Patio3.JPG");
        backgroundPanel.setLayout(new GridLayout(5, 20));
        setContentPane(backgroundPanel);
		//setLayout(new GridLayout(5, 20));
		
		
		ArrayList<JButton> botones = new ArrayList<JButton>();
		for(int i = 0; i<100;i++) {
			int fila = i / 20; // 10 columnas por fila
		    int columna = i % 20;
			JButton botonCesped = new JButton();
			botonCesped.putClientProperty("fila", fila);
			botonCesped.putClientProperty("columna", columna);
			botonCesped.setBorder(BorderFactory.createEmptyBorder());
			botonCesped.setFocusPainted(false);
			botonCesped.setContentAreaFilled(false);
			botonCesped.setOpaque(false);
			ArrayList<Integer> coordenadas = new ArrayList<Integer>();
		    coordenadas.add(fila);
		    coordenadas.add(columna);
		    
		    if(mapaFinalPlantas.get(coordenadas) instanceof Planta) {
		    	try {
					botonCesped.setIcon(new ImageIcon(Simulacionv1.getBuferedimagePlanta(mapaFinalPlantas.get(coordenadas)).getScaledInstance(Ajustes.resolucionx()/20, Ajustes.resoluciony()/5, Image.SCALE_SMOOTH)));
					botonCesped.putClientProperty("planta", mapaFinalPlantas.get(coordenadas));
		    	} catch (IOException e) {
					e.printStackTrace();
				}
		    } else if (mapaFinalZombies.get(coordenadas) instanceof Zombie) {
		    	try {
					botonCesped.setIcon(new ImageIcon(Simulacionv2.getBuferedimagePlanta(mapaFinalZombies.get(coordenadas)).getScaledInstance(Ajustes.resolucionx()/20, Ajustes.resoluciony()/5, Image.SCALE_SMOOTH)));
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
					while (!detener && zombie.getVida() > 0) {
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
							System.out.println ("Zombie ataca");
							try {
								Thread.sleep(zombie.getTmp_atac()*1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
                        } else if (botones.get(finalI-1).getClientProperty("planta") instanceof Zombie) {
                        	try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
                        } else {
                        	botones.get(finalI-1).setIcon(botones.get(finalI).getIcon());
                        	botones.get(finalI-1).putClientProperty("planta", zombie);
                        	botones.get(finalI).setIcon(null);
                        	botones.get(finalI).putClientProperty("planta", null);
                        	finalI--;
                        	try {
                                Thread.sleep((zombie.getVelocidad())*1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if(finalI%20 == 0) {
							detener = true;
							System.out.println("Los zombies ganan");
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
							for(int u = finalI ; u%19 <= finalI%20+planta.getRango();u++ ){
								if(botones.get(u).getClientProperty("planta") instanceof Zombie) {
									System.out.println("Planta ataca a zombie en la columna: "+u%20);	
									Zombie zombie = (Zombie) botones.get(u).getClientProperty("planta");
									try {
										zombie.setVida(zombie.getVida()-planta.getDanyo());
									} catch (Exception e) {
										try {
											((Zombie)botones.get(u-1).getClientProperty("planta")).setVida(((Zombie)botones.get(u-1).getClientProperty("planta")).getVida()-planta.getDanyo());
                                            
                                        } catch (Exception e2) {
                                        	//aqui solamente llega si es que la planta muere en el instante de atacar
                                        }
									}
									try {
										if(((Zombie)botones.get(u).getClientProperty("planta")).getVida()<=0) {
											botones.get(u).setIcon(null);
											botones.get(u).putClientProperty("planta", null);
										}
									} catch (Exception e) {
										if(botones.get(u).getClientProperty("planta") instanceof Zombie) {
											if(((Zombie)botones.get(u-1).getClientProperty("planta")).getVida()<=0) {
												botones.get(u-1).setIcon(null);
												botones.get(u-1).putClientProperty("planta", null);
											}
										}
									}
									try {
										Thread.sleep(planta.getTmp_atac() * 1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
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
		Thread hilo = new Thread();
		hilo = new Thread(() -> {
			if (detener) {
                for (Thread thread : hilosZombies) {
                    thread.interrupt();
                }
                for (Thread thread : hilosPlantas) {
                    thread.interrupt();
                }
            }
		});
		for (Thread thread : hilosZombies) {
			thread.start();
		}
		for (Thread thread : hilosPlantas) {
			thread.start();
		}
		hilo.start();
	}
	

}