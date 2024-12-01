package db;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import gui.Planta;
public class daoUsuario {
	Conexion cx;
	
	public daoUsuario() {
		cx= new Conexion();
	}
	public boolean insertarPlanta(Planta planta) {
		PreparedStatement ps=null;
		try {
			ps=cx.conectar().prepareStatement("INSERT INTO planta VALUES(null,null,?,?,?,?)");
			ps.setString(1, planta.getTipo());
			ps.setString(2, planta.getNombre());
			ps.setInt(3, planta.getVida());
			ps.setInt(4, planta.getTmp_atac());
			ps.setInt(5, planta.getDanyo());
			ps.setInt(6, planta.getNivel());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}

}
