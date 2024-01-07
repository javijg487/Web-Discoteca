package es.uv.etse.twcam.backend.business.ListaCanciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;


import es.uv.etse.twcam.backend.business.Canciones.Cancion;
import es.uv.etse.twcam.backend.business.Canciones.CancionNotExistException;


import org.apache.logging.log4j.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListaCancionesImplTest {

  /**
   * Logger
   */
  private static Logger logger = null;

  /**
   * Servicio a probar.
   */
  protected ListaCancionesImpl service;

  /**
   * Crea el test unitario
   */
  public ListaCancionesImplTest() {
    this.service = ListaCancionesImpl.getInstance();
  }

  @BeforeAll
  public static void setLogger() {
    logger = LogManager.getLogger(ListaCancionesImplTest.class.getName());
  }

  @Test
  @Order(1)
  void testGetInstance() {
    assertNotNull(service);
  }

  @Test
  @Order(2)
  void testCreate() {
    ArrayList<Cancion> canciones = new ArrayList<Cancion>();
    ArrayList<Cancion> cancionesPendientes = new ArrayList<Cancion>();
    ArrayList<Cancion> cancionesReproducir = new ArrayList<Cancion>();

    Cancion cancion = new Cancion(1, "La playa del ingles", "Quevedo", "3:58", "reggaeton");

    canciones.add(cancion);

    cancionesPendientes.add(cancion);

    cancion = new Cancion(2, "Animals", "Martin Garrix", "3:12", "electro");

    canciones.add(cancion);

    cancion = new Cancion(3, "Mon amour", "Aitana", "2:39", "pop");
    cancionesReproducir.add(cancion);

    ListaCanciones testListaCanciones = new ListaCanciones(1, canciones, cancionesPendientes, cancionesReproducir);
    ListaCanciones ListaCancion = service.create(testListaCanciones);

    assertNotNull(ListaCancion);

  }

  @Test
  @Order(3)
  void testListAll() {

    List<Cancion> ListaCancionesAll = service.listAllCanciones(1);
    assertNotNull(ListaCancionesAll);

    assertEquals(2, ListaCancionesAll.size());
  }

  @Test
  @Order(4)
  void testFiltarDuracion() {
    List<Cancion> ListaCancionesDuracion = service.getByduracion("3:00", 1);
    assertNotNull(ListaCancionesDuracion);
    assertEquals(2, ListaCancionesDuracion.size());

    ListaCancionesDuracion = service.getByduracion("4:00", 1);
    assertNotNull(ListaCancionesDuracion);
    assertEquals(0, ListaCancionesDuracion.size());
  }

  @Test
  @Order(5)
  void testFiltarAutor() {
    List<Cancion> ListaCancionesAutor = service.getByautor("quevedo", 1);
    assertNotNull(ListaCancionesAutor);
    assertEquals(1, ListaCancionesAutor.size());

    ListaCancionesAutor = service.getByautor("martin", 1);
    assertNotNull(ListaCancionesAutor);
    assertEquals(1, ListaCancionesAutor.size());

  }

  @Test
  @Order(6)
  void testFiltarTematica() {
    List<Cancion> ListaCancionesTematica = service.getBytematica("electro", 1);
    assertNotNull(ListaCancionesTematica);
    assertEquals(1, ListaCancionesTematica.size());

    ListaCancionesTematica = service.getBytematica("rock", 1);
    assertNotNull(ListaCancionesTematica);
    assertEquals(0, ListaCancionesTematica.size());
  }

  @Test
  @Order(7)
  void testeditarEstado() throws CancionNotExistException {
    Cancion CancionPendiente = service.editarEstado(2, 1);
    assertNotNull(CancionPendiente);

    assertTrue(service.dictionary.get(String.valueOf(1)).getCancionesPendientes().contains(CancionPendiente));
    assertEquals(2, service.dictionary.get(String.valueOf(1)).getCancionesPendientes().size());

  }

  @Test
  @Order(8)
  void testPasarReproducida() throws CancionNotExistException {
    Cancion CancionReproducir = service.pasarReproducir(1, 1);
    assertNotNull(CancionReproducir);

    assertTrue(service.dictionary.get(String.valueOf(1)).getCancionReproducida().contains(CancionReproducir));
    assertEquals(2, service.dictionary.get(String.valueOf(1)).getCancionReproducida().size());
  }

  @Test
  @Order(9)
  void testPendientes() {
    List<Cancion> ListaPendientes = service.mostrarPendientes(1);
    assertNotNull(ListaPendientes);
    assertEquals(2, ListaPendientes.size());
  }

  @Test
  @Order(10)
  void testReproducidas() {
    List<Cancion> ListaReproducir = service.mostrarReproducida(1);
    assertNotNull(ListaReproducir);
    assertEquals(2, ListaReproducir.size());
  }

  @Test
  @Order(11)
  void testRemove() throws CancionNotExistException {
    Cancion CancionRemove = service.remove(1, 1);
    assertNotNull(CancionRemove);
    assertFalse(service.dictionary.get(String.valueOf(1)).getCancionesPendientes().contains(CancionRemove));
    assertFalse(service.dictionary.get(String.valueOf(1)).getCanciones().contains(CancionRemove));
    assertEquals(1, service.dictionary.get(String.valueOf(1)).getCanciones().size());
    assertEquals(1, service.dictionary.get(String.valueOf(1)).getCancionesPendientes().size());
  }

  @Test
  @Order(12)
  void testRemoveReproducir() throws CancionNotExistException {
    Cancion CancionRemoveReproducir = service.removeReproducidas(1, 1);
    assertNotNull(CancionRemoveReproducir);
    assertFalse(service.dictionary.get(String.valueOf(1)).getCancionReproducida().contains(CancionRemoveReproducir));
    assertEquals(1, service.dictionary.get(String.valueOf(1)).getCancionReproducida().size());

    CancionRemoveReproducir = service.removeReproducidas(3, 1);
    assertNotNull(CancionRemoveReproducir);
    assertFalse(service.dictionary.get(String.valueOf(1)).getCancionReproducida().contains(CancionRemoveReproducir));
    assertEquals(0, service.dictionary.get(String.valueOf(1)).getCancionReproducida().size());

  }

  @Test
  @Order(13)
  void testFailededitarEstadoNull() {

    try {
      service.editarEstado(null, null);
      fail("El valor nulo en evento o cancion no estaba controlado");
    } catch (CancionNotExistException e) {
      logger.info("El valor nulo ha sido controlado correctamente.");
    }

  }

  @Test
  @Order(14)
  void testFailedremoveNull() {

    try {
      service.remove(null, null);
      fail("El valor nulo en evento o cancion no estaba controlado");
    } catch (CancionNotExistException e) {
      logger.info("El valor nulo ha sido controlado correctamente.");
    }
  }

  @Test
  @Order(15)
  void testFailedremoveReproducidasNull() {

    try {
      service.removeReproducidas(null, null);
      fail("El valor nulo en evento o cancion no estaba controlado");
    } catch (CancionNotExistException e) {
      logger.info("El valor nulo ha sido controlado correctamente.");
    }
  }

  @Test
  @Order(16)
  void testFailedpasarReproducirNull() {

    try {
      service.pasarReproducir(null, null);
      fail("El valor nulo en evento o cancion no estaba controlado");
    } catch (CancionNotExistException e) {
      logger.info("El valor nulo ha sido controlado correctamente.");
    }
  }

  @Test
  @Order(15)
  void testClearInstance() {
    ListaCancionesImpl.clearInstance();
    assertNotNull(ListaCancionesImpl.getInstance());
    ListaCancionesImpl.clearInstance();

  }

}
