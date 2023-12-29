package es.uv.etse.twcam.backend.business.Eventos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import es.uv.etse.twcam.backend.business.Login.Usuario;
import es.uv.etse.twcam.backend.business.Canciones.Cancion;
import es.uv.etse.twcam.backend.business.Reserva.Reserva;

public class Evento {
    private Integer id;
    private Usuario dj;
    private LocalDateTime fecha;
    private String tematica;
    private ArrayList<Cancion> cancionesDisponibles;
    /*
     * Me falta añadir la Pista
     */
    private ArrayList<Cancion> cancionesReproducidas;
    private ArrayList<Cancion> peticiones;
    private ArrayList<Reserva> reservas;

    public Evento(Integer id, Usuario dj, LocalDateTime fecha, String tematica, ArrayList<Cancion> cancionesDisponibles,
            ArrayList<Cancion> cancionesReproducidas, ArrayList<Cancion> peticiones, ArrayList<Reserva> reservas) {
        
        this.id = id;
        this.dj = dj;
        this.fecha = fecha;
        this.tematica = tematica;
        this.cancionesDisponibles = cancionesDisponibles;
        this.cancionesReproducidas = cancionesReproducidas;
        this.peticiones = peticiones;
        this.reservas = reservas;
    }

    public Integer getId(){
        return id;
    }

    public Usuario getDj() {
        return dj;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getTematica() {
        return tematica;
    }

    public ArrayList<Cancion> getCancionesDisponibles() {
        return cancionesDisponibles;
    }

    public ArrayList<Cancion> getCancionesRepro() {
        return cancionesReproducidas;
    }

    public ArrayList<Cancion> getPeticiones() {
        return peticiones;
    }


    public ArrayList<Reserva> getReservas(){
        return reservas;
    }

    /*
    public void setId(Integer id){
        this.id = id;
    }
    
    public void setDj(Usuario dj) {
        this.dj = dj;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public void setCancionesDisponibles(ArrayList<Cancion> cancionesDisponibles) {
        this.cancionesDisponibles = cancionesDisponibles;
    }

    public void setCancionesRepro(ArrayList<Cancion> cancionesReproducidas) {
        this.cancionesReproducidas = cancionesReproducidas;
    }

    public void setPeticiones(ArrayList<Cancion> peticiones) {
        this.peticiones = peticiones;
    }

    public void setReservas(ArrayList<Reserva> reservas){
        this.reservas = reservas;
    }
     */
    

}
