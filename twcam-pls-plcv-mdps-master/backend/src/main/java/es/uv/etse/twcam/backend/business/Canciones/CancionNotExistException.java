package es.uv.etse.twcam.backend.business.Canciones;

import es.uv.etse.twcam.backend.business.GeneralException;

public class CancionNotExistException extends GeneralException {

	private static final long serialVersionUID = 1L;

	public CancionNotExistException(Integer id) {
		super("La cancion "+id+" no existe");
	}

}
