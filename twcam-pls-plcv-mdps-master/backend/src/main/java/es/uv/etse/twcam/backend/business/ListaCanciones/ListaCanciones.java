package es.uv.etse.twcam.backend.business.ListaCanciones;

import java.util.List;

import es.uv.etse.twcam.backend.business.Canciones.Cancion;

public class ListaCanciones {
    private int idEvento;
    private List<Cancion> canciones;

    public ListaCanciones(int idEvento, List<Cancion> canciones) {

        this.idEvento = idEvento;
        this.canciones = canciones;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }
  
    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

}
