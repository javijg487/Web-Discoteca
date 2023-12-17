package es.uv.etse.twcam.backend.business.Login;

import java.util.Hashtable;
import java.util.Map;

import es.uv.etse.twcam.backend.business.IncorrectProductException;
import es.uv.etse.twcam.backend.business.ProductException;
import es.uv.etse.twcam.backend.business.Producto;
import es.uv.etse.twcam.backend.business.UsuarioNotExistException;

public class UsuarioServiceImpl implements UsuarioService {

    /**
	 * Instancia única.
	 */
	private static UsuarioServiceImpl the;

    /**
	 * Diccionario para el almacenamiento de productos.
	 */
	protected Map<String,Usuario> dictionary;

	
	/**
	 * Crea un servicio sin datos.
	 */
	private UsuarioServiceImpl() {
		dictionary = new Hashtable<>();
	}

    /**
	 * Obtiene la instancia única del servicio.
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
	 * @param usu Información del producto.
	 * @return Producto añadido por el servicio.
	 * @throws ProductException Indicador de error en la adición.
	 */
	public Usuario create(Usuario usu) throws ProductException {
		
		if (usu != null && usu.getNombre() != null) {
			dictionary.put(usu.getNombre(), usu);
		} else {
			throw new IncorrectProductException("Producto o su nombre son nulos");
		}
		
		return usu;
		
	}


    @Override
	public Usuario find(String nombre) throws UsuarioNotExistException{

		
		if (dictionary.containsKey(nombre)) {
			return dictionary.get(nombre);
		} else {
            if(nombre == null){
			throw new UsuarioNotExistException(nombre);
            }else{
                return null;
            }
		}
	}

    

    @Override
    public Usuario_no_password validarCredenciales(Usuario usu) throws ProductException{
        Usuario usuario = find(usu.getNombre());
        if(usuario!=null && usuario.getPassword().equals(usu.getPassword())){
            return new Usuario_no_password(usuario.getNombre(), usuario.getRol());
        }else{
            return null; 
        }
    }
}
