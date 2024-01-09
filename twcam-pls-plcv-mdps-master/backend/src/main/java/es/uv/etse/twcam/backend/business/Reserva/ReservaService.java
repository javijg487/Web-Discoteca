package es.uv.etse.twcam.backend.business.Reserva;

import java.util.List;

import es.uv.etse.twcam.backend.business.GeneralException;

public interface ReservaService {
  public List<Reserva> listAll();

  public List<Reserva> listByUser(String username);

  public Reserva getById(Integer id);

  public Reserva create(Reserva reserva);

  public Reserva update(Reserva reserva) throws GeneralException;

  public void denyPending(Reserva reserva);

  public boolean tieneSalasDisponibles(Integer eventoId);
}
