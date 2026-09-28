package ar.edu.sigdb;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

/** Prototipo operacional de consola; autenticación y permisos previstos para otro incremento. */
public class Main {
  static Connection conectar() throws SQLException {
    return DriverManager.getConnection(System.getenv().getOrDefault("SIGDB_URL","jdbc:mysql://localhost:3306/sigdb"),System.getenv().getOrDefault("SIGDB_USER","sigdb_app"),System.getenv().getOrDefault("SIGDB_PASSWORD",""));
  }
  static void altaJugador(Connection c,Scanner sc) throws SQLException {
    System.out.print("Nombre: ");String nombre=sc.nextLine();System.out.print("Apellido: ");String apellido=sc.nextLine();System.out.print("Nacimiento (AAAA-MM-DD): ");LocalDate fecha=LocalDate.parse(sc.nextLine());
    if(nombre.isBlank()||apellido.isBlank()) throw new IllegalArgumentException("Nombre y apellido obligatorios");
    try(PreparedStatement p=c.prepareStatement("INSERT INTO jugador(nombre,apellido,fecha_nacimiento) VALUES(?,?,?)")){p.setString(1,nombre);p.setString(2,apellido);p.setDate(3,Date.valueOf(fecha));p.executeUpdate();System.out.println("Jugador guardado");}
  }
  static void listar(Connection c) throws SQLException {
    try(PreparedStatement p=c.prepareStatement("SELECT id_jugador,nombre,apellido,activo FROM jugador ORDER BY apellido,nombre");ResultSet r=p.executeQuery()) {while(r.next())System.out.printf("%d | %s %s | activo=%s%n",r.getLong(1),r.getString(2),r.getString(3),r.getBoolean(4));}
  }
  static void asignar(Connection c,Scanner sc) throws SQLException {
    System.out.print("ID jugador: ");long j=Long.parseLong(sc.nextLine());System.out.print("ID categoría: ");long cat=Long.parseLong(sc.nextLine());
    try(PreparedStatement p=c.prepareStatement("INSERT INTO jugador_categoria(id_jugador,id_categoria,fecha_alta) VALUES(?,?,CURRENT_DATE)")){p.setLong(1,j);p.setLong(2,cat);p.executeUpdate();System.out.println("Asignación guardada");}
  }
  static void asistencia(Connection c,Scanner sc) throws SQLException {
    System.out.print("ID entrenamiento: ");long ent=Long.parseLong(sc.nextLine());System.out.print("ID jugador: ");long j=Long.parseLong(sc.nextLine());System.out.print("PRESENTE o AUSENTE: ");String estado=sc.nextLine().trim().toUpperCase();
    if(!estado.equals("PRESENTE")&&!estado.equals("AUSENTE"))throw new IllegalArgumentException("Estado inválido");
    String q="INSERT INTO asistencia(id_entrenamiento,id_jugador,estado) SELECT ?,?,? FROM entrenamiento e JOIN jugador_categoria jc ON jc.id_categoria=e.id_categoria AND jc.id_jugador=? WHERE e.id_entrenamiento=? ON DUPLICATE KEY UPDATE estado=VALUES(estado)";
    try(PreparedStatement p=c.prepareStatement(q)){p.setLong(1,ent);p.setLong(2,j);p.setString(3,estado);p.setLong(4,j);p.setLong(5,ent);if(p.executeUpdate()==0)throw new IllegalArgumentException("Jugador no asignado al entrenamiento");System.out.println("Asistencia guardada");}
  }
  static void historial(Connection c,Scanner sc) throws SQLException {
    System.out.print("ID jugador: ");long id=Long.parseLong(sc.nextLine());
    try(PreparedStatement p=c.prepareStatement("SELECT e.fecha,c.nombre,a.estado FROM asistencia a JOIN entrenamiento e ON e.id_entrenamiento=a.id_entrenamiento JOIN categoria c ON c.id_categoria=e.id_categoria WHERE a.id_jugador=? ORDER BY e.fecha")){p.setLong(1,id);try(ResultSet r=p.executeQuery()){while(r.next())System.out.printf("%s | %s | %s%n",r.getDate(1),r.getString(2),r.getString(3));}}
  }
  public static void main(String[] args) {
    try(Connection c=conectar();Scanner sc=new Scanner(System.in)){System.out.println("SIGDB - Prototipo Java/MySQL");while(true){System.out.print("1 Alta jugador | 2 Listar | 3 Asignar categoría | 4 Asistencia | 5 Historial | 0 Salir: ");String op=sc.nextLine();if(op.equals("0"))break;try{switch(op){case "1"->altaJugador(c,sc);case "2"->listar(c);case "3"->asignar(c,sc);case "4"->asistencia(c,sc);case "5"->historial(c,sc);default->System.out.println("Opción desconocida");}}catch(Exception e){System.err.println("Operación rechazada: "+e.getMessage());}}}catch(SQLException e){System.err.println("Error de conexión: "+e.getMessage());}
  }
}
