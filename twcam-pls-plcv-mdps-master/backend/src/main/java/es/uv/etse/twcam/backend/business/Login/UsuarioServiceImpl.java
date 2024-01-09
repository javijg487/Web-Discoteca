package es.uv.etse.twcam.backend.business.Login;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.IncorrectElementException;
import es.uv.etse.twcam.backend.business.UsuarioNotExistException;

public class UsuarioServiceImpl implements UsuarioService {
	private static UsuarioServiceImpl the;
	protected Map<String, Usuario> dictionary;

	private UsuarioServiceImpl() {
		dictionary = new Hashtable<>();
	}

	public static UsuarioServiceImpl getInstance() {

		if (the == null) {
			the = new UsuarioServiceImpl();
		}

		return the;
	}

	public static void clearInstance() {

		if (the != null) {
			the.dictionary.clear();
			the = null;
		}

	}

	public Usuario create(Usuario usu) throws GeneralException {

		if (usu != null && usu.getNombre() != null) {
			dictionary.put(usu.getNombre(), usu);
		} else {
			throw new IncorrectElementException("Usuario o su nombre son nulos");
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
	public List<Usuario_no_password> mostrarDJ() {
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
