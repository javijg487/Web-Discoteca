package es.uv.etse.twcam.backend.apirest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uv.etse.twcam.backend.business.Login.Usuario;
import es.uv.etse.twcam.backend.business.Login.Usuario_no_password;
import es.uv.etse.twcam.backend.business.Login.UsuarioService;
import es.uv.etse.twcam.backend.business.Login.UsuarioServiceImpl;

import org.apache.logging.log4j.*;

/**
 * Implementaci&oacute;n b&aacute;sica del Endpoint <b>Usuarios</b>.
 * 
 * 
 */
@WebServlet("/api/login") // <1>
public class UsuariosEndpoint extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(UsuariosEndpoint.class.getName()); // <7>
	private final Gson g = new GsonBuilder().create();
	private static UsuarioService service = UsuarioServiceImpl.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UsuariosEndpoint() {
		super();
		logger.info("Login EndPoint creado"); // <7>
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		List<Usuario_no_password> usuarios = null;
		String action = null;

		action = request.getParameter("rol");

		logger.info("GET at {}", request.getContextPath());

		try {

			if (action!=null && (action.equals("DJ")) ) {
				usuarios = service.mostrarDJ();
			} else {
				usuarios = service.listAllUsuario();
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); // <3>
			logger.error("Usuario DJ no encontrado", e);
		}

		result = g.toJson(usuarios);

		addCORSHeaders(response); // <2>

		if (result != null) {
			response.setStatus(HttpServletResponse.SC_ACCEPTED);// <3>
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);// <3>
			result = "{}";
		}

		try {
			PrintWriter pw = response.getWriter();
			pw.println(result);
			pw.flush();
			pw.close();
		} catch (IOException ex) {
			logger.error("Imposible enviar respuesta", ex); // <7>
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		Usuario usu = null;

		try {

			usu = getUsuarioFromInputStream(request.getInputStream());

			if (usu == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				addCORSHeaders(response);
				logger.error("Usuario no encontrdo porque no se puede extraer desde JSON");
			} else {
				Usuario_no_password usunopass = service.validarCredenciales(usu);

				if (usunopass != null) {
					response.setStatus(HttpServletResponse.SC_CREATED);
					addCORSHeaders(response);
					PrintWriter pw = response.getWriter();
					pw.println(g.toJson(usunopass));
					pw.flush();
					pw.close();
				} else {

					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					addCORSHeaders(response);
					response.getWriter().write("Credenciales incorrectas");
				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); // <3>
			logger.error("Usuario o password incorrecta", e); // <7>
		}

	}

	/**
	 * Obtiene el Usuario de un stream JSON
	 * 
	 * @param stream Stream JSON
	 * @return Usuario
	 */
	private Usuario getUsuarioFromInputStream(InputStream stream) {

		Usuario us = null;

		try {
			us = g.fromJson(new InputStreamReader(stream), Usuario.class);
		} catch (Exception e) {
			us = null;
			logger.error("Error al obtener usuario desde JSON", e); // <7>
		}

		return us;
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) {

		addCORSHeaders(response); // <2>

		try {
			super.doOptions(request, response);
		} catch (ServletException se) {
			logger.error("Error genérico en la clase padre"); // <7>
		} catch (IOException ioe) {
			logger.error("Error genérico de salida la clase padre"); // <7>
		}
	}

	/**
	 * Añade cabeceras Cross-origin resource sharing (CORS) para poder invocar el
	 * API
	 * REST desde Angular
	 * 
	 * @param response Repuesta HTTP a la que añadir cabeceras
	 */
	private void addCORSHeaders(HttpServletResponse response) { // <2>
		response.addHeader("Content-Type", "application/json");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
		response.addHeader("Access-Control-Allow-Headers", "authorization,content-type");
		response.addHeader("Access-Control-Allow-Origin", "*");
	}
}