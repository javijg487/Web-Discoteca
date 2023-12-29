package es.uv.etse.twcam.backend.business.Eventos;

import java.util.List;

import es.uv.etse.twcam.backend.business.GeneralException;

public interface EventoService {
    public List<Evento> listAll();

    public Evento getById(Integer id);

    public Evento create(Evento evento);

    public Evento delete(Evento evento);
}
