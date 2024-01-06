import { Component, OnInit, Inject } from '@angular/core';
import { Evento } from '../compartido/evento';
import { EventoService } from '../services/evento.service';


@Component({
  selector: 'app-eventos',
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.scss'],
})
export class EventosComponent {
  vEventos: Array<any>= [];
  errorMensaje: string = "";
  eventoSeleccionado = new Evento();

  constructor(private eventoService: EventoService,
    @Inject('baseURL') public BaseURL: string) { }


  cargarEventos() {
    this.eventoService.getEventos().subscribe(
      eventos => {
        this.vEventos = eventos;
        console.log("Eventos cargados:", this.vEventos); // Imprime en consola
      },
      errorMensaje => {
        this.errorMensaje = <any>errorMensaje;
        console.error("Error al cargar eventos:", this.errorMensaje); // Imprime el error en consola
      }
    );
  }

  ngOnInit() {
    this.cargarEventos();

  }

  seleccionarEvento(evento: Evento) {
    this.eventoSeleccionado = evento;
  }
}
