package ar.edu.sigdb.model;
import java.time.LocalDate;
import java.util.List;
/** Demostración ejecutable sin requerir conexión a MySQL. */
public class DemoPOO {
 public static void main(String[] args){
  List<Persona> personas=List.of(new Jugador(1,"Ana","Pérez",LocalDate.of(2012,4,3)),new Entrenador(2,"Luis","Gómez"));
  for(Persona persona:personas)System.out.println(persona.descripcion());
 }
}
