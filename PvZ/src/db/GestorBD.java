package db;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import Main.Main;
import domain.Planta;
import domain.Zombie;
import gui.CargarZombies;
import gui.MenuPlantas;

public class GestorBD {
	private final String PROPERTIES_FILE = "src/db/app.properties";
	private final String CSV_PLANTAS = "src/DatosCsv/plantas.csv";
	private final String CSV_ZOMBIES = "src/DatosCsv/zombies.csv";
	private final String CSV_PLANTAS_TIENDA = "src/DatosCsv/plantasTienda.csv";
	private final String CSV_ZOMBIES_TIENDA = "src/DatosCsv/zombiesTienda.csv";
	
	private Properties properties;
	private String driverName;
	private String databaseFile;
	private String connectionString;
	
	public GestorBD() {
		try {
			//Inicialización del Logger
			
			//Lectura del fichero properties
			properties = new Properties();
			properties.load(new FileReader(PROPERTIES_FILE));
			
			driverName = properties.getProperty("driver");
			databaseFile = properties.getProperty("file");
			connectionString = properties.getProperty("connection");
			
			//Cargar el diver SQLite
			Class.forName(driverName);
		} catch (Exception ex) { //ehhh
		}
	}
	public void initilizeFromCSV() {
		System.out.println("lol");
		//Sólo se inicializa la BBDD si la propiedad initBBDD es true.
		if (properties.get("loadCSV").equals("true")) {
			System.out.println("xD lol");
			//Se borran los datos, si existía alguno
			this.borrarDatos();
			this.borrarDatosTienda();
			ArrayList<Planta> plantas = new ArrayList<Planta>();
			MenuPlantas.cargarPlantasCSV(plantas, CSV_PLANTAS);
			this.insertarPlanta(plantas.toArray(new Planta[plantas.size()]));
			
			ArrayList<Planta> plantasTienda = new ArrayList<Planta>();
			MenuPlantas.cargarPlantasCSV(plantasTienda, CSV_PLANTAS_TIENDA);
			this.insertarPlantaTienda(plantasTienda.toArray(new Planta[plantasTienda.size()]));
			
			//Se leen los zombies del CSV
			ArrayList<Zombie> zombies = new ArrayList<Zombie>();
			CargarZombies.cargarZombiesCSV(zombies, CSV_ZOMBIES);
			this.insertarZombie(zombies.toArray(new Zombie[zombies.size()]));
			
			//Se leen los zombies del CSV
			ArrayList<Zombie> zombiesTienda = new ArrayList<Zombie>();
			CargarZombies.cargarZombiesCSV(zombiesTienda, CSV_ZOMBIES_TIENDA);
			this.insertarZombieTienda(zombiesTienda.toArray(new Zombie[zombiesTienda.size()]));
		}
	}
	private void insertarZombie(Zombie[] arrayz) {
		//Se define la plantilla de la sentencia SQL
		String sql = "INSERT INTO Zombies (Tipo, Nombre, Vida, Tmp_atac, Danyo, Velocidad, Nivel) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
									
			//Se recorren los clientes y se insertan uno a uno
			for (Zombie zombie : arrayz) {
				//Se añaden los parámetros al PreparedStatement
				pStmt.setString(1, zombie.getTipo());
				pStmt.setString(2, zombie.getNombre());
				pStmt.setInt(3, zombie.getVida());
				pStmt.setInt(4, zombie.getTmp_atac());
				pStmt.setInt(5, zombie.getDanyo());
				pStmt.setInt(6, zombie.getVelocidad());
				pStmt.setInt(7, zombie.getNivel());
				pStmt.executeUpdate();
			}
			
		} catch (Exception ex) {
			System.err.println("Fallo en insertarZombie: ");
			ex.printStackTrace();
		}
		
	}
	private void insertarZombieTienda(Zombie[] arrayz) {
		//Se define la plantilla de la sentencia SQL
		String sql = "INSERT INTO ZombiesTienda (Tipo, Nombre, Vida, Tmp_atac, Danyo, Velocidad, Nivel) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
									
			//Se recorren los clientes y se insertan uno a uno
			for (Zombie zombie : arrayz) {
				//Se añaden los parámetros al PreparedStatement
				pStmt.setString(1, zombie.getTipo());
				pStmt.setString(2, zombie.getNombre());
				pStmt.setInt(3, zombie.getVida());
				pStmt.setInt(4, zombie.getTmp_atac());
				pStmt.setInt(5, zombie.getDanyo());
				pStmt.setInt(6, zombie.getVelocidad());
				pStmt.setInt(7, zombie.getNivel());
				pStmt.executeUpdate();
			}
			
		} catch (Exception ex) {
			System.err.println("Fallo en insertarZombie: ");
			ex.printStackTrace();
		}
		
	}
	private void insertarPlanta(Planta[] arrayp) {
		//Se define la plantilla de la sentencia SQL
				String sql = ";INSERT INTO Plantas (Tipo, Nombre, Vida, Tmp_atac, Danyo, Rango, Nivel) VALUES (?, ?, ?, ?, ?, ?, ?);";
				
				//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
				try (Connection con = DriverManager.getConnection(connectionString);
					 PreparedStatement pStmt = con.prepareStatement(sql)) {
											
					//Se recorren los clientes y se insertan uno a uno
					for (Planta planta : arrayp) {
						//Se añaden los parámetros al PreparedStatement
						pStmt.setString(1, planta.getTipo());
						pStmt.setString(2, planta.getNombre());
						pStmt.setInt(3, planta.getVida());
						pStmt.setInt(4, planta.getTmp_atac());
						pStmt.setInt(5, planta.getDanyo());
						pStmt.setInt(6, planta.getRango());
						pStmt.setInt(7, planta.getNivel());
						pStmt.executeUpdate();
						System.out.println("exito");
					}
					
				} catch (Exception ex) {
					System.err.println("exito " + ex);
					
				}
		
	}
	private void insertarPlantaTienda(Planta[] arrayp) {
		//Se define la plantilla de la sentencia SQL
				String sql = "INSERT INTO PlantasTienda (Tipo, Nombre, Vida, Tmp_atac, Danyo, Rango, Nivel) VALUES (?, ?, ?, ?, ?, ?, ?);";
				
				//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
				try (Connection con = DriverManager.getConnection(connectionString);
					 PreparedStatement pStmt = con.prepareStatement(sql)) {
											
					//Se recorren los clientes y se insertan uno a uno
					for (Planta planta : arrayp) {
						//Se añaden los parámetros al PreparedStatement
						pStmt.setString(1, planta.getTipo());
						pStmt.setString(2, planta.getNombre());
						pStmt.setInt(3, planta.getVida());
						pStmt.setInt(4, planta.getTmp_atac());
						pStmt.setInt(5, planta.getDanyo());
						pStmt.setInt(6, planta.getRango());
						pStmt.setInt(7, planta.getNivel());
						pStmt.executeUpdate();
						System.out.println("exito");
					}
					
				} catch (Exception ex) {
					System.err.println("exito " + ex);
					
				}
		
	}
	public void borrarDatos() {
		//Sólo se borran los datos si la propiedad cleanBBDD es true
		if (properties.get("cleanBBDD").equals("true")) {	
			String sql1 = "DELETE FROM Plantas;";
			String sql2 = "DELETE FROM Zombies;";
			
	        //Se abre la conexión y se crea un PreparedStatement para borrar los datos de cada tabla
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2)) {
				
				//Se ejecutan las sentencias de borrado de las tablas
		        if (!pStmt1.execute() && !pStmt2.execute()) {
		        }
			} catch (Exception ex) {
				System.err.println("Error: " + ex);
			}
		}
		
	}
	public void borrarDatosTienda() {
		//Sólo se borran los datos si la propiedad cleanBBDD es true
		if (properties.get("cleanBBDDtienda").equals("true")) {	
			String sql1 = "DELETE FROM PlantasTienda;";
			String sql2 = "DELETE FROM ZombiesTienda;";
			
	        //Se abre la conexión y se crea un PreparedStatement para borrar los datos de cada tabla
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2)) {
				
				//Se ejecutan las sentencias de borrado de las tablas
		        if (!pStmt1.execute() && !pStmt2.execute()) {
		        }
			} catch (Exception ex) {
				System.err.println("Error: " + ex);
			}
		}
		
	}
	public ArrayList<Planta> getPlantas() {
		ArrayList<Planta> plantas = new ArrayList<>();
		String sql = "SELECT * FROM Plantas";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet
			ResultSet rs = pStmt.executeQuery();			
			Planta planta;
			
			//Se recorre el ResultSet y se crean objetos
			while (rs.next()) {
				planta = new Planta(rs.getString("Tipo"), 
						rs.getString("Nombre"), 
						rs.getInt("Vida"), 
						rs.getInt("Tmp_atac"),
						rs.getInt("Danyo"),
						rs.getInt("Rango"),
						rs.getInt("Nivel"));
				//Se inserta cada nuevo cliente en la lista de clientes
				plantas.add(planta);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.println("lo lograste chacalillo");
		} catch (Exception ex) {
			System.out.println("f, chacalillo");
		}		
		
		return plantas;
	}
	public ArrayList<Planta> getPlantasTienda() {
		ArrayList<Planta> plantas = new ArrayList<>();
		String sql = "SELECT * FROM PlantasTienda";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet
			ResultSet rs = pStmt.executeQuery();			
			Planta planta;
			
			//Se recorre el ResultSet y se crean objetos
			while (rs.next()) {
				planta = new Planta(rs.getString("Tipo"), 
						rs.getString("Nombre"), 
						rs.getInt("Vida"), 
						rs.getInt("Tmp_atac"),
						rs.getInt("Danyo"),
						rs.getInt("Rango"),
						rs.getInt("Nivel"));
				//Se inserta cada nuevo cliente en la lista de clientes
				plantas.add(planta);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.println("lo lograste chacalillo");
		} catch (Exception ex) {
			System.out.println("f, chacalillo");
		}		
		
		return plantas;
	}
	public ArrayList<Zombie> getZombies() {
		ArrayList<Zombie> zombies = new ArrayList<>();
		String sql = "SELECT * FROM Zombies";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet
			ResultSet rs = pStmt.executeQuery();			
			Zombie zombie;
			
			//Se recorre el ResultSet y se crean objetos
			while (rs.next()) {
				zombie = new Zombie(rs.getString("Tipo"), 
						rs.getString("Nombre"), 
						rs.getInt("Vida"), 
						rs.getInt("Tmp_atac"),
						rs.getInt("Danyo"),
						rs.getInt("Velocidad"),
						rs.getInt("Nivel"));
				//Se inserta cada nuevo cliente en la lista de clientes
				zombies.add(zombie);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.println("lo lograste chacalillo");
		} catch (Exception ex) {
			System.out.println("f, chacalillo");
		}		
		
		return zombies;
	}
	public ArrayList<Zombie> getZombiesTienda() {
		ArrayList<Zombie> zombies = new ArrayList<>();
		String sql = "SELECT * FROM ZombiesTienda";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt = con.prepareStatement(sql)) {			
			
			//Se ejecuta la sentencia y se obtiene el ResultSet
			ResultSet rs = pStmt.executeQuery();			
			Zombie zombie;
			
			//Se recorre el ResultSet y se crean objetos
			while (rs.next()) {
				zombie = new Zombie(rs.getString("Tipo"), 
						rs.getString("Nombre"), 
						rs.getInt("Vida"), 
						rs.getInt("Tmp_atac"),
						rs.getInt("Danyo"),
						rs.getInt("Velocidad"),
						rs.getInt("Nivel"));
				//Se inserta cada nuevo cliente en la lista de clientes
				zombies.add(zombie);
			}
			
			//Se cierra el ResultSet
			rs.close();
			
			System.out.println("lo lograste chacalillo");
		} catch (Exception ex) {
			System.out.println("f, chacalillo");
		}		
		
		return zombies;
	}
	public void borrarPlanta(String nombre) {
		String sql = "DELETE FROM Plantas WHERE Nombre = ? ;";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
				
			//Se añaden los parámetros al PreparedStatement
			pStmt.setString(1, nombre);
			if (pStmt.executeUpdate() != 1) {					
				System.out.println("epico hermano");
			} else {
				System.out.println("no tan epico hermano");
			}
		} catch (Exception ex) {
			System.out.println("vaya mierdon hermano");
		}				
	}
	public void borrarPlantaTienda(String nombre) {
		String sql = "DELETE FROM PlantasTienda WHERE Nombre = ? ;";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
				
			//Se añaden los parámetros al PreparedStatement
			pStmt.setString(1, nombre);
			if (pStmt.executeUpdate() != 1) {					
				System.out.println("epico hermano");
			} else {
				System.out.println("no tan epico hermano");
			}
		} catch (Exception ex) {
			System.out.println("vaya mierdon hermano");
		}				
	}
	
	public void borrarZombie(String nombre) {
		String sql = "DELETE FROM Zombies WHERE Nombre = ? ;";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
				
			//Se añaden los parámetros al PreparedStatement
			pStmt.setString(1, nombre);
			if (pStmt.executeUpdate() != 1) {					
				System.out.println("epico hermano");
			} else {
				System.out.println("no tan epico hermano");
			}
		} catch (Exception ex) {
			System.out.println("vaya mierdon hermano");
		}				
	}
	public void borrarZombieTienda(String nombre) {
		String sql = "DELETE FROM ZombiesTienda WHERE Nombre = ? ;";
		
		//Se abre la conexión y se crea el PreparedStatement con la sentencia SQL
		try (Connection con = DriverManager.getConnection(connectionString);
			 PreparedStatement pStmt = con.prepareStatement(sql)) {
				
			//Se añaden los parámetros al PreparedStatement
			pStmt.setString(1, nombre);
			if (pStmt.executeUpdate() != 1) {					
				System.out.println("epico hermano");
			} else {
				System.out.println("no tan epico hermano");
			}
		} catch (Exception ex) {
			System.out.println("vaya mierdon hermano");
		}				
	}
	public ArrayList<Planta> Level_updater_P(String nombre, 
			int vida, int tmp_atac, int danyo, int rango, int nivel) throws SQLException{
		String r2d2 = nombre;
		String sql2 = "UPDATE Plantas "
	            + "SET Vida = ?, Tmp_atac = ?, Danyo = ?, Rango = ?, Nivel = ? "
	            + "WHERE Nombre = ?;";
		ArrayList<Planta> listaP = getPlantas();
		System.out.println(listaP.size() - 1);
		System.out.println(listaP);
		rp_recurs(listaP, listaP.size() - 1, r2d2, vida, tmp_atac, danyo, rango, nivel, sql2);
				return listaP;
		
	}
	public ArrayList<Zombie> Level_updater_Z(String nombre, 
			int vida, int tmp_atac, int danyo, int velocidad, int nivel) throws SQLException{
		String r2d2 = nombre;
		String sql2 = "UPDATE Zombies "
	            + "SET Vida = ?, Tmp_atac = ?, Danyo = ?, Velocidad = ?, Nivel = ? "
	            + "WHERE Nombre = ?;";
		ArrayList<Zombie> listaZ = getZombies();
		System.out.println(listaZ.size() - 1);
		System.out.println(listaZ);
		rz_recurs(listaZ, listaZ.size() - 1, r2d2, vida, tmp_atac, danyo, velocidad, nivel, sql2);
		return listaZ;
		
	}
	
	public void rz_recurs(ArrayList<Zombie> listaZ, int i, String nombre,
			int vida, int tmp_atac, int danyo, int velocidad, int nivel, String sql2) throws SQLException {
		if(listaZ.get(i).getNombre().equals(nombre)) {
			System.out.println("1");
			listaZ.get(i).setVida(vida);
			listaZ.get(i).setTmp_atac(tmp_atac);
			listaZ.get(i).setDanyo(danyo);
			listaZ.get(i).setVelocidad(velocidad);
			listaZ.get(i).setNivel(nivel);
			
			try (Connection con2 = DriverManager.getConnection(connectionString);
					 PreparedStatement pStmt2 = con2.prepareStatement(sql2)) {
				System.out.println("3");
						pStmt2.setString(6, listaZ.get(i).getNombre());
						pStmt2.setInt(1, listaZ.get(i).getVida());
						pStmt2.setInt(2, listaZ.get(i).getTmp_atac());
						pStmt2.setInt(3, listaZ.get(i).getDanyo());
						pStmt2.setInt(4, listaZ.get(i).getVelocidad());
						pStmt2.setInt(5, listaZ.get(i).getNivel());
						pStmt2.executeUpdate();
						System.out.println("exito");
					
					
				} catch (Exception ex) {
					System.err.println("exito " + ex);
					
				}
				
		}else {
			i--;
			rz_recurs(listaZ, i, nombre, vida, tmp_atac, danyo, velocidad, nivel, sql2);
		}
	}
	public void rp_recurs(ArrayList<Planta> listaP, int i, String nombre,
			int vida, int tmp_atac, int danyo, int rango, int nivel, String sql2) throws SQLException {
		if(listaP.get(i).getNombre().equals(nombre)) {
			System.out.println("1");
			listaP.get(i).setVida(vida);
			listaP.get(i).setTmp_atac(tmp_atac);
			listaP.get(i).setDanyo(danyo);
			listaP.get(i).setRango(rango);
			listaP.get(i).setNivel(nivel);
			try (Connection con2 = DriverManager.getConnection(connectionString);
					 PreparedStatement pStmt2 = con2.prepareStatement(sql2)) {
				System.out.println("3");
						pStmt2.setString(6, listaP.get(i).getNombre());
						pStmt2.setInt(1, listaP.get(i).getVida());
						pStmt2.setInt(2, listaP.get(i).getTmp_atac());
						pStmt2.setInt(3, listaP.get(i).getDanyo());
						pStmt2.setInt(4, listaP.get(i).getRango());
						pStmt2.setInt(5, listaP.get(i).getNivel());
						pStmt2.executeUpdate();
						System.out.println("exito");
					
					
				} catch (Exception ex) {
					System.err.println("exito " + ex);
					
				}
				
		}else {
			i--;
			rp_recurs(listaP, i, nombre, vida, tmp_atac, danyo, rango, nivel, sql2);
		}
	}
	public void getCoins() {
		String sql = "SELECT * FROM Monedas";
		try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt = con.prepareStatement(sql)) {			
				
				//Se ejecuta la sentencia y se obtiene el ResultSet
				ResultSet rs = pStmt.executeQuery();			
				
				
				//Se recorre el ResultSet y se crean objetos
				while (rs.next()) {
							Main.solespublic =rs.getInt("Soles");
							Main.cerebrospublic=rs.getInt("Cerebros");
				}
				
				//Se cierra el ResultSet
				rs.close();
				
				System.out.println("lo lograste chacalillo");
			} catch (Exception ex) {
				System.out.println("f, chacalillo");
			}		
		
	}
	public void updateCoins(int soles, int cerebros) throws SQLException {
		String sql1 = "DELETE FROM Monedas ;";
		String sql2 ="INSERT INTO Monedas (Soles, Cerebros) VALUES (?, ?);";
		try(Connection con1 = DriverManager.getConnection(connectionString);
				PreparedStatement pStmt1 = con1.prepareStatement(sql1)){
			pStmt1.executeUpdate();
		}
		try (Connection con2 = DriverManager.getConnection(connectionString);
				 PreparedStatement pStmt2 = con2.prepareStatement(sql2)) {
			System.out.println("3");
					pStmt2.setInt(1, soles);
					pStmt2.setInt(2, cerebros);
					pStmt2.executeUpdate();
					System.out.println("exito");
				
			} catch (Exception ex) {
				System.err.println("exito " + ex);
				
			}
		
		
		
	}
	}

	
	

