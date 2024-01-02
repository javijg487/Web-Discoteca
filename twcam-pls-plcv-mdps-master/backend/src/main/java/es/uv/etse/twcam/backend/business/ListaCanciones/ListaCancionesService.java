package es.uv.etse.twcam.backend.business.ListaCanciones;

import java.util.List;

import es.uv.etse.twcam.backend.business.Canciones.Cancion;
import es.uv.etse.twcam.backend.business.Canciones.CancionNotExistException;

public interface ListaCancionesService {

    public List<Cancion> listAllCanciones(Integer idEvento);

    public ListaCanciones create(ListaCanciones listaCanciones) ;

    public List<Cancion> getByduracion(String duracion, Integer idEvento);

    public List<Cancion> getBytematica(String tematica, Integer idEvento);

    public List<Cancion> getByautor(String autor, Integer idEvento);

    public List<Cancion> mostrarReproducida(Integer idEvento);

    public List<Cancion> mostrarPendientes(Integer idEvento);

    public Cancion editarEstado(Integer id, Integer idEvento) throws CancionNotExistException;

    public Cancion pasarReproducir(Integer id,Integer idEvento)throws CancionNotExistException;

    public Cancion remove(Integer id, Integer idEvento) throws CancionNotExistException;
}
