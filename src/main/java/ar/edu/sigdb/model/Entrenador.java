package ar.edu.sigdb.model;
/** Segunda implementación polimórfica de Persona. */
public final class Entrenador extends Persona {
 public Entrenador(long id,String nombre,String apellido){super(id,nombre,apellido);}
 @Override public String descripcion(){return "Entrenador: "+getApellido()+", "+getNombre();}
}
