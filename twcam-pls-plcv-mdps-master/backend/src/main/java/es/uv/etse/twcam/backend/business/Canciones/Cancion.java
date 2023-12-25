package es.uv.etse.twcam.backend.business.Canciones;

public class Cancion {
    private int id;
    private String nombre;
    private String autor;
    private String duracion;
    private String tematica;


    public Cancion(int id, String nombre, String autor, String duracion, String tematica) {
		
        this.id = id;
		this.nombre = nombre;
		this.autor = autor;
        this.duracion = duracion;
        this.tematica = tematica;
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
}