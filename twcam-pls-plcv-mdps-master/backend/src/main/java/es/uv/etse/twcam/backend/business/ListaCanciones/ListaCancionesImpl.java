package es.uv.etse.twcam.backend.business.ListaCanciones;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import es.uv.etse.twcam.backend.business.IncorrectProductException;
import es.uv.etse.twcam.backend.business.ProductException;
import es.uv.etse.twcam.backend.business.Canciones.Cancion;
import es.uv.etse.twcam.backend.business.Canciones.CancionNotExistException;

public class ListaCancionesImpl implements ListaCancionesService {
    private static ListaCancionesImpl the;

    protected Map<String, ListaCanciones> dictionary;
    protected int currentIndex;

    private ListaCancionesImpl() {
        dictionary = new Hashtable<>();
        currentIndex = 0;
    }

    public static ListaCancionesImpl getInstance() {

        if (the == null) {
            the = new ListaCancionesImpl();
        }

        return the;
    }

    public static void clearInstance() {
        if (the != null) {
            the = null;
            the.dictionary.clear();
        }
    }

    @Override
    public List<Cancion> listAllCanciones(Integer idEvento) {
        List<Cancion> canciones = new ArrayList<>();
        for (ListaCanciones listaCanciones : dictionary.values()) {
            if (listaCanciones.getIdEvento().equals(idEvento)) {
                canciones.addAll(listaCanciones.getCanciones());
            }
        }

        return canciones;
    }

    @Override
    public ListaCanciones create(ListaCanciones listaCanciones) {

        if (listaCanciones != null && listaCanciones.getIdEvento() != null) {
            dictionary.put(String.valueOf(listaCanciones.getIdEvento()), listaCanciones);
        }
        return listaCanciones;

    }

    @Override
    public List<Cancion> getByduracion(String duracion, Integer idEvento) {

        List<Cancion> cancionesDuracion = new ArrayList<>();
        int tiempototal = Integer.parseInt(duracion.split(":")[0]) * 60 + Integer.parseInt(duracion.split(":")[1]);

        for (ListaCanciones listaCanciones : dictionary.values()) {
            if (listaCanciones.getIdEvento().equals(idEvento)) {
                for (Cancion cancion : listaCanciones.getCanciones()) {
                    String duracioncancion = cancion.getDuracion();
                    int tiempototalcancion = Integer.parseInt(duracioncancion.split(":")[0]) * 60
                            + Integer.parseInt(duracioncancion.split(":")[1]);
                    if (!cancionesDuracion.contains(cancion) && tiempototalcancion > tiempototal) {
                        cancionesDuracion.add(cancion);
                    }
                }
            }
        }
        return cancionesDuracion;
    }

    @Override
    public List<Cancion> getBytematica(String tematica, Integer idEvento) {
        List<Cancion> cancionesTematica = new ArrayList<>();

        for (ListaCanciones listaCanciones : dictionary.values()) {
            if (listaCanciones.getIdEvento().equals(idEvento)) {
                for (Cancion cancion : listaCanciones.getCanciones()) {
                    if (!cancionesTematica.contains(cancion) && cancion.getTematica().equals(tematica)) {
                        cancionesTematica.add(cancion);
                    }
                }
            }
        }
        return cancionesTematica;
    }

    @Override
    public List<Cancion> getByautor(String autor, Integer idEvento) {
        List<Cancion> cancionesAutor = new ArrayList<>();

        for (ListaCanciones listaCanciones : dictionary.values()) {
            if (listaCanciones.getIdEvento().equals(idEvento)) {
                for (Cancion cancion : listaCanciones.getCanciones()) {
                    if (!cancionesAutor.contains(cancion) && cancion.getAutor().equals(autor)) {
                        cancionesAutor.add(cancion);
                    }
                }
            }
        }
        return cancionesAutor;
    }

    @Override
    public Cancion editarEstado(Integer id, Integer idEvento) throws CancionNotExistException {

        for (ListaCanciones listaCanciones : dictionary.values()) {
            if (listaCanciones.getIdEvento().equals(idEvento)) {
                for (Cancion cancion : listaCanciones.getCanciones()) {
                    if (cancion.getID().equals(id)) {
                        cancion.setEstado("pendiente");
                        return cancion;
                    }
                }
            }
        }
        throw new CancionNotExistException(id);
        
    }

    @Override
    public Cancion remove(Integer id, Integer idEvento) throws CancionNotExistException {

        for (ListaCanciones listaCanciones : dictionary.values()) {
            if (listaCanciones.getIdEvento().equals(idEvento)) {
                for (Cancion cancion : listaCanciones.getCanciones()) {
                    if (cancion.getID().equals(id)) {
                        listaCanciones.getCanciones().remove(cancion);
                        return cancion;
                    }
                }
            }
        }
        throw new CancionNotExistException(id);
    }
}
