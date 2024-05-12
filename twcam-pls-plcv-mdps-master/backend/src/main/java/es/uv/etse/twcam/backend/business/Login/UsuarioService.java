package es.uv.etse.twcam.backend.business.Login;

import java.util.List;

import es.uv.etse.twcam.backend.business.UsuarioNotExistException;

public interface UsuarioService {

    public List<Usuario_no_password> listAllUsuario();

    public Usuario find(String nombre) throws UsuarioNotExistException;

    public Usuario_no_password validarCredenciales(Usuario usu) throws UsuarioNotExistException;

    public List<Usuario_no_password> mostrarDJ();
}