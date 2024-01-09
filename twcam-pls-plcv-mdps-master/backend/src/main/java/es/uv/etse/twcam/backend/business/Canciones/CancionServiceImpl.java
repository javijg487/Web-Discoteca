package es.uv.etse.twcam.backend.business.Canciones;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.IncorrectElementException;

public class CancionServiceImpl implements CancionService {
	private static CancionServiceImpl the;

	protected Map<String, Cancion> dictionary;
	protected int currentIndex;

	private CancionServiceImpl() {
		dictionary = new Hashtable<>();
		currentIndex = 0;
	}

	public static CancionServiceImpl getInstance() {

		if (the == null) {
			the = new CancionServiceImpl();
		}

		return the;
	}

	public static void clearInstance() {
		if (the != null) {
			the.dictionary.clear();
			the = null;
			
		}
	}

	public Cancion create(Cancion cancion) throws GeneralException {

		if (cancion != null && cancion.getID() != null) {
			dictionary.put(String.valueOf(cancion.getID()), cancion);
		} else {
			throw new IncorrectElementException("Cancion o su nombre son nulos");
		}

		return cancion;

	}

	@Override
	public List<Cancion> listAll() {
		List<Cancion> canciones = new ArrayList<>();
		canciones.addAll(dictionary.values());

		return canciones;
	}

	

}
