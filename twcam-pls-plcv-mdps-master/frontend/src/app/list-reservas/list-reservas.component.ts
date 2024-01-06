import { Component } from "@angular/core";
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from "@angular/animations";
import { Reserva } from "../compartido/reserva";
import { ReservaService } from "../services/reserva.service";
import { getUserData } from "../utils/getUserData";
import { Router } from "@angular/router";
// import { forkJoin } from "rxjs";

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
  eventos: Array<any> = [
    {
      id: 3,
      nombre: "Evento 3",
      dj: {
        nombre: "DJ 3",
      },
      fecha: "2024-06-15T21:00",
      tematica: "Pop",
    },
    {
      id: 2,
      nombre: "Evento 2",
      dj: {
        nombre: "DJ 2",
      },
      fecha: "2024-06-15T21:00",
      tematica: "Reggaeton",
    },
    {
      id: 1,
      nombre: "Evento 1",
      dj: {
        nombre: "DJ 1",
      },
      fecha: "2024-06-15T21:00",
      tematica: "Rock",
    },
  ];

  constructor(private reservaService: ReservaService, private router: Router) {}

  cargarReservas() {
    const usernameForGet = this.userRole === "cliente" ? this.username : null;
    // TODO: Create forkJoin for Eventos & Reservas
    // forkJoin([p1, p2, p3], function (p1, p2, p3) {
    //   /* your combining code here */
    // });
    this.reservaService.getReservas(usernameForGet).subscribe((reservas) => {
      this.tableData = reservas.map((reserva) => {
        const eventoDeReserva = this.eventos.find(
          (evento) => evento.id === reserva.eventoId
        );
        return {
          ...reserva,
          tipoDeReserva: reserva.esIndividual ? "Individual" : "Sala VIP",
          nombreEvento: eventoDeReserva.nombre,
          fecha: eventoDeReserva.fecha.replace(/T.*/,'').split('-').reverse().join('-'),
        };
      });
      console.log(this.tableData);
    });
  }

  ngOnInit() {
    this.cargarReservas();
  }

  onApprovalClick(reservaId: number) {
    this.reservaService
      .editarEstadoReserva(reservaId, "Aprobada")
      .subscribe(() => {
        this.cargarReservas();
      });
  }

  onDenialClick(reservaId: number) {
    this.reservaService
      .editarEstadoReserva(reservaId, "Denegada")
      .subscribe(() => {
        this.cargarReservas();
      });
  }

  onInUseClick(reservaId: number) {
    this.reservaService
      .editarEstadoReserva(reservaId, "En uso")
      .subscribe(() => {
        this.cargarReservas();
      });
  }

  pagar(reservaId: number) {
    this.router.navigate([`/pagos/${reservaId}`]);
  }
}

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
  description: string;
}
