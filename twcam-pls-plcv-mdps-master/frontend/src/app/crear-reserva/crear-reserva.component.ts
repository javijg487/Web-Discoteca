import { HttpClient } from "@angular/common/http";
import { Component } from "@angular/core";
import {
  FormControl,
  FormGroup,
  FormBuilder,
  FormArray,
  Validators,
} from "@angular/forms";
import { baseAPIURL, httpOptions } from "../compartido/baseurl";
import { catchError, throwError } from "rxjs";
import { ReservaService } from "../services/reserva.service";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-crear-reserva",
  templateUrl: "./crear-reserva.component.html",
  styleUrls: ["./crear-reserva.component.scss"],
})
export class CrearReservaComponent {
  eventoForm!: FormGroup;
  eventoId!: String;


  constructor(
    private fb: FormBuilder,
    private reservaService: ReservaService,
    private route: ActivatedRoute
  ) {}

  erroresForm: any = {
    nombre: "El nombre es obligatorio",
    dni: "El DNI es obligatorio",
  };

  ngOnInit() {
    this.eventoForm = this.fb.group({
      invitados: this.fb.array([this.createInvitado()], Validators.required),
    });

    this.route.params.subscribe(params => {
      this.eventoId = params['eventoId'];
    });
  }

  get invitados(): FormArray {
    return <FormArray>this.eventoForm.get("invitados");
  }

  addInvitado() {
    this.invitados.push(this.createInvitado());
  }

  createInvitado(): FormGroup {
    return this.fb.group({
      nombre: [null, Validators.required],
      dni: [null, Validators.required],
    });
  }

  removeInvitado(index: number) {
    this.invitados.removeAt(index);
  }

  onSubmit() {
    console.log("entre");
    if (this.eventoForm.status == "VALID") {
      this.reservaService.enviarReserva({...this.eventoForm.value, eventoId: this.eventoId}).subscribe();
    }
  }
}
