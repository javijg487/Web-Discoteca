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
  columnsToDisplay = ["eventoId", "estado"];
  expandedElement: Reserva | null = null;
  reservas: Array<Reserva> = [];
  userRole: String = getUserData().rol;
  username: String = getUserData().nombre;

  constructor(private reservaService: ReservaService, private router: Router) {}

  cargarReservas() {
    const usernameForGet = this.userRole === "cliente" ? this.username : null;
    this.reservaService.getReservas(usernameForGet).subscribe((reservas) => {
      this.reservas = reservas;
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
