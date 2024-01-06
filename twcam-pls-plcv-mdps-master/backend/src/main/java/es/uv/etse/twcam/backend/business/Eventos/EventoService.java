package es.uv.etse.twcam.backend.business.Eventos;

import java.util.List;

import es.uv.etse.twcam.backend.business.ElementNotExistsException;
import es.uv.etse.twcam.backend.business.GeneralException;

public interface EventoService {
    public List<Evento> listAll();

    public Evento getById(Integer id) throws ElementNotExistsException;

    public Evento create(Evento evento) throws GeneralException;

}
