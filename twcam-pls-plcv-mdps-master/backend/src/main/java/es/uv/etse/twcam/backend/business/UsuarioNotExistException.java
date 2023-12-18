package es.uv.etse.twcam.backend.business;

public class UsuarioNotExistException extends ProductException {

	private static final long serialVersionUID = 1L;

	public UsuarioNotExistException(String nombre) {
		super("El usuario "+nombre+" no existe");
	}

}
