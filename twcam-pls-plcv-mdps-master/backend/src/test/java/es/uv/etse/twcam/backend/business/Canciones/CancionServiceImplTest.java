package es.uv.etse.twcam.backend.business.Canciones;

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

import es.uv.etse.twcam.backend.business.ProductException;

import org.apache.logging.log4j.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CancionServiceImplTest {

  /**
   * Logger
   */
  private static Logger logger = null;

  /**
   * Servicio a probar.
   */
  protected CancionServiceImpl service;

  /**
   * Crea el test unitario
   */
  public CancionServiceImplTest() {
    this.service = CancionServiceImpl.getInstance();
  }

  @BeforeAll
  public static void setLogger() {
    logger = LogManager.getLogger(CancionServiceImplTest.class.getName());
  }

  @Test
  @Order(1)
  void testGetInstance() {
    assertNotNull(service);
  }

  @Test
  @Order(2)
  void testCreate() throws ProductException {
    ArrayList<Cancion> canciones = new ArrayList<Cancion>();

    Cancion cancion = new Cancion(1, "La playa del ingles", "Quevedo", "3:58", "reggaeton");

    canciones.add(cancion);

    Cancion cancionesTest = service.create(cancion);

    assertNotNull(cancionesTest);
  }

  @Test
  @Order(3)
  void testListAll() {
    List<Cancion> can = service.listAll();
    assertNotNull(can);
    assertEquals(1, can.size());
  }

  @Test
  @Order(4)
  void testFailedCreationFromNull() {

    try {
      service.create(null);
      fail("El valor nulo en producto no estaba controlado");
    } catch (ProductException e) {
      logger.info("El valor nulo ha sido controlado correctamente.");
    }

  }

  @Test
  @Order(5)
  void testClearInstance() {

    CancionServiceImpl.clearInstance();
    assertNotNull(CancionServiceImpl.getInstance());
    CancionServiceImpl.clearInstance();

  }

}
