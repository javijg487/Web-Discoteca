package es.uv.etse.twcam.backend.business;

public class Invitado {

  private String nombre;

  private String dni;

  public Invitado(String nombre, String dni) {
    this.nombre = nombre;
    this.dni = dni;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDni() {
    return dni;
  }
}