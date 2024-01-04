import { Component, OnInit, Inject } from '@angular/core';
import { Evento } from '../compartido/evento';
import { EventoService } from '../services/evento.service';

@Component({
  selector: 'app-eventos',
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.scss'],
})
export class EventosComponent{
  vEventos: Evento[] = [];
  errorMensaje: string = "";
  eventoSeleccionado = new Evento();

  constructor(private eventoService: EventoService,
    @Inject('baseURL') public BaseURL: string) { }

  ngOnInit(){
    this.eventoService.getEventos().subscribe(eventos => this.vEventos = eventos,
      errorMensaje => this.errorMensaje = <any>errorMensaje);
  }

  seleccionarEvento(evento:Evento){
    this.eventoSeleccionado = evento;
  }
}