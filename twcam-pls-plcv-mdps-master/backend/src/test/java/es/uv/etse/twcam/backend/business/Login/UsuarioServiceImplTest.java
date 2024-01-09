package es.uv.etse.twcam.backend.business.Login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.UsuarioNotExistException;

import org.apache.logging.log4j.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioServiceImplTest {
    /**
     * Logger
     */
    private static Logger logger = null;

    /**
     * Servicio a probar.
     */
    protected UsuarioServiceImpl service;

    /**
     * Crea el test unitario
     */
    public UsuarioServiceImplTest() {
        this.service = UsuarioServiceImpl.getInstance();
    }

    @BeforeAll
    public static void setLogger() {
        logger = LogManager.getLogger(UsuarioServiceImplTest.class.getName());
    }

    @Test
    @Order(1)
    void testGetInstance() {
        assertNotNull(service);
    }

    @Test
    @Order(2)
    void testCreate() throws GeneralException {

        Usuario usuario1 = new Usuario("cliente4", "cliente4", "cliente");
        Usuario creaUsuario = service.create(usuario1);
        assertNotNull(creaUsuario);

        Usuario usuario2 = new Usuario("admin3", "admin3", "admin");
        Usuario creaUsuario2 = service.create(usuario2);
        assertNotNull(creaUsuario2);

        Usuario usuario3 = new Usuario("dj3", "dj3", "dj");
        Usuario creaUsuario3 = service.create(usuario3);
        assertNotNull(creaUsuario3);
    }

    @Test
    @Order(3)
    void testlistAllUsuario() {
        List<Usuario_no_password> listaUsuarios = service.listAllUsuario();
        assertNotNull(listaUsuarios);

        assertEquals(3, listaUsuarios.size());

    }

    @Test
    @Order(4)
    void testfind() throws UsuarioNotExistException {
        Usuario usuario = service.find("cliente4");
        assertNotNull(usuario);
        assertEquals("cliente4", usuario.getNombre());
        assertEquals("cliente", usuario.getRol());

        usuario = service.find("dj3");
        assertNotNull(usuario);
        assertEquals("dj3", usuario.getNombre());
        assertEquals("dj", usuario.getRol());
    }

    @Test
    @Order(5)
    void testvalidarCredenciales() throws UsuarioNotExistException {
        Usuario usuario3 = new Usuario("dj3", "dj3", "dj");
        Usuario_no_password usuarioCredenciales = service.validarCredenciales(usuario3);
        assertNotNull(usuarioCredenciales);

        assertEquals(usuario3.getNombre(), usuarioCredenciales.getNombre());
        assertEquals(usuario3.getRol(), usuarioCredenciales.getRol());

    }

    @Test
    @Order(6)
    void testmostrarDJ(){
        List<Usuario_no_password> listaDJs = service.mostrarDJ();
        assertNotNull(listaDJs);

        assertEquals(1, listaDJs.size());
    }

    @Test
    @Order(7)
    void testFailedFindNombreNull() {
        try {
            service.find(null);
            fail("El valor nulo en nombre no estaba controlado");
        } catch (UsuarioNotExistException e) {
            assertTrue(true, "El valor nulo ha sido controlado correctamente.");
        }
    }

    @Test
    @Order(8)
    void testFailedvalidarCredencialesUsuarioNull()throws UsuarioNotExistException {
       Usuario usuario3 = new Usuario(null, "fgdsjfdjs", "dj");
        Usuario_no_password usuarioCredenciales = service.validarCredenciales(usuario3);

        assertNull(usuarioCredenciales);
    }


    @Test
  @Order(9)
  void testClearInstance() {

    UsuarioServiceImpl.clearInstance();
    assertNotNull(UsuarioServiceImpl.getInstance());
    UsuarioServiceImpl.clearInstance();

  }


}
