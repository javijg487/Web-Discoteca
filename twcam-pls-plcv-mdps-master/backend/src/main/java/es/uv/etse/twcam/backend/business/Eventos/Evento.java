package es.uv.etse.twcam.backend.business.Eventos;

import es.uv.etse.twcam.backend.business.Login.Usuario;


public class Evento {
    private Integer id;
    private Integer pista;
    private String nombre;
    private Usuario dj;
    private String fecha;
    private String tematica;
    private String imagen;
    

    public Evento(Integer id, Integer pista, String nombre, Usuario dj, String fecha, String tematica, String imagen) {
        
        this.nombre = nombre;
        this.pista = pista;
        this.id = id;
        this.dj = dj;
        this.fecha = fecha;
        this.tematica = tematica;
        this.imagen = imagen;
        
    }

    public Integer getId(){
        return id;
    }

    public Integer getPista(){
        return pista;
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
    
    public void setPista(Integer pista){
        this.pista = pista;
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
