package es.uv.etse.twcam.backend.business;

public class Invitado {

  private String nombre;

  private String dni;

  public Invitado(String nombre, String dni) {
    this.nombre = nombre;
    this.dni = dni;
  }

  /**
   * @param estrellas the estrellas to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getDni() {
    return dni;
  }
}