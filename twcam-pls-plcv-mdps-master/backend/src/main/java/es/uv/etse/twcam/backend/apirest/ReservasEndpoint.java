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

import es.uv.etse.twcam.backend.business.Reserva.Reserva;
import es.uv.etse.twcam.backend.business.Reserva.ReservaService;
import es.uv.etse.twcam.backend.business.Reserva.ReservaServiceDictionaryImpl;

import org.apache.logging.log4j.*;

@WebServlet("/api/reservas/*")
public class ReservasEndpoint extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private static final Logger logger = LogManager.getLogger(ProductosEndpoint.class.getName());

  private final Gson g = new GsonBuilder().create();

  private static ReservaService service = ReservaServiceDictionaryImpl.getInstance();

  public ReservasEndpoint() {
    super();
    logger.info("Reservas EndPoint creado");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

    String result = null;
    String username = request.getParameter("username");

    logger.info("GET at {} with username: {}", request.getContextPath(), username);

    if (username == null) {
      List<Reserva> reservas = null;
      reservas = service.listAll();
      result = g.toJson(reservas);
    } else {
      List<Reserva> reserva = service.listByUser(username);
      result = g.toJson(reserva);
    }

    addCORSHeaders(response);

    response.setStatus(HttpServletResponse.SC_ACCEPTED);

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

      Reserva reserva = getReservaFromInputStream(request.getInputStream());

      if (reserva == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        addCORSHeaders(response);
        logger.error("Formato incorrecto, no se pudo crear la reserva");
      } else {
        reserva = service.create(reserva);

        logger.info("POST at: {} with {} ", request.getContextPath(), reserva);

        response.setStatus(HttpServletResponse.SC_ACCEPTED);

        addCORSHeaders(response);

        PrintWriter pw = response.getWriter();
        pw.println(g.toJson(reserva));
        pw.flush();
        pw.close();
      }

    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      logger.error("Producto no actualizado", e);
    }
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Integer id = null;
    String nuevoEstado = getReservaFromInputStream(request.getInputStream()).getEstado();

    try {
      id = getReservaId(request);
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      logger.error("El ID no fue provisto", e);
    }

    Reserva reserva = service.getById(id);

    if (reserva == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      logger.error("El ID de la reserva no fue provisto");
    } else {
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      reserva.setEstado(nuevoEstado);
    }

    logger.info("PUT at: {} with {} ", request.getContextPath(), id);

    addCORSHeaders(response);

    try {
      PrintWriter pw = response.getWriter();
      pw.println(g.toJson(reserva));
      pw.flush();
      pw.close();
    } catch (IOException ex) {
      logger.error("Imposible enviar respuesta", ex);
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

  private Integer getReservaId(HttpServletRequest request) throws APIRESTException {

    String url = request.getRequestURL().toString();

    int posIni = url.lastIndexOf("/");

    int posEnd = url.lastIndexOf("?");

    if (posEnd < 0) {
      posEnd = url.length();
    }

    String id = url.substring(posIni + 1, posEnd);

    if (id.trim().isEmpty()) {
      throw new APIRESTException("Faltan parámetros en el EndPoint");
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