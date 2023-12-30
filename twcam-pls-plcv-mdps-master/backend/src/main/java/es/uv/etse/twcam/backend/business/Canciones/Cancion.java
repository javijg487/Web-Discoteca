package es.uv.etse.twcam.backend.business.Canciones;

public class Cancion {
    private int id;
    private String nombre;
    private String autor;
    private String duracion;
    private String tematica;
    private String estado;


    public Cancion(int id, String nombre, String autor, String duracion, String tematica, String estado) {
		
        this.id = id;
		this.nombre = nombre;
		this.autor = autor;
        this.duracion = duracion;
        this.tematica = tematica;
        this.estado = estado;
    }

    public Integer getID(){
        return id;
    }

    public String getAutor(){
        return autor;
    }

    public String getDuracion(){
        return duracion;
    }

    public String getNombre(){
        return nombre;
    }

    public String getTematica(){
        return tematica;
    }

    public String getEstado(){
        return estado;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}