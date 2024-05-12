import { Component } from "@angular/core";
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from "@angular/animations";
import { forkJoin } from "rxjs";

import { Reserva } from "../compartido/reserva";
import { ReservaService } from "../services/reserva.service";
import { getUserData } from "../utils/getUserData";
import { Router } from "@angular/router";
import { EventoService } from "../services/evento.service";

/**
 * @title Table with expandable rows
 */
@Component({
  selector: "app-list-reservas",
  styleUrls: ["list-reservas.component.scss"],
  templateUrl: "list-reservas.component.html",
  animations: [
    trigger("detailExpand", [
      state("collapsed", style({ height: "0px", minHeight: "0" })),
      state("expanded", style({ height: "*" })),
      transition(
        "expanded <=> collapsed",
        animate("225ms cubic-bezier(0.4, 0.0, 0.2, 1)")
      ),
    ]),
  ],
})
export class ListReservasComponent {
  tableHeaderLabels = {
    eventoId: "ID del Evento",
    nombreEvento: "Nombre del evento",
    fecha: "Fecha",
    estado: "Estado",
    tipoDeReserva: "Tipo de Reserva",
  };
  columnsToDisplay = Object.keys(this.tableHeaderLabels) as Array<
    keyof typeof this.tableHeaderLabels
  >;
  expandedElement: Reserva | null = null;
  tableData: Array<Object> = [];
  userRole: String = getUserData().rol;
  username: String = getUserData().nombre;
  eventos: Array<any> = [];

  constructor(
    private reservaService: ReservaService,
    private eventoService: EventoService,
    private router: Router
  ) {}

  cargarReservas() {
    const usernameForGet = this.userRole === "cliente" ? this.username : null;
    // TODO: Create forkJoin for Eventos & Reservas
    forkJoin(
      [
        this.reservaService.getReservas(usernameForGet),
        this.eventoService.getEventos(),
      ],
      (reservas, eventos) => {
        this.eventos = eventos;
        this.tableData = reservas.map((reserva) => {
          const eventoDeReserva = this.eventos.find(
            (evento) => evento.id === reserva.eventoId
          );
          return {
            ...reserva,
            tipoDeReserva: reserva.esIndividual ? "Individual" : "Sala VIP",
            nombreEvento: eventoDeReserva.nombre,
            fecha: eventoDeReserva.fecha
              .replace(/T.*/, "")
              .split("-")
              .reverse()
              .join("-"),
          };
        });
      }
    ).subscribe();
  }

  ngOnInit() {
    this.cargarReservas();
  }

  onApprovalClick(reserva: Reserva) {
    reserva.estado = "Aprobada";
    this.reservaService.editarEstadoReserva(reserva).subscribe(() => {
      this.cargarReservas();
    });
  }

  onDenialClick(reserva: Reserva) {
    reserva.estado = "Denegada";
    this.reservaService.editarEstadoReserva(reserva).subscribe(() => {
      this.cargarReservas();
    });
  }

  onInUseClick(reserva: Reserva) {
    reserva.estado = "En uso";
    this.reservaService.editarEstadoReserva(reserva).subscribe(() => {
      this.cargarReservas();
    });
  }

  pagar(reservaId: number) {
    this.router.navigate([`/pagos/${reservaId}`]);
  }

  onPedirCancionesClick(eventoId: number) {
    this.router.navigate([`/canciones/${eventoId}`]);
  }
}

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
  description: string;
}
