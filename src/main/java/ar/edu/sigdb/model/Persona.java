package ar.edu.sigdb.model;
/** Abstracción común con encapsulamiento. */
public abstract class Persona {
 private final long id;
 private String nombre;
 private String apellido;
 protected Persona(long id,String nombre,String apellido){
  if(id<0 || nombre==null || nombre.isBlank() || apellido==null || apellido.isBlank()) throw new IllegalArgumentException("Datos personales inválidos");
  this.id=id;this.nombre=nombre.trim();this.apellido=apellido.trim();
 }
 public long getId(){return id;}
 public String getNombre(){return nombre;}
 public String getApellido(){return apellido;}
 public void setNombre(String nombre){if(nombre==null||nombre.isBlank())throw new IllegalArgumentException("Nombre obligatorio");this.nombre=nombre.trim();}
 public abstract String descripcion();
}
