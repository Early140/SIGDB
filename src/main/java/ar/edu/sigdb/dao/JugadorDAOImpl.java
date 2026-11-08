package ar.edu.sigdb.dao;
import java.sql.*;
import java.util.ArrayList;
import ar.edu.sigdb.db.Conexion;
import ar.edu.sigdb.model.Jugador;
public class JugadorDAOImpl implements JugadorDAO {
 public long crear(Jugador j) throws SQLException {
  try(Connection c=Conexion.abrir(); PreparedStatement p=c.prepareStatement("INSERT INTO jugador(nombre,apellido,fecha_nacimiento) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS)){
   p.setString(1,j.getNombre());p.setString(2,j.getApellido());p.setDate(3,Date.valueOf(j.getNacimiento()));p.executeUpdate();
   try(ResultSet r=p.getGeneratedKeys()){return r.next()?r.getLong(1):-1;}
  }
 }
 public ArrayList<Jugador> listar() throws SQLException {
  ArrayList<Jugador> resultado=new ArrayList<>();
  try(Connection c=Conexion.abrir();PreparedStatement p=c.prepareStatement("SELECT id_jugador,nombre,apellido,fecha_nacimiento FROM jugador ORDER BY apellido,nombre");ResultSet r=p.executeQuery()){
   while(r.next())resultado.add(new Jugador(r.getLong(1),r.getString(2),r.getString(3),r.getDate(4).toLocalDate()));
  }
  return resultado;
 }
 public boolean actualizar(long id,String nombre,String apellido) throws SQLException {
  if(nombre==null||nombre.isBlank()||apellido==null||apellido.isBlank())throw new IllegalArgumentException("Nombre y apellido obligatorios");
  try(Connection c=Conexion.abrir();PreparedStatement p=c.prepareStatement("UPDATE jugador SET nombre=?,apellido=? WHERE id_jugador=?")){
   p.setString(1,nombre.trim());p.setString(2,apellido.trim());p.setLong(3,id);return p.executeUpdate()>0;
  }
 }
}
