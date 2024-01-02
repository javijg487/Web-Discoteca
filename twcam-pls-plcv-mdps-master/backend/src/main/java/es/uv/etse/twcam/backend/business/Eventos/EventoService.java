package es.uv.etse.twcam.backend.business.Eventos;

import java.util.List;

import es.uv.etse.twcam.backend.business.ProductException;
import es.uv.etse.twcam.backend.business.ProductNotExistException;

public interface EventoService {
    public List<Evento> listAll();

    public Evento getById(Integer id) throws ProductNotExistException;

    public Evento create(Evento evento) throws ProductException;

}
