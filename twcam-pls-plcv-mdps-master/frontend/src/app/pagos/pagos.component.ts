import { Component } from "@angular/core";
import { ReservaService } from "../services/reserva.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Reserva } from "../compartido/reserva";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: "app-pagos",
  templateUrl: "./pagos.component.html",
  styleUrls: ["./pagos.component.scss"],
})
export class PagosComponent {
  reserva: Reserva = new Reserva();
  reservaId!: number;
  pagosForm!: FormGroup;

  erroresForm: any = {
    numero: "Numero de tarjeta requerido",
    fechaVencimiento: "Fecha de vencimiento requerida",
    cvv: "Codigo de seguridad requerido"
  };

  constructor(
    private fb: FormBuilder,
    private reservaService: ReservaService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit() {
    this.pagosForm = this.fb.group({
      numero: ["", Validators.required],
      fechaVencimiento: ["", Validators.required],
      cvv: ["", [Validators.required]],
    });
    this.route.params.subscribe((params) => {
      this.reservaId = params["reservaId"];
      this.reservaService
        .getReserva(this.reservaId)
        .subscribe((reservaResponse) => {
          this.reserva = reservaResponse;
        });
    });
  }

  pagar() {
    if (this.pagosForm.status == "VALID") {
      this.reservaService
        .editarEstadoReserva(this.reservaId, "Pagada (Esperando aprobacion)")
        .subscribe(() => {
          this.router.navigate(["/reservas"]);
        });
    } else {
      this.pagosForm.markAllAsTouched();
    }
  }
}
