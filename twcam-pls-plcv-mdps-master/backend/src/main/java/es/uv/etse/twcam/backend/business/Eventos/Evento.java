package es.uv.etse.twcam.backend.business.Eventos;

import java.time.LocalDateTime;
import java.util.ArrayList;

import es.uv.etse.twcam.backend.business.Login.Usuario;
import es.uv.etse.twcam.backend.business.Canciones.Cancion;
import es.uv.etse.twcam.backend.business.Reserva.Reserva;

public class Evento {
    private Integer id;
    private String nombre;
    private Usuario dj;
    private String fecha;
    private String tematica;
    private String imagen;
    /*
     * Me falta añadir la Pista
     */
    

    public Evento(Integer id,String nombre, Usuario dj, String fecha, String tematica, String imagen) {
        
        this.nombre = nombre;
        this.id = id;
        this.dj = dj;
        this.fecha = fecha;
        this.tematica = tematica;
        this.imagen = imagen;
        
    }

    public Integer getId(){
        return id;
    }

    public Usuario getDj() {
        return dj;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTematica() {
        return tematica;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setId(Integer id){
        this.id = id;
    }
    
    public void setDj(Usuario dj) {
        this.dj = dj;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    } 

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
