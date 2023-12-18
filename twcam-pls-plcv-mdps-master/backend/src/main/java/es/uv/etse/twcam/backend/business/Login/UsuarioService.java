package es.uv.etse.twcam.backend.business.Login;

import es.uv.etse.twcam.backend.business.ProductException;
import es.uv.etse.twcam.backend.business.UsuarioNotExistException;

public interface UsuarioService {

    /**
     * Obtiene un usuario a partir de su nombre.
     * En el caso de que el usuario no exista lo indica con una excepci&oacute;n.
     * @param nombre nombre del usuario.
     * @return Usuario asociado al indentificador.
     * @throws ProductNotExistException Indicador de identificador no existente.
     */
    public Usuario find(String nombre) throws UsuarioNotExistException;

    /**
     * Compara un usuario a partir de su usuario y contraseña.
     * En el caso de que el empleado no exista lo indica con una excepcion.
     * @param nombre nombre del usuario.
     * @param password contraseña del usuario
     * @return Producto asociado al indentificador.
     */
    public Usuario_no_password validarCredenciales(Usuario usu) throws ProductException; //Cambiar por GeneralException cuando lo incluyamos
}
