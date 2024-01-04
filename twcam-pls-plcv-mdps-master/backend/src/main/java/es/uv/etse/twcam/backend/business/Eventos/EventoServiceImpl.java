package es.uv.etse.twcam.backend.business.Eventos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.uv.etse.twcam.backend.business.IncorrectProductException;
import es.uv.etse.twcam.backend.business.ProductException;
import es.uv.etse.twcam.backend.business.ProductNotExistException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        eventos.sort((e1, e2) -> {
            LocalDateTime fecha1 = LocalDateTime.parse(e1.getFecha(), formatter);
            LocalDateTime fecha2 = LocalDateTime.parse(e2.getFecha(), formatter);
            return fecha1.compareTo(fecha2);
        });

        return eventos;
    }

    @Override
    public Evento getById(Integer id) throws ProductNotExistException {
       if (dictionary.containsKey(id)) {
			return dictionary.get(id);
		} else {
			throw new ProductNotExistException(id);
		}
    }
    
    @Override
    public Evento create(Evento evento) throws ProductException {

        Integer cont = 0;
        List<Evento> eventos = listAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaEventoCrear = LocalDateTime.parse(evento.getFecha(), formatter);
        LocalDateTime fechaEventoActual = null;

        for (Evento ev : eventos) {
            fechaEventoActual = LocalDateTime.parse(ev.getFecha(), formatter);

            if (fechaEventoActual.isAfter(fechaEventoCrear) && cont < 3) {
                break;
            } else if (cont == 3) {
                throw new IncorrectProductException("Ya hay 3 eventos en un día");

            } else if (fechaEventoActual.isEqual(fechaEventoCrear)) {
                cont++;
            }
        }

        if (cont < 3) {
            evento.setId(currentIndex);
            dictionary.put(currentIndex, evento);
            currentIndex++;
            return evento;
        } else {
            throw new IncorrectProductException("Ya hay 3 eventos en un día");
        }

    }
}
