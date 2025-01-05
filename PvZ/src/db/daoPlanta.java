package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import domain.Planta;

public class daoPlanta {
	Conexion cx;
	
	public daoPlanta() {
		cx= new Conexion();
	}
	
	public boolean insertarPlanta(Planta planta) {
		PreparedStatement ps=null;
		try {
			ps=cx.conectar().prepareStatement("INSERT INTO planta VALUES(?,?,?,?,?,?,?)");
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

}
