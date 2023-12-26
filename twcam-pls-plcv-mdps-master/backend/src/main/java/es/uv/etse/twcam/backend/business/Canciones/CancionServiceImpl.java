package es.uv.etse.twcam.backend.business.Canciones;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import es.uv.etse.twcam.backend.business.IncorrectProductException;
import es.uv.etse.twcam.backend.business.ProductException;


public class CancionServiceImpl implements CancionService{
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
			the = null;
			the.dictionary.clear();
		}
	}

	public Cancion create(Cancion cancion) throws ProductException {
		
		if (cancion != null && cancion.getID() != null) {
			dictionary.put(String.valueOf(cancion.getID()), cancion);
		} else {
			throw new IncorrectProductException("Cancion o su nombre son nulos");
		}
		
		return cancion;
		
	}

    @Override
	public List<Cancion> listAll() {
		List<Cancion> canciones = new ArrayList<>();
		canciones.addAll(dictionary.values());

		return canciones;
	}

    @Override
    public List<Cancion> getByduracion(String duracion) {
		
		List<Cancion> cancionesDuracion = new ArrayList<>();
		int tiempototal = Integer.parseInt(duracion.split(":")[0])*60 + Integer.parseInt(duracion.split(":")[1]);

		for(Cancion cancion : dictionary.values()){
			String duracioncancion = cancion.getDuracion();
			int tiempototalcancion = Integer.parseInt(duracioncancion.split(":")[0])*60 + Integer.parseInt(duracioncancion.split(":")[1]);
			if(!cancionesDuracion.contains(cancion)&&tiempototalcancion>tiempototal){
			cancionesDuracion.add(cancion);
			}
		}
		return cancionesDuracion;
	}

    @Override
    public List<Cancion> getBytematica(String tematica) {
		List<Cancion> cancionesTematica = new ArrayList<>();

		for(Cancion cancion : dictionary.values()){
			if(!cancionesTematica.contains(cancion)&&cancion.getTematica().equals(tematica)){
			cancionesTematica.add(cancion);
			}
		}
		return cancionesTematica;
	}

     @Override
    public List<Cancion> getByautor(String autor) {
		List<Cancion> cancionesAutor = new ArrayList<>();

		for(Cancion cancion : dictionary.values()){
			if(!cancionesAutor.contains(cancion)&&cancion.getAutor().equals(autor)){
			cancionesAutor.add(cancion);
			}
		}
		return cancionesAutor;
	}
    
    @Override
	public Cancion remove(Integer id) throws CancionNotExistException {
		if (dictionary.containsKey(id.toString())) {
			return dictionary.remove(id.toString());
		} else {
			throw new CancionNotExistException(id);
		}
	}
    

}
