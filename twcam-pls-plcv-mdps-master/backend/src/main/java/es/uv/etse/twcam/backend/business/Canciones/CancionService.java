package es.uv.etse.twcam.backend.business.Canciones;

import java.util.List;



public interface CancionService {
    public List<Cancion> listAll();
    
    public List<Cancion> getByduracion(String duracion);

    public List<Cancion> getBytematica(String tematica);

    public List<Cancion> getByautor(String autor);

    public Cancion remove(Integer id) throws CancionNotExistException;
    
}
