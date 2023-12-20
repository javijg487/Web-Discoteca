package es.uv.etse.twcam.backend.business.Reserva;

import java.util.ArrayList;

import es.uv.etse.twcam.backend.business.Invitado;

public class Reserva {

  private Integer id;
  private Integer eventoId;
  private String usuario;
  private String estado;
  private ArrayList<Invitado> invitados;

  public Reserva(Integer id, Integer eventoId, String usuario, ArrayList<Invitado> invitados, String estado) {
    this.id = id;
    this.eventoId = eventoId;
    this.usuario = usuario;
    this.estado = estado;
    this.invitados = invitados;
  }

  public Integer getId() {
    return id;
  }

  public Integer getEventoId() {
    return eventoId;
  }

  public String getUsuario() {
    return usuario;
  }

  public String getEstado() {
    return estado;
  }

  public ArrayList<Invitado> getInvitados() {
    return invitados;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setEventoId(Integer eventoId) {
    this.eventoId = eventoId;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public void setInvitados(ArrayList<Invitado> invitados) {
    this.invitados = invitados;
  }

  @Override
  public String toString() {
    return "Reserva [id=" + id + ", usuario=" + usuario + ", invitados=" + invitados + "]";
  }

}
