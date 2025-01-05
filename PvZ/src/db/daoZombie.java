package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Zombie;

public class daoZombie {
	Conexion cx;
	
	public daoZombie() {
		cx= new Conexion();
	}
	
	public boolean insertarZombie(Zombie zombie) {
		
		PreparedStatement ps=null;
		try {
			ps=cx.conectar().prepareStatement("INSERT INTO zombie VALUES(?,?,?,?,?,?,?)");
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
