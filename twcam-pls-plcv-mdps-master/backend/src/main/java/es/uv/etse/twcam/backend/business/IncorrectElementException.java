package es.uv.etse.twcam.backend.business;

public class IncorrectElementException extends GeneralException {

  public IncorrectElementException(String bug) {
    super(bug + " con formato erróneo");
  }

  private static final long serialVersionUID = 1L;

}
