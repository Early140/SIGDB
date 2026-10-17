package ar.edu.sigdb.model;
import java.time.LocalDate;
/** Herencia de Persona y polimorfismo por sobrescritura. */
public final class Jugador extends Persona {
 private final LocalDate nacimiento;
 public Jugador(long id,String nombre,String apellido,LocalDate nacimiento){super(id,nombre,apellido);if(nacimiento==null||nacimiento.isAfter(LocalDate.now()))throw new IllegalArgumentException("Nacimiento inválido");this.nacimiento=nacimiento;}
 public LocalDate getNacimiento(){return nacimiento;}
 @Override public String descripcion(){return "Jugador: "+getApellido()+", "+getNombre();}
}
