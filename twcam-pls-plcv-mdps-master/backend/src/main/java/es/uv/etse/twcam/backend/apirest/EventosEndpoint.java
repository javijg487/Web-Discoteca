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
import com.google.gson.JsonElement;

import es.uv.etse.twcam.backend.business.Eventos.Evento;
import es.uv.etse.twcam.backend.business.Eventos.EventoService;
import es.uv.etse.twcam.backend.business.Eventos.EventoServiceImpl;
import es.uv.etse.twcam.backend.business.Reserva.ReservaService;
import es.uv.etse.twcam.backend.business.Reserva.ReservaServiceDictionaryImpl;


import org.apache.logging.log4j.*;

@WebServlet("/api/eventos/*") // <1>
public class EventosEndpoint extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(EventosEndpoint.class.getName());

    private static final String END_POINT_NAME = "eventos";

    private final Gson g = new GsonBuilder().create();

    private static EventoService service = EventoServiceImpl.getInstance();
    private static ReservaService reservaService = ReservaServiceDictionaryImpl.getInstance();

    public EventosEndpoint() {
        super();
        logger.info("Product EndPoint creado"); // <7>
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String result = null;
        Integer id = null;

        try {
            id = getEventoId(request);
        } catch (Exception e) {
            logger.info("No se ha podido obtener el identificador del request");
        }

        logger.info("GET at {} with ID: {}", request.getContextPath(), id);

        if (id == null) {
            List<Evento> eventos = null;
            eventos = service.listAll();
            result = g.toJson(eventos);

        } else {
            try {
                Evento ev = service.getById(id);
                JsonElement jsonElement = g.toJsonTree(ev);
                jsonElement.getAsJsonObject().addProperty("tieneSalasDisponibles",
                        reservaService.tieneSalasDisponibles(id));
                result = g.toJson(jsonElement);
            } catch (Exception e) {
                logger.error("Evento no encontrado");
            }
        }

        addCORSHeaders(response);

        if (result != null) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            result = "{}";
        }

        try {
            PrintWriter pw = response.getWriter();
            pw.println(result);
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            logger.error("Imposible enviar respuesta", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Evento evento = getEventoFromInputStream(request.getInputStream());

            if (evento == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                addCORSHeaders(response);
                logger.error("Formato incorrecto, no se pudo crear el evento");
            } else {
                evento = service.create(evento);

                logger.info("POST at: {} with {} ", request.getContextPath(), evento);

                response.setStatus(HttpServletResponse.SC_ACCEPTED);

                addCORSHeaders(response);

                PrintWriter pw = response.getWriter();
                pw.println(g.toJson(evento));
                pw.flush();
                pw.close();
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.error("Evento no creado", e);
        }
    }

   protected void doPut(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		
		Evento evento = null;
		
		try {

			evento = getEventoFromInputStream(request.getInputStream());
           
			if (evento == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				addCORSHeaders(response);
				logger.error("Evento no actualizado por no se puede extraer desde JSON");
			} else {
				evento = service.update(evento);

				logger.info("PUT at: {} with {} ", request.getContextPath(), evento);

				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				addCORSHeaders(response); // <2>

				PrintWriter pw = response.getWriter();
				pw.println(g.toJson(evento));
				pw.flush();
				pw.close();
			}
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); // <3>
			logger.error("Producto no actualizado", e); // <7>
		}
	}

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {

        addCORSHeaders(response);

        try {
            super.doOptions(request, response);
        } catch (ServletException se) {
            logger.error("Error genérico en la clase padre");
        } catch (IOException ioe) {
            logger.error("Error genérico de salida la clase padre");
        }
    }

    protected static Integer getEventoId(HttpServletRequest request) throws APIRESTException { // <5>

        String url = request.getRequestURL().toString();

        int posIni = url.lastIndexOf("/");

        int posEnd = url.lastIndexOf("?");

        if (posEnd < 0) {
            posEnd = url.length();
        }

        String id = url.substring(posIni + 1, posEnd);

        logger.debug("ID: {}", id);

        if (id.trim().isEmpty()) {
            id = null;
        }

        if (id == null) {
            throw new APIRESTException("Faltan parámetros en el EndPoint");
        } else {
            if (id.equals(END_POINT_NAME)) {
                id = null;
            }
        }

        Integer valor = null;

        if (id != null) {
            valor = Integer.valueOf(id);
        }

        return valor;
    }

    private Evento getEventoFromInputStream(InputStream stream) {

        Evento evento = null;

        try {

            evento = g.fromJson(new InputStreamReader(stream), Evento.class);

        } catch (Exception e) {
            evento = null;
            logger.error("Error al obtener evento desde JSON", e);
        }

        return evento;
    }

    private void addCORSHeaders(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
        response.addHeader("Access-Control-Allow-Headers", "authorization,content-type");
        response.addHeader("Access-Control-Allow-Origin", "*");
    }
}
