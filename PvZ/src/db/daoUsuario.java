package db;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Planta;
import domain.Zombie;
public class daoUsuario {
	Conexion cx;
	
	public daoUsuario() {
		cx= new Conexion();
	}
	public boolean insertarPlanta(Planta planta) {
		PreparedStatement ps=null;
		try {
			ps=cx.conectar().prepareStatement("INSERT INTO planta VALUES(null,null,?,?,?,?,?)");
			ps.setString(1, planta.getTipo());
			ps.setString(2, planta.getNombre());
			ps.setInt(3, planta.getVida());
			ps.setInt(4, planta.getTmp_atac());
			ps.setInt(5, planta.getDanyo());
			ps.setInt(6, planta.getRango());
			ps.setInt(7, planta.getNivel());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	public boolean insertarZombie(Zombie zombie) {
		PreparedStatement ps=null;
		try {
			ps=cx.conectar().prepareStatement("INSERT INTO planta VALUES(null,null,?,?,?,?,?)");
			ps.setString(1, zombie.getTipo());
			ps.setString(2, zombie.getNombre());
			ps.setInt(3, zombie.getVida());
			ps.setInt(4, zombie.getTmp_atac());
			ps.setInt(5, zombie.getDanyo());
			ps.setInt(6, zombie.getVelocidad());
			ps.setInt(7, zombie.getNivel());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
