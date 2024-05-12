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

import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.Reserva.Reserva;
import es.uv.etse.twcam.backend.business.Reserva.ReservaService;
import es.uv.etse.twcam.backend.business.Reserva.ReservaServiceDictionaryImpl;

import org.apache.logging.log4j.*;

@WebServlet("/api/reservas/*")
public class ReservasEndpoint extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final Logger logger = LogManager.getLogger(ReservasEndpoint.class.getName());

  private final Gson g = new GsonBuilder().create();

  private static ReservaService service = ReservaServiceDictionaryImpl.getInstance();

  public ReservasEndpoint() {
    super();
    logger.info("Reservas EndPoint creado");
  }

  private void finishConnection(HttpServletResponse response, String jsonString) {
    try {
      addCORSHeaders(response);
      PrintWriter pw = response.getWriter();
      pw.println(jsonString);
      pw.flush();
      pw.close();
    } catch (IOException ex) {
      logger.error("Imposible enviar respuesta", ex);
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    Integer id = null;
    String result = null;
    String username = request.getParameter("username");

    logger.info("GET at {} with username: {}", request.getContextPath(), username);

    try {
      id = getReservaId(request, false);
    } catch (Exception ex) {
      // No existe necesidad de tener un ID
    }

    if (id != null) {
      Reserva reserva = null;
      reserva = service.getById(id);
      if (reserva != null) {
        result = g.toJson(reserva);
      }
    } else if (username == null) {
      List<Reserva> reservas = null;
      reservas = service.listAll();
      result = g.toJson(reservas);
    } else {
      List<Reserva> reserva = service.listByUser(username);
      result = g.toJson(reserva);
    }

    if (result != null) {
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    finishConnection(response, result);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Reserva reserva = getReservaFromInputStream(request.getInputStream());

      if (reserva == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        logger.error("Formato incorrecto, no se pudo crear la reserva");
      } else {
        reserva = service.create(reserva);
        logger.info("POST at: {} with {} ", request.getContextPath(), reserva);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);

      }
      finishConnection(response, g.toJson(reserva));

    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      logger.error("Reserva no actualizada", e);
    }
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Reserva reserva = getReservaFromInputStream(request.getInputStream());

      if (reserva == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        logger.error("El valor de la reserva no fue provisto");
      } else if (service.getById(reserva.getId()) == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      } else {
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        service.update(reserva);
        service.denyPending(reserva);

        logger.info("PUT at: {} with {} ", request.getContextPath(), reserva.getId());
      }

      finishConnection(response, g.toJson(reserva));
    } catch (IOException ex) {
      logger.error("Imposible enviar respuesta", ex);
    } catch (GeneralException ex) {
      logger.error("No se encontro la reserva");
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {

      Integer reservaId = getReservaId(request, true);

      if (reservaId == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        addCORSHeaders(response);
        logger.error("Reserva no actualizada por no se puede extraer desde JSON");
      } else {
        service.delete(reservaId);

        logger.info("DELETE at: {} with {} ", request.getContextPath(), reservaId);

        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        addCORSHeaders(response);

        PrintWriter pw = response.getWriter();
        pw.println("Reserva borrada exitosamente");
        pw.flush();
        pw.close();
      }

    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      logger.error("Empleado no actualizado", e);
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

  protected static Integer getReservaId(HttpServletRequest request, Boolean required) throws APIRESTException {
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

  private Reserva getReservaFromInputStream(InputStream stream) {
    Reserva reserva = null;
    try {
      reserva = g.fromJson(new InputStreamReader(stream), Reserva.class);
    } catch (Exception e) {
      reserva = null;
      logger.error("Error al obtener reserva desde JSON", e);
    }

    return reserva;
  }

  private void addCORSHeaders(HttpServletResponse response) { // <2>
    response.addHeader("Content-Type", "application/json");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
    response.addHeader("Access-Control-Allow-Headers", "authorization,content-type");
    response.addHeader("Access-Control-Allow-Origin", "*");
  }
}