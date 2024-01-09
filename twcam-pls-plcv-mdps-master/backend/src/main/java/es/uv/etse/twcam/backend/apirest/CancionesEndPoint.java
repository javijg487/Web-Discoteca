package es.uv.etse.twcam.backend.apirest;

import java.io.IOException;
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
import es.uv.etse.twcam.backend.business.ListaCanciones.ListaCancionesImpl;
import es.uv.etse.twcam.backend.business.ListaCanciones.ListaCancionesService;

import org.apache.logging.log4j.*;

/**
 * Implementaci&oacute;n b&aacute;sica del Endpoint <b>Usuarios</b>.
 * 
 * 
 */
@WebServlet("/api/canciones/*") // <1>
public class CancionesEndPoint extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CancionesEndPoint.class.getName()); // <7>
	private final Gson g = new GsonBuilder().create();
	private static ListaCancionesService service2 = ListaCancionesImpl.getInstance();

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
		Integer idEvento = null;

		action = request.getHeader("Action");

		logger.info("GET at {} with {}", request.getContextPath(), action); // <7>

		try {
			idEvento = getEventoId(request, true);
		} catch (Exception ex) {
			logger.error("Error al obtener el ID", ex);
		}

		try {
			if (idEvento != null) {

				switch (action) {
					case "Duracion":
						duracion = request.getParameter("duracion");
						canciones = service2.getByduracion(duracion, idEvento);
						break;
					case "Autor":
						autor = request.getParameter("autor");
						canciones = service2.getByautor(autor, idEvento);
						break;
					case "Tematica":
						tematica = request.getParameter("tematica");
						canciones = service2.getBytematica(tematica, idEvento);
						break;
					case "Todas":
						canciones = service2.listAllCanciones(idEvento);
						break;
					case "Pendientes":
						canciones = service2.mostrarPendientes(idEvento);
						break;
					case "Reproducir":
						canciones = service2.mostrarReproducida(idEvento);
						break;

					default:
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.getWriter().write("Accion invalida");

				}

				result = g.toJson(canciones);

			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				result = "{}";
			}

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
	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		Integer id = null;
		Integer idEvento = null;
		String action = null;

		action = request.getHeader("Action");

		try {
			idEvento = getEventoId(request, true);
		} catch (Exception ex) {
			logger.error("Error al obtener el ID del evento", ex);
		}

		if (idEvento != null) {
			try {
				switch (action) {
					case "Pendiente":
						id = Integer.parseInt(request.getParameter("pendiente"));
						service2.editarEstado(id, idEvento);
						break;
					case "Reproducido":

						id = Integer.parseInt(request.getParameter("reproducir"));
						service2.pasarReproducir(id, idEvento);
						break;
					default:
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						response.getWriter().write("Accion invalida");
				}
				logger.info("PUT  at {} with ID: {}", request.getContextPath(), id); // <7>
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				addCORSHeaders(response);
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				logger.error("cancion no encontrado para editar", e);
				logger.info("No se ha podido obtener el identificador del request");
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Id del evento nulo");
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		Integer id = null;
		Integer idEvento = null;
		String[] pathParts = null;
		String pathInfo = request.getPathInfo();

		try {
			idEvento = getEventoId(request, true);
		} catch (Exception ex) {
			logger.error("Error al obtener el ID del evento", ex);
		}
		if (idEvento != null) {

			try {
				id = Integer.parseInt(request.getParameter("eliminar"));
			} catch (Exception e) {
				logger.info("No se ha podido obtener el identificador del request"); // <7>
			}

			logger.info("DELETE at {} with ID: {}", request.getContextPath(), id);

			if (pathInfo != null) {
				pathParts = pathInfo.split("/");

			}

			if (id == null) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				addCORSHeaders(response);
				logger.error("ID de cancion no proporcionado en la solicitud DELETE");
			} else {
				try {
					if (pathParts != null && pathParts[1].equals("reproducir")) {
						service2.removeReproducidas(id, idEvento);
					} else {
						service2.remove(id, idEvento);
					}
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					addCORSHeaders(response);
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					logger.error("cancion no encontrado para eliminar", e);
				}
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Id del evento nulo");
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

	private Integer getEventoId(HttpServletRequest request, Boolean required) throws APIRESTException {

		String url = request.getRequestURL().toString();

		int posIni = url.lastIndexOf("/");

		int posEnd = url.lastIndexOf("?");

		if (posEnd < 0) {
			posEnd = url.length();
		}

		String id = url.substring(posIni + 1, posEnd);

		if (id.trim().isEmpty()) {
			if (required) {
				throw new APIRESTException("Faltan parámetros en el EndPoint");
			}
			return null;
		}

		return Integer.parseInt(id);

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
		response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
		response.addHeader("Access-Control-Allow-Headers", "authorization,content-type, action");
		response.addHeader("Access-Control-Allow-Origin", "*");
	}
}