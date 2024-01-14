import { Component, OnInit, Inject } from "@angular/core";
import { getUserData } from '../utils/getUserData';
import { EventoService } from "../services/evento.service";
import { Evento } from "../compartido/evento";

@Component({
  selector: "app-inicio",
  templateUrl: "./inicio.component.html",
  styleUrls: ["./inicio.component.scss"],
})
export class InicioComponent implements OnInit {
  eventos: Evento[] = [];
  userRole: String = getUserData().rol;

  constructor(
    private eventoService: EventoService,
    @Inject("baseURL") public BaseURL: string
  ) {}

  ngOnInit(): void {
    this.eventoService
      .getEventos()
      .subscribe((eventos) => (this.eventos = eventos.slice(0,3)));
  }
}
