package es.uv.etse.twcam.backend.business.Login;

public class Usuario_no_password {
    private String nombre;
    private String rol;


    public Usuario_no_password(String nombre, String rol) {
		
		this.nombre = nombre;
		
        this.rol = rol;
}

public String getNombre(){
    return nombre;
}

public String getRol(){
    return rol;
}

}
