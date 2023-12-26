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

import es.uv.etse.twcam.backend.business.Canciones.Cancion;
import es.uv.etse.twcam.backend.business.Canciones.CancionService;
import es.uv.etse.twcam.backend.business.Canciones.CancionServiceImpl;

import org.apache.logging.log4j.*;

/**
 * Implementaci&oacute;n b&aacute;sica del Endpoint <b>Usuarios</b>.
 * 
 * 
 */
@WebServlet("/api/canciones/*") // <1>
public class CancionesEndPoint extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(CancionesEndPoint.class.getName()); // <7>

	/**
	 * Gson parser
	 */
	private final Gson g = new GsonBuilder().create();

	/**
	 * Servicio sobre productos.
	 */
	private static CancionService service = CancionServiceImpl.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CancionesEndPoint() {
		super();
		logger.info("Cancion EndPoint creado"); // <7>
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		String result = null;
		String duracion = null;
		String tematica = null;
		String autor = null;
		String action = null;
		List<Cancion> canciones = null;

		action = request.getHeader("Action");

		logger.info("GET at {} with {}", request.getContextPath(), action); // <7>

		try {

			switch (action) {
				case "Duracion":
					duracion = request.getParameter("duracion");
					canciones = service.getByduracion(duracion);
					break;
				case "Autor":
					autor = request.getParameter("autor");
					canciones = service.getByautor(autor);
					break;
				case "Tematica":
					tematica = request.getParameter("tematica");
					canciones = service.getBytematica(tematica);
					break;
				case "Todas":
					canciones = service.listAll();
					break;

				default:
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().write("Accion invalida");

			}

			result = g.toJson(canciones);

		} catch (Exception e) {
			logger.info("No se ha podido obtener el identificador para filtrar del request");
		}

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
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			logger.error("Imposible enviar respuesta", ex); // <7>
		}
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

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		Integer id = null;

		try {
			id = Integer.parseInt(request.getParameter("eliminar"));
		} catch (Exception e) {
			logger.info("No se ha podido obtener el identificador del request"); // <7>
		}

		if (id == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			addCORSHeaders(response);
			logger.error("ID de cancion no proporcionado en la solicitud DELETE");
		} else {
			try {
				service.remove(id);
				logger.info("DELETE at {} with ID: {}", request.getContextPath(), id); // <7>
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				addCORSHeaders(response);
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				logger.error("cancion no encontrado para eliminar", e);
			}
		}
	}

	/**
	 * Obtiene el Product de un stream JSON
	 * 
	 * @param stream Stream JSON
	 * @return Product
	 */
	private Cancion getCancionFromInputStream(InputStream stream) {

		Cancion can = null;

		try {

			can = g.fromJson(new InputStreamReader(stream), Cancion.class); // <4>

		} catch (Exception e) {
			can = null;
			logger.error("Error al obtener Cancion desde JSON", e); // <7>
		}

		return can;
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