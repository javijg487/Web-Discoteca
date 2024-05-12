package es.uv.etse.twcam.backend.apirest;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.Canciones.Cancion;
import es.uv.etse.twcam.backend.business.Canciones.CancionService;
import es.uv.etse.twcam.backend.business.Canciones.CancionServiceImpl;
import es.uv.etse.twcam.backend.business.Eventos.Evento;
import es.uv.etse.twcam.backend.business.Eventos.EventoService;
import es.uv.etse.twcam.backend.business.Eventos.EventoServiceImpl;
import es.uv.etse.twcam.backend.business.ListaCanciones.ListaCanciones;
import es.uv.etse.twcam.backend.business.ListaCanciones.ListaCancionesImpl;
import es.uv.etse.twcam.backend.business.ListaCanciones.ListaCancionesService;
import es.uv.etse.twcam.backend.business.Login.Usuario;
import es.uv.etse.twcam.backend.business.Login.UsuarioService;
import es.uv.etse.twcam.backend.business.Login.UsuarioServiceImpl;
import es.uv.etse.twcam.backend.business.Reserva.Reserva;
import es.uv.etse.twcam.backend.business.Reserva.ReservaService;
import es.uv.etse.twcam.backend.business.Reserva.ReservaServiceDictionaryImpl;

/**
 * Servlet de inicializaci&oacute;n
 * 
 * @author <a href="mailto:raul.penya@uv.es">Ra&uacute;l Pe&ntilde;a-Ortiz</a>
 */
public class InitServlet extends HttpServlet {

    /**
     * Identificador de versi&oacute;n
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger
     */
    protected static Logger logger = LogManager.getLogger(InitServlet.class.getName());

    @Override
    public void init() throws ServletException {
        try {

            logger.info("Starting angular-j2e-example apirest ...");

            String jsonFileusu = getServletConfig().getInitParameter("json-database-usuarios"); // <1>
            InputStream jsonStreamusu = getServletContext().getResourceAsStream(jsonFileusu); // <2>
            initUsuarioService(jsonStreamusu); // <3>

            String jsonFilecancion = getServletConfig().getInitParameter("json-database-canciones"); // <1>
            InputStream jsonStreamcancion = getServletContext().getResourceAsStream(jsonFilecancion); // <2>
            initCancionService(jsonStreamcancion);

            String jsonlistaCanciones = getServletConfig().getInitParameter("json-database-listaCanciones"); // <1>
            InputStream jsonStreamlistaCanciones = getServletContext().getResourceAsStream(jsonlistaCanciones); // <2>
            initListaCancionService(jsonStreamlistaCanciones);

            String jsonEventos = getServletConfig().getInitParameter("json-database-eventos"); // <1>
            InputStream jsonStreamEventos = getServletContext().getResourceAsStream(jsonEventos); // <2>
            initEventoService(jsonStreamEventos);

            String jsonReservas = getServletConfig().getInitParameter("json-database-reservas"); // <1>
            InputStream jsonStreamReservas = getServletContext().getResourceAsStream(jsonReservas); // <2>
            initReservaService(jsonStreamReservas);

            logger.info("proyecto-discoteca apirest is started");

        } catch (Exception e) {
            logger.error("proyecto-discoteca apirest is not able to be started: ", e);
            throw new ServletException(e);
        }
    }

    public static UsuarioService initUsuarioService(InputStream jsonStream)
            throws GeneralException { // <3>

        UsuarioServiceImpl service = UsuarioServiceImpl.getInstance();

        Reader jsonReader = new InputStreamReader(jsonStream);

        Gson gson = new GsonBuilder().create();

        Usuario[] usuarios = gson.fromJson(jsonReader, Usuario[].class);

        for (Usuario usuario : usuarios) {
            service.create(usuario);
        }

        logger.info("Cargados {} usuarios", usuarios.length);

        return service;
    }

    public static CancionService initCancionService(InputStream jsonStream)
            throws GeneralException { // <3>

        CancionServiceImpl service = CancionServiceImpl.getInstance();

        Reader jsonReader = new InputStreamReader(jsonStream);

        Gson gson = new GsonBuilder().create();

        Cancion[] canciones = gson.fromJson(jsonReader, Cancion[].class);

        for (Cancion cancion : canciones) {
            service.create(cancion);
        }

        logger.info("Cargados {} canciones", canciones.length);

        return service;
    }

    public static ListaCancionesService initListaCancionService(InputStream jsonStream)
            throws GeneralException { // <3>

        ListaCancionesImpl service = ListaCancionesImpl.getInstance();

        Reader jsonReader = new InputStreamReader(jsonStream);

        Gson gson = new GsonBuilder().create();

        ListaCanciones[] listaCanciones = gson.fromJson(jsonReader, ListaCanciones[].class);

        for (ListaCanciones evento : listaCanciones) {
            service.create(evento);
        }

        logger.info("Cargados {} listaCanciones", listaCanciones.length);

        return service;
    }

    public static EventoService initEventoService(InputStream jsonStream)
            throws GeneralException { // <3>

        EventoServiceImpl service = EventoServiceImpl.getInstance();

        Reader jsonReader = new InputStreamReader(jsonStream);

        Gson gson = new GsonBuilder().create();

        Evento[] eventos = gson.fromJson(jsonReader, Evento[].class);

        for (Evento evento : eventos) {
            service.create(evento);
        }

        logger.info("Cargados {} eventos", eventos.length);

        return service;
    }

    public static ReservaService initReservaService(InputStream jsonStream)
            throws GeneralException { // <3>

        ReservaServiceDictionaryImpl service = ReservaServiceDictionaryImpl.getInstance();
        Reader jsonReader = new InputStreamReader(jsonStream);
        Gson gson = new GsonBuilder().create();
        Reserva[] eventos = gson.fromJson(jsonReader, Reserva[].class);

        for (Reserva evento : eventos) {
            service.create(evento);
        }
        logger.info("Cargados {} productos", eventos.length);

        return service;
    }
}