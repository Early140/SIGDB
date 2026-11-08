package ar.edu.sigdb.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import ar.edu.sigdb.model.Jugador;
public interface JugadorDAO {
 long crear(Jugador jugador) throws SQLException;
 ArrayList<Jugador> listar() throws SQLException;
 boolean actualizar(long id,String nombre,String apellido) throws SQLException;
}
