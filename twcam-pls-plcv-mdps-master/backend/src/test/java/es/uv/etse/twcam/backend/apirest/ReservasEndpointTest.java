package es.uv.etse.twcam.backend.apirest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import java.io.StringWriter;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.Invitado;
import es.uv.etse.twcam.backend.business.Reserva.Reserva;
import es.uv.etse.twcam.backend.business.Reserva.ReservaServiceDictionaryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.apache.logging.log4j.*;

public class ReservasEndpointTest {
  private static Logger logger = null;
  private static Gson gson = null;

  @BeforeAll
  public static void setLogger() {
    logger = LogManager.getLogger(ReservasEndpointTest.class.getName());
  }

  @BeforeAll
  public static void setGson() {
    gson = new GsonBuilder().create();
  }

  @BeforeAll
  public static void EventosService() throws GeneralException {
    ReservaServiceDictionaryImpl.clearInstance();
    ReservaServiceDictionaryImpl service = ReservaServiceDictionaryImpl.getInstance();

    ArrayList<Invitado> invitados = new ArrayList<Invitado>();
    Invitado invitado = new Invitado("Samuel", "12345A");
    invitados.add(invitado);
    invitado = new Invitado("Maria", "000000B");
    invitados.add(invitado);
    Reserva reserva = new Reserva(0, 1, "cliente1", invitados, null, false);

    service.create(reserva);
    invitados = new ArrayList<Invitado>();
    invitado = new Invitado("Mauricio", "000000B");
    invitados.add(invitado);
    reserva = new Reserva(0, 1, "cliente2", invitados, null, false);
    service.create(reserva);
  }

  private Integer executeGetReservaIdInTest(String url, Boolean required) throws APIRESTException {
    HttpServletRequest request = mock(HttpServletRequest.class);

    StringBuffer buffer = new StringBuffer();
    buffer.append(url);
    when(request.getRequestURL()).thenReturn(buffer);
    Integer result = null;
    result = ReservasEndpoint.getReservaId(request, required);

    return result;
  }

  @Test
  void testGetReservaId1() {
    Integer result = null;

    try {
      result = executeGetReservaIdInTest("http://localhost:8080/api/reservas/", false);
      assertNull(result);
      logger.info("URL correcta");
    } catch (Exception e) {
      assertNull(e);
    }
  }

  @Test
  void testGetEventoId2() {
    Integer result = null;

    try {
      result = executeGetReservaIdInTest("http://localhost:8080/api/reservas/1", true);
      assertEquals(Integer.valueOf(1), result);
      logger.info("URL correcta");
    } catch (Exception e) {
      assertNull(e);
    }
  }

  @Test
  void testGetEventoId3() {

    Integer result = null;

    try {
      result = executeGetReservaIdInTest("http://localhost:8080/api/reservas/?usuario=cliente1", false);

      assertNull(result);

      logger.info("URL correcta");
    } catch (Exception e) {
      assertNull(e);
    }
  }

  @Test
  @DisplayName("GET /reservas")
  void testDoGet1() throws Exception {

    HttpServletRequest request = mock(HttpServletRequest.class);

    when(request.getParameter("usuario")).thenReturn(null);

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);

    ReservasEndpoint endpoint = new ReservasEndpoint();
    endpoint.doGet(request, response);

    Reserva[] result = gson.fromJson(sw.getBuffer().toString().trim(),
        Reserva[].class);
    assertNotNull(result);
    assertEquals(2, result.length);
  }

  @Test
  @DisplayName("GET /reservas?username=cliente1")
  void testDoGet2() throws Exception {

    HttpServletRequest request = mock(HttpServletRequest.class);

    when(request.getParameter("username")).thenReturn("cliente1");

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);

    ReservasEndpoint endpoint = new ReservasEndpoint();
    endpoint.doGet(request, response);

    Reserva[] result = gson.fromJson(sw.getBuffer().toString().trim(),
        Reserva[].class);
    assertNotNull(result);
    assertEquals(1, result.length);

  }

  @Test
  @DisplayName("GET eventos/10000")
  void testDoGet4() throws Exception {

    HttpServletRequest request = mock(HttpServletRequest.class);

    StringBuffer buffer = new StringBuffer("http://localhost:8080/api/reservas/10000");
    when(request.getRequestURL()).thenReturn(buffer);

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);

    ReservasEndpoint endpoint = new ReservasEndpoint();
    endpoint.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    verify(response).addHeader("Content-Type", "application/json");
  }

  @Test
  @DisplayName("GET reservas/1")
  void testDoGet5() throws Exception {

    HttpServletRequest request = mock(HttpServletRequest.class);

    StringBuffer buffer = new StringBuffer("http://localhost:8080/plc-pls-mps-tutorial/api/reservas/1");
    when(request.getRequestURL()).thenReturn(buffer);

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);

    ReservasEndpoint endpoint = new ReservasEndpoint();
    endpoint.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    verify(response).addHeader("Content-Type", "application/json");

    Reserva result = gson.fromJson(sw.getBuffer().toString().trim(),
        Reserva.class);
    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("Pendiente de Pago", result.getEstado());
    assertEquals(1, result.getInvitados().size());

  }

  @Test
  @DisplayName("PUT reservas/10000")
  void testDoPut1() throws Exception {

    HttpServletRequest request = mock(HttpServletRequest.class);

    StringBuffer urlBuffer = new StringBuffer("http://localhost:8080/api/reservas/10000");

    when(request.getRequestURL()).thenReturn(urlBuffer);

    ServletInputStream mockServletInputStream = mock(ServletInputStream.class);

    when(mockServletInputStream.read(ArgumentMatchers.<byte[]>any(), anyInt(),
        anyInt()))
        .thenAnswer(new Answer<Integer>() {

          String inputJSON = "{\"id\": 1000,\"eventoId\": 3,\"usuario\": \"cliente1\",\"estado\": \"Aprobada\",\"invitados\": [{\"nombre\": \"Emma\",\"dni\": \"12345D\"}],\"esIndividual\": false}";

          ByteArrayInputStream bytesInput = new ByteArrayInputStream(inputJSON.getBytes());

          @Override
          public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
            Object[] args = invocationOnMock.getArguments();
            byte[] output = (byte[]) args[0];
            int offset = (int) args[1];
            int length = (int) args[2];
            return bytesInput.read(output, offset, length);
          }
        });

    when(request.getInputStream()).thenReturn(mockServletInputStream);

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);

    ReservasEndpoint endpoint = new ReservasEndpoint();
    endpoint.doPut(request, response);

    verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  @Test
  @DisplayName("PUT eventos/1")
  void testDoPut3() throws Exception {

    HttpServletRequest request = mock(HttpServletRequest.class);

    StringBuffer urlBuffer = new StringBuffer("http://localhost:8080/plc-pls-mps-tutorial/api/eventos/1");

    when(request.getRequestURL()).thenReturn(urlBuffer);

    ServletInputStream mockServletInputStream = mock(ServletInputStream.class);

    when(mockServletInputStream.read(ArgumentMatchers.<byte[]>any(), anyInt(),
        anyInt()))
        .thenAnswer(new Answer<Integer>() {

          String inputJSON = "{\"id\": 1,\"eventoId\": 3,\"usuario\": \"cliente1\",\"estado\": \"Aprobada\",\"invitados\": [{\"nombre\": \"Emma\",\"dni\": \"12345D\"}],\"esIndividual\": false}";

          ByteArrayInputStream bytesInput = new ByteArrayInputStream(inputJSON.getBytes());

          @Override
          public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
            Object[] args = invocationOnMock.getArguments();
            byte[] output = (byte[]) args[0];
            int offset = (int) args[1];
            int length = (int) args[2];
            return bytesInput.read(output, offset, length);
          }
        });

    when(request.getInputStream()).thenReturn(mockServletInputStream);

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);

    ReservasEndpoint endpoint = new ReservasEndpoint();
    endpoint.doPut(request, response);

    verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    verify(response).addHeader("Content-Type", "application/json");

    // Validación
    ReservaServiceDictionaryImpl service = ReservaServiceDictionaryImpl.getInstance();

    Reserva reserva = service.getById(1);

    assertEquals("Aprobada", reserva.getEstado());
  }

  @Test
  @DisplayName("OPTIONS eventos/")
  void testOptions() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    ReservasEndpoint endpoint = new ReservasEndpoint();
    endpoint.doOptions(request, response);

    verify(response).addHeader("Content-Type", "application/json");
    verify(response).addHeader("Access-Control-Allow-Credentials", "true");
    verify(response).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
    verify(response).addHeader("Access-Control-Allow-Headers", "authorization,content-type");
    verify(response).addHeader("Access-Control-Allow-Origin", "*");
  }
}