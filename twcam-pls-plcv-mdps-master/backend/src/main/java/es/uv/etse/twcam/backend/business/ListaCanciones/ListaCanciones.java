package es.uv.etse.twcam.backend.business.ListaCanciones;

import java.util.List;

import es.uv.etse.twcam.backend.business.Canciones.Cancion;

public class ListaCanciones {
    private int idEvento;
    private List<Cancion> canciones;
    private List<Cancion> cancionesPendientes;
    private List<Cancion> cancionReproducida;

    public ListaCanciones(int idEvento, List<Cancion> canciones, List<Cancion> cancionesPendientes, List<Cancion> cancionReproducida ) {

        this.idEvento = idEvento;
        this.canciones = canciones;
        this.cancionesPendientes = cancionesPendientes;
        this.cancionReproducida = cancionReproducida;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public List<Cancion> getCancionesPendientes() {
        return cancionesPendientes;
    }

    public List<Cancion>  getCancionReproducida() {
        return cancionReproducida;
    }

    public void setCancionReproducida(List<Cancion>  cancionReproducida) {
        this.cancionReproducida = cancionReproducida;
    }

}
