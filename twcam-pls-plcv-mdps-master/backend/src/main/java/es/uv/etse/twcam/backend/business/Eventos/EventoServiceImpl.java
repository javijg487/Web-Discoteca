package es.uv.etse.twcam.backend.business.Eventos;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import es.uv.etse.twcam.backend.business.ElementNotExistsException;
import es.uv.etse.twcam.backend.business.GeneralException;
import es.uv.etse.twcam.backend.business.IncorrectElementException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventoServiceImpl implements EventoService {

    private static EventoServiceImpl the;

    protected Map<Integer, Evento> dictionary;
    protected int currentIndex;

    private EventoServiceImpl() {
        dictionary = new Hashtable<>();
        currentIndex = 1;
    }

    public static EventoServiceImpl getInstance() {

        if (the == null) {
            the = new EventoServiceImpl();
        }

        return the;
    }

    public static void clearInstance() {

        if (the != null) {
            the.dictionary.clear();
            the = null;
        }

    }

    @Override
    public List<Evento> listAll() {
        List<Evento> eventos = new ArrayList<>();
        eventos.addAll(dictionary.values());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        eventos.sort((e1, e2) -> {
            LocalDate fecha1 = LocalDate.parse(e1.getFecha(), formatter);
            LocalDate fecha2 = LocalDate.parse(e2.getFecha(), formatter);
            return fecha1.compareTo(fecha2);
        });

        return eventos;
    }

    @Override
    public Evento getById(Integer id) throws ElementNotExistsException {
        if (dictionary.containsKey(id)) {
            return dictionary.get(id);
        } else {
            throw new ElementNotExistsException("Evento", id);
        }
    }

    @Override
    public Evento create(Evento evento) throws GeneralException {

        Integer cont = 0;
        List<Evento> eventos = listAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaEventoCrear = LocalDate.parse(evento.getFecha(), formatter);
        LocalDate fechaEventoActual = null;

        for (Evento ev : eventos) {
            fechaEventoActual = LocalDate.parse(ev.getFecha(), formatter);

            if (fechaEventoActual.isAfter(fechaEventoCrear) && cont < 3) {
                break;
            } else if (cont == 3) {
                throw new GeneralException("Ya hay 3 eventos en un día");
            } else if (fechaEventoActual.isEqual(fechaEventoCrear)) {
                cont++;
            }
        }

        if (cont < 3) {
            evento.setId(currentIndex);
            evento.setPista(cont);
            dictionary.put(currentIndex, evento);
            currentIndex++;
            return evento;
        } else {
            throw new IncorrectElementException("Ya hay 3 eventos en un día");
        }

    }
}
