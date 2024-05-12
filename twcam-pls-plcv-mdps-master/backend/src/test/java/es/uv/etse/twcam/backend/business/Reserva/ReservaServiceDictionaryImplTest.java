package es.uv.etse.twcam.backend.business.Reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.Invitado;

import org.apache.logging.log4j.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservaServiceDictionaryImplTest {

  /**
   * Logger
   */
  private static Logger logger = null;

  /**
   * Servicio a probar.
   */
  protected ReservaServiceDictionaryImpl service;

  /**
   * Crea el test unitario
   */
  public ReservaServiceDictionaryImplTest() {
    this.service = ReservaServiceDictionaryImpl.getInstance();
  }

  @BeforeAll
  public static void clearInstance() {
    ReservaServiceDictionaryImpl.clearInstance();
  }

  @BeforeAll
  public static void setLogger() {
    logger = LogManager.getLogger(ReservaServiceDictionaryImpl.class.getName());
  }

  @Test
  @Order(1)
  void testGetInstance() {
    assertNotNull(service);
  }

  @Test
  @Order(2)
  void testCreate() {
    ArrayList<Invitado> invitados = new ArrayList<Invitado>();
    Invitado invitado = new Invitado("Samuel", "12345A");
    invitados.add(invitado);

    invitado = new Invitado("Maria", "000000B");
    invitados.add(invitado);

    Reserva testReserva = new Reserva(321415, 1, "cliente1", invitados, null, false);
    Reserva reservaReal = service.create(testReserva);

    assertEquals(reservaReal.getId(), 0);
    assertEquals(reservaReal.getEstado(), "Pendiente de Pago");

    Reserva testReserva2 = new Reserva(321415, 1, "cliente2", invitados, null, false);
    Reserva reservaReal2 = service.create(testReserva2);

    assertEquals(reservaReal2.getId(), 1);
    assertEquals(reservaReal2.getEstado(), "Pendiente de Pago");

  }

  @Test
  @Order(3)
  void testListAll() {
    List<Reserva> reservas = service.listAll();
    assertNotNull(reservas);
    assertEquals(2, reservas.size());
  }

  @Test
  @Order(5)
  void testUpdate() throws GeneralException {
    Reserva reserva = service.listAll().get(0);
    assertEquals("Pendiente de Pago", reserva.getEstado());

    reserva.setEstado("Aprobada");
    reserva = service.update(reserva);
    assertNotNull(reserva);
    assertEquals("Aprobada", reserva.getEstado());
  }

  @Test
  @Order(6)
  void testListByUser() {
    ArrayList<Reserva> reservas = service.listByUser("admin1");
    assertEquals(reservas.size(), 0);

    reservas = service.listByUser("cliente1");
    assertEquals(reservas.size(), 1);
    assertEquals(reservas.get(0).getId(), 0);

    reservas = service.listByUser("cliente2");
    assertEquals(reservas.size(), 1);
    assertEquals(reservas.get(0).getId(), 1);
  }

  @Test
  @Order(7)
  void testGetById() {
    Reserva reserva = service.getById(0);
    assertEquals(reserva.getId(), 0);
    assertEquals(reserva.getUsuario(), "cliente1");
  }

  @Test
  @Order(8)
  void testDenyPending() {
    ArrayList<Invitado> invitados = new ArrayList<Invitado>();
    Invitado invitado = new Invitado("Samuel", "12345A");
    invitados.add(invitado);
    invitado = new Invitado("Maria", "000000B");
    invitados.add(invitado);

    Reserva reserva = new Reserva(321415, 1, "cliente1", invitados, "Aprobada", false);
    service.create(reserva);
    service.create(reserva);

    service.denyPending(service.getById(1));

    assertEquals(service.getById(0).getEstado(), "Denegada");
  }

  @Test
  @Order(9)
  void testTieneSalasDisponibles() {
    assertEquals(service.tieneSalasDisponibles(1), false);
    assertEquals(service.tieneSalasDisponibles(0), true);
  }

  @Test
  @Order(10)
  void testFailedUpdate() {
    try {
      service.update(null);
      fail("El valor nulo en reserva no estaba controlado");
    } catch (GeneralException e) {
      logger.info("El valor nulo ha sido controlado correctamente.");
    }
  }

  @Test
  @Order(11)
  void testClearInstance() {

    ReservaServiceDictionaryImpl.clearInstance();
    assertNotNull(ReservaServiceDictionaryImpl.getInstance());
    ReservaServiceDictionaryImpl.clearInstance();

  }
}
