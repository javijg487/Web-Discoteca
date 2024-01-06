import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { getUserData } from "../utils/getUserData";
import { DjService } from "../services/dj.service";
import { FormBuilder } from "@angular/forms";
import { EventoService } from "../services/evento.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-crear-evento",
  templateUrl: "./crear-evento.component.html",
  styleUrls: ["./crear-evento.component.scss"],
})
export class CrearEventoComponent implements OnInit {
  eventForm!: FormGroup;
  vDjs: any[] = [];
  errorMensaje: string = "";
  eventoNombre!: String;
  eventoDJ!: String;
  eventoFecha!: String;
  eventoTematica!: String;

  constructor(
    private djService: DjService,
    @Inject("baseURL") public BaseURL: string,
    private fb: FormBuilder,
    private eventoService: EventoService,
    private router: Router
  ) {
    this.crearFormulario();
  }

  erroresForm: any = {
    nombre: "El nombre es obligatorio",
    dni: "El DNI es obligatorio",
  };

  ngOnInit() {
    this.djService.getDjs().subscribe((djs) => (this.vDjs = djs));
  }

  crearFormulario() {
    this.eventForm = this.fb.group({
      nombre: [
        "",
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(25),
        ],
      ],
      dj: ["", [Validators.required]],
      fecha: ["", [Validators.required]],
      tematica: ["", [Validators.required]],
    });
  }

  onSubmitEvento() {
   console.log(this.eventForm.status)
    // Aquí procesarías los datos del formulario
    this.eventoService
      .enviarEvento({
        ...this.eventForm.value,
        dj: { nombre: this.eventForm.value.dj },
      })
      .subscribe();

    this.eventForm.reset({
      nombre: "",
      dj: "",
      fecha: "",
      tematica: "",
    });
  }
}
