package es.uv.etse.twcam.backend.business;

public class ElementNotExistsException extends GeneralException {

  private static final long serialVersionUID = 1L;

  public ElementNotExistsException(String elemento, Integer id) {
    super("El " + elemento + " " + id + " no existe");
  }

}
