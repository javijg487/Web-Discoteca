package es.uv.etse.twcam.backend.business.Login;

public class Usuario {
    private String nombre;
    private String password;
    private String rol;


    public Usuario(String nombre, String password, String rol) {
		
		this.nombre = nombre;
		this.password = password;
        this.rol = rol;
}

public String getNombre(){
    return nombre;
}

public String getPassword(){
    return password;
}

public String getRol(){
    return rol;
}

}
