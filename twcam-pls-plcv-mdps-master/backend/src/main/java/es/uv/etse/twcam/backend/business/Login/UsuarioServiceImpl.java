package es.uv.etse.twcam.backend.business.Login;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import es.uv.etse.twcam.backend.business.IncorrectProductException;
import es.uv.etse.twcam.backend.business.ProductException;
import es.uv.etse.twcam.backend.business.UsuarioNotExistException;

public class UsuarioServiceImpl implements UsuarioService {

	/**
	 * Instancia única.
	 */
	private static UsuarioServiceImpl the;

	/**
	 * Diccionario para el almacenamiento de productos.
	 */
	protected Map<String, Usuario> dictionary;

	/**
	 * Crea un servicio sin datos.
	 */
	private UsuarioServiceImpl() {
		dictionary = new Hashtable<>();
	}

	/**
	 * Obtiene la instancia única del servicio.
	 * 
	 * @return Instancia única.
	 */
	public static UsuarioServiceImpl getInstance() {

		if (the == null) {
			the = new UsuarioServiceImpl();
		}

		return the;
	}

	/**
	 * Limpia la instancia única del servicio.
	 * 
	 */
	public static void clearInstance() {

		if (the != null) {
			the.dictionary.clear();
			the = null;
		}

	}

	/**
	 * Añade un usuario al servicio.
	 * 
	 * @param usu Información del usuario.
	 * @return Usuario añadido por el servicio.
	 * @throws ProductException Indicador de error en la adición.
	 */
	public Usuario create(Usuario usu) throws ProductException {

		if (usu != null && usu.getNombre() != null) {
			dictionary.put(usu.getNombre(), usu);
		} else {
			throw new IncorrectProductException("Usuario o su nombre son nulos");
		}

		return usu;

	}

	@Override
	public List<Usuario_no_password> listAllUsuario() {

		List<Usuario_no_password> usuariosInfo = new ArrayList<>();

		for (Usuario usuario : dictionary.values()) {
			Usuario_no_password usuarioInfo = new Usuario_no_password(usuario.getNombre(), usuario.getRol());
			usuariosInfo.add(usuarioInfo);
		}

		return usuariosInfo;
	}

	@Override
	public Usuario find(String nombre) throws UsuarioNotExistException {

		if (nombre != null && dictionary.containsKey(nombre)) {
			return dictionary.get(nombre);
		} else {
			throw new UsuarioNotExistException(nombre);
		}
	}

	@Override
	public Usuario_no_password validarCredenciales(Usuario usu) throws UsuarioNotExistException {
		if (usu != null && usu.getNombre() != null) {
			Usuario usuario = find(usu.getNombre());
			if (usuario != null && usuario.getPassword().equals(usu.getPassword())) {
				return new Usuario_no_password(usuario.getNombre(), usuario.getRol());

			}

		}
		return null;
	}

	@Override
	public List<Usuario_no_password> mostrarDJ()  {
		List<Usuario_no_password> nombresDJ = new ArrayList<>();

		for (Usuario usuario : dictionary.values()) {
			if ("dj".equals(usuario.getRol())) {
				Usuario_no_password usuarioInfo = new Usuario_no_password(usuario.getNombre(), usuario.getRol());
				nombresDJ.add(usuarioInfo);
			}
		}

		return nombresDJ;
	}

}
