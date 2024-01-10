package es.uv.etse.twcam.backend.business.Evento;

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

import es.uv.etse.twcam.backend.business.ElementNotExistsException;
import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.Eventos.Evento;
import es.uv.etse.twcam.backend.business.Eventos.EventoServiceImpl;
import es.uv.etse.twcam.backend.business.Login.Usuario;

import org.apache.logging.log4j.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventoServiceImplTest {

    /**
     * Logger
     */
    private static Logger logger = null;

    /**
     * Servicio a probar.
     */
    protected EventoServiceImpl service;

    public EventoServiceImplTest() {
        this.service = EventoServiceImpl.getInstance();
    }

    @BeforeAll
    public static void setLogger() {
        logger = LogManager.getLogger(EventoServiceImpl.class.getName());
    }

    @Test
    @Order(1)
    void testGetInstance() {
        assertNotNull(service);
    }

    @Test
    @Order(2)
    void testCreate() throws GeneralException{
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        Usuario dj = new Usuario("dj1", "dj1", "dj");
        Evento testEvento = new Evento(null, null, "Evento 1", dj, "2024-08-08", "Rock", null);

        Evento eventoReal = service.create(testEvento);
        assertEquals(eventoReal.getId(), 1);
        assertEquals(eventoReal.getPista(), 0);

        Usuario dj2 = new Usuario("dj2", "dj2", "dj");
        Evento tesEvento2 = new Evento(null, null, "Evento 2", dj2, "2024-08-08", "Pop", null);
        Evento eventoReal2 = service.create(tesEvento2);

        assertEquals(eventoReal2.getId(), 2);
        assertEquals(eventoReal2.getPista(), 1);

        assertNotNull(eventoReal2);
        assertNotNull(eventoReal);

        eventos.add(eventoReal);
        eventos.add(eventoReal2);


        
    }

    @Test
    @Order(3)
    void testListAll(){
        List<Evento> eventosAll = service.listAll();
        assertNotNull(eventosAll);
        assertEquals(2, eventosAll.size());
    }

    @Test
    @Order(4)
    void testUpdate() throws GeneralException{
        Evento evento = service.getById(1);
        assertEquals("2024-08-08", evento.getFecha());

        evento.setFecha("2024-08-09");
        evento = service.update(evento);
        assertNotNull(evento);
        assertEquals("2024-08-09", evento.getFecha());
        
    }

    @Test
    @Order(5)
    void testGetById() throws ElementNotExistsException{
        Evento evento = service.getById(1);
        assertEquals(evento.getId(), 1);
        assertEquals(evento.getTematica(), "Rock");
    }

    @Test
    @Order(6)
    void testDelete() throws GeneralException{
        Usuario dj6 = new Usuario("dj6", "dj6", "dj");
        Evento testEvento6 = new Evento(null, null, "Evento 2", dj6, "2024-08-20", "Pop", null);
        Evento eventoReal6= service.create(testEvento6);
        eventoReal6 = service.remove(eventoReal6.getId());
        assertEquals(null, eventoReal6);
    }

    @Test
    @Order(7)
    void testFailedCreate() throws GeneralException{
        List<Evento> eventos = service.listAll();
        Usuario dj3 = new Usuario("dj3", "dj3", "dj");
        Evento tesEvento3 = new Evento(null, null, "Evento 3", dj3, "2024-08-08", "Pop", null);
        Evento eventoReal3 = service.create(tesEvento3);

        Usuario dj4 = new Usuario("dj4", "dj4", "dj");
        Evento tesEvento4 = new Evento(null, null, "Evento 4", dj4, "2024-08-08", "Electro", null);
        Evento eventoReal4 = service.create(tesEvento4);

        eventos.add(eventoReal3);
        eventos.add(eventoReal4);

        Usuario dj5 = new Usuario("dj5", "dj5", "dj");
        Evento tesEvento5 = new Evento(null, null, "Evento 5", dj5, "2024-08-08", "Electro", null);
        try{
            service.create(tesEvento5);
            fail("No se ha controlado 3 eventos por día");
        }catch(GeneralException e){
            logger.info("Se ha controlado 3 eventos por día");
        }
       
        
    }

    @Test
    @Order(8)
    void testFailedUpdate() throws ElementNotExistsException{
        Evento evento = service.getById(1);
        evento.setFecha("2024-08-08");
        
        try{
            service.update(evento);
            fail("No se controlan los días");
        }catch(GeneralException e){
            logger.info("Se ha controlado 3 eventos por día");
        }
        
    }

    @Test
    @Order(9)
    void testFailedGetById(){
        try{
            Evento evento = service.getById(10);
            fail("No controla si existe el identificador");
        }catch(GeneralException e){
            logger.info("Se ha controlado si existe el identificador");
        }
    }

    @Test
    @Order(10)
    void testFailedRemove(){
        try{
            Evento evento = service.remove(100);
            fail("No controla si existe el identificador");
        }catch(GeneralException e){
            logger.info("Se ha controlado que no existe el identificador");
        }
    }

    @Test
    @Order(11)
    void testClearInstace(){
        EventoServiceImpl.clearInstance();
        assertNotNull(EventoServiceImpl.getInstance());
        EventoServiceImpl.clearInstance();
    }

}